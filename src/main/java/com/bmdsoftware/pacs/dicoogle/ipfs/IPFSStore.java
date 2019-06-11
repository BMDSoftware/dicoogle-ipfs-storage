/**
 * Copyright (C) 2018  BMD software, Lda
 *
 * This file is part of Dicoogle/ipfs.
 *
 * Dicoogle/ipfs is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dicoogle/ipfs is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Dicoogle.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.bmdsoftware.pacs.dicoogle.ipfs;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.io.DicomOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ua.dicoogle.sdk.StorageInputStream;
import pt.ua.dicoogle.sdk.StorageInterface;
import pt.ua.dicoogle.sdk.settings.ConfigurationHolder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

/**
 * @author Luís A. Bastião Silva - <bastiao@bmd-software.com>
 */
public class IPFSStore implements StorageInterface {

    private static final Logger logger = LoggerFactory.getLogger(IPFSStore.class);

    private boolean enabled = true;

    private IPFS ipfs = null;


    public IPFSStore(IPFS ipfs) {
        this.ipfs = ipfs;
    }

    @Override
    public String getScheme() {
        return "ipfs://";
    }

    @Override
    public boolean handles(URI location) {
        return location.toString().contains("ipfs://");
    }

    @Override
    public Iterable<StorageInputStream> at(final URI location, Object... objects) {


        Iterable<StorageInputStream> c = new Iterable<StorageInputStream>() {

            @Override
            public Iterator<StorageInputStream> iterator() {
                Collection c2 = new ArrayList<>();
                StorageInputStream s = new StorageInputStream() {

                    @Override
                    public URI getURI() {
                        return location;
                    }

                    @Override
                    public InputStream getInputStream() throws IOException {
                        Multihash filePointer = Multihash.fromBase58(location.toString().replaceFirst("ipfs://", ""));

                        byte[] fileContents = new byte[0];
                        try {
                            fileContents = ipfs.get(filePointer);
                        } catch (IOException e) {
                            logger.error("Failed to retrieve object", e);
                            return null;
                        }

                        ByteArrayInputStream bin = new ByteArrayInputStream(fileContents);
                        return bin;
                    }

                    @Override
                    public long getSize() throws IOException {
                        Multihash filePointer = Multihash.fromBase58(location.toString().replaceFirst("ipfs://", ""));
                        return ipfs.get(filePointer).length;
                    }
                };
                c2.add(s);
                return c2.iterator();
            }
        };
        return c;

    }

    @Override
    public URI store(DicomObject dicomObject, Object... objects) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DicomOutputStream dos = new DicomOutputStream(bos);
        try {
            dos.writeDicomFile(dicomObject);
        } catch (IOException ex) {
            logger.error("Failed to store object", ex);
        }


        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(UUID.randomUUID().toString(), bos.toByteArray());
        MerkleNode addResult = null;
        try {
            addResult = ipfs.add(file).get(0);
        } catch (IOException e) {
            logger.error("Failed to store object", e);
            return null;
        }

        // Retrieve the hash
        try {
            return new URI("ipfs://" + addResult.hash.toBase58());
        } catch (URISyntaxException e) {
            logger.error("Failed to build uri ", e);
            return null;
        }
    }

    @Override
    public URI store(DicomInputStream dicomInputStream, Object... objects) throws IOException {

        DicomObject obj = dicomInputStream.readDicomObject();
        return store(obj);
    }

    @Override
    public void remove(URI uri) {
        // TODO: not supported at IPFS. What should we do?
        logger.error("Operation not supported for " + uri.toString());
    }

    @Override
    public String getName() {
        return "ipfs-storage";
    }

    @Override
    public boolean enable() {
        this.enabled = true;
        return true;
    }

    @Override
    public boolean disable() {
        this.enabled = false;
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public ConfigurationHolder getSettings() {
        return null;
    }

    @Override
    public void setSettings(ConfigurationHolder configurationHolder) {

    }
}

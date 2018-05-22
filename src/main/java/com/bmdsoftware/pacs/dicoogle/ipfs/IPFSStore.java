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
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;
import pt.ua.dicoogle.sdk.StorageInputStream;
import pt.ua.dicoogle.sdk.StorageInterface;
import pt.ua.dicoogle.sdk.settings.ConfigurationHolder;

import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * @author Luís A. Bastião Silva - <bastiao@bmd-software.com>
 */
public class IPFSStore implements StorageInterface {

    private IPFS ipfs = null;


    public IPFSStore(IPFS ipfs){
        this.ipfs = ipfs;
    }

    @Override
    public String getScheme() {
        return "ipfs://";
    }

    @Override
    public boolean handles(URI location) {
        if (location.toString().contains("ipfs://"))
            return true;
        return false;
    }


    @Override
    public Iterable<StorageInputStream> at(URI uri, Object... objects) {
        return null;
    }

    @Override
    public URI store(DicomObject dicomObject, Object... objects) {
        return null;
    }

    @Override
    public URI store(DicomInputStream dicomInputStream, Object... objects) throws IOException {
        //

        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File("hello.txt"));
        MerkleNode addResult = ipfs.add(file).get(0);

        return null;
    }

    @Override
    public void remove(URI uri) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean enable() {
        return false;
    }

    @Override
    public boolean disable() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setSettings(ConfigurationHolder configurationHolder) {

    }

    @Override
    public ConfigurationHolder getSettings() {
        return null;
    }
}

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

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.io.DicomInputStream;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.ua.dicoogle.sdk.PluginSet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import static org.junit.Assert.assertEquals;

/**
 * @author Rui Lebre - <ruilebre@ua.pt>
 */
public class TestStorage {
    private static IPFSStore ipfsStorePlugin;

    @BeforeClass
    public static void setup() throws IOException {
        PluginSet ps = new IPFSPluginSet();
        ipfsStorePlugin = (IPFSStore) ps.getStoragePlugins().toArray()[0];
    }

    @Test
    public void testStorageDicomStream() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File dicomFile = new File(classLoader.getResource("DoseSR_GE_01.DCM").getPath());

        // test store operation
        URI fileURI = ipfsStorePlugin.store(new DicomInputStream(dicomFile));

        // test at operation
        InputStream storedDicomStream = ipfsStorePlugin.at(fileURI).iterator().next().getInputStream();

        DicomObject originalDicomObj = new DicomInputStream(dicomFile).readDicomObject();
        DicomObject storedDicomObj = new DicomInputStream(storedDicomStream).readDicomObject();

        assertEquals(originalDicomObj.get(Tag.SOPInstanceUID), storedDicomObj.get(Tag.SOPInstanceUID));

        ipfsStorePlugin.remove(fileURI);
    }
}

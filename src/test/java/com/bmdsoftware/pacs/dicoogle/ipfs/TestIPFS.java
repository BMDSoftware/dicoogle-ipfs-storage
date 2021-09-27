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
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Rui Lebre - <ruilebre@ua.pt>
 */
public class TestIPFS {
    private static IPFS ipfs;
    private static String testFileContent = "test test test";

    @BeforeClass
    public static void setup() {
        ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
    }

    @Test
    public void testFileOperations() throws IOException {
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(UUID.randomUUID().toString(), testFileContent.getBytes(StandardCharsets.UTF_8));

        // Test add file
        MerkleNode addResult = ipfs.add(file).get(0);
        byte[] catResult = ipfs.cat(addResult.hash);
        assertTrue("Add file failed.", catResult.length > 0);


        // Test file content
        assertTrue("File content not equal.", Arrays.equals(catResult, file.getContents()));

        // Test file remove pin
        List<Multihash> pinRm = ipfs.pin.rm(addResult.hash, true);
        assertTrue("Pin removal failed.", pinRm.get(0).equals(addResult.hash));

        // Test garbage collector
        ipfs.repo.gc();
    }

    @Test
    public void testStats() throws IOException {
        Map stats = ipfs.stats.bw();

        assertNotNull("Stats null.", stats);
    }

    @Test
    public void testListLocal() throws IOException {
        List localRefs = ipfs.refs.local();

        assertNotNull("Local refs null.", localRefs);
        assertTrue("Local refs must be at least one.", localRefs.size() > 0);
    }
}

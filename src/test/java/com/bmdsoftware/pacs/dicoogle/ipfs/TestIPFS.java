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
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.List;

public class TestIPFS {



    @Test
    @Ignore // needs isolation
    public void ipfs() throws IOException {


        IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
        //IPFS ipfs = new IPFS("/ip4/104.236.179.241/tcp/4001");


//        ipfs.refs.local();
        ClassLoader classLoader = getClass().getClassLoader();
        File fileTmp = new File(classLoader.getResource("hello.txt").getFile());
        BufferedReader br = new BufferedReader(new FileReader(fileTmp));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            System.out.println(everything);
        } finally {
            br.close();
        }

        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(fileTmp);
        System.out.println("File name: " + file.getName().get());
        MerkleNode addResult = ipfs.add(file).get(0);
        System.out.println("File hash " +addResult.hash);
        System.out.println("File size " +addResult.size.get());
        System.out.println("File size " +addResult.toJSON());

        //NamedStreamable.ByteArrayWrapper file2 = new NamedStreamable.ByteArrayWrapper("hello.txt", "G'day world! IPFS rocks!".getBytes());
        //MerkleNode addResult2 = ipfs.add(file2).get(0);

//        System.out.println(ipfs.stats.bw().size());

        Multihash filePointer = Multihash.fromBase58(addResult.hash.toBase58());
        //Multihash filePointer = Multihash.fromBase58("QmdWFrmDRZmAG8kMX5UG2RmEWpNbUkppeuiLcvNTBnU8df");
        System.out.println(filePointer.type.length);




        byte[] fileContents = ipfs.cat(filePointer);
        System.out.println(new String(fileContents));
        List<MerkleNode> addResult3 = ipfs.ls(filePointer);


    }

}

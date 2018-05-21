package com.bmdsoftware.pacs.dicoogle.ipfs;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;

public class TestIPFS {



    @Test
    //@Ignore // needs isolation
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
        System.out.println("File hash " +addResult.hash.toBase58());
//        System.out.println("File size " +addResult.size.get());
        System.out.println("File size " +addResult.toJSON());

        //NamedStreamable.ByteArrayWrapper file2 = new NamedStreamable.ByteArrayWrapper("hello.txt", "G'day world! IPFS rocks!".getBytes());
        //MerkleNode addResult2 = ipfs.add(file2).get(0);

//        System.out.println(ipfs.stats.bw().size());

        //Multihash filePointer = Multihash.fromBase58(addResult.hash.toBase58());
        Multihash filePointer = Multihash.fromBase58("QmdWFrmDRZmAG8kMX5UG2RmEWpNbUkppeuiLcvNTBnU8df");
        System.out.println(filePointer.type.length);



        byte[] fileContents = ipfs.get(filePointer);
        System.out.println(new String(fileContents));

    }

}

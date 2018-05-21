package com.bmdsoftware.pacs.dicoogle.ipfs;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestIPFS {



    @Test
    //@Ignore // needs isolation
    public void ipfs() throws IOException {


        IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");

        ipfs.refs.local();

        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File("hello.txt"));
        MerkleNode addResult = ipfs.add(file).get(0);


        NamedStreamable.ByteArrayWrapper file2 = new NamedStreamable.ByteArrayWrapper("hello.txt", "G'day world! IPFS rocks!".getBytes());
        addResult = ipfs.add(file2).get(0);


        Multihash filePointer = Multihash.fromBase58("QmPZ9gcCEpqKTo6aq61g2nXGUhM4iCL3ewB6LDXZCtioEB");
        byte[] fileContents = ipfs.cat(filePointer);


    }

}

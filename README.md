Dicoogle IPFS Storage Plugin 
========================

This is a Dicoogle plugin to support the storage of files over IPFS. 

Getting Started
---------------

[IPFS](https://ipfs.io/) is a P2P distributed file system that targets to connect all computing devices with 
the same system of files. IPFS provides a high-throughput, content-addressed block storage model, with 
addressed with a hash URI. Dicoogle IPFS is as far as we know, the first PACS archive supporting [DICOM](https://www.dicomstandard.org/) communications
with IPFS capabilities.
  

### Run IPFS Daemon 


1. First, go to and download the IPFS. You can use Go IPFS Daemon. 
2. Run init to create the hash files and keys with:

    $ ./ipfs init

3. Start the daemon: 

    $ ./ipfs daemon

It should retrieve something similar: 

```
Initializing daemon...
Swarm listening on /ip4/127.0.0.1/tcp/4001
Swarm listening on /ip4/169.254.195.127/tcp/4001
Swarm listening on /ip4/172.20.20.25/tcp/4001
Swarm listening on /ip4/192.168.1.72/tcp/4001
Swarm listening on /ip6/::1/tcp/4001
Swarm listening on /p2p-circuit/ipfs/QmPYwyhd9WfH6cmyXh4SJpSkxweeHmUbohao6g4H4zhD3G
Swarm announcing /ip4/127.0.0.1/tcp/4001
Swarm announcing /ip4/169.254.195.127/tcp/4001
Swarm announcing /ip4/172.20.20.25/tcp/4001
Swarm announcing /ip4/192.168.1.72/tcp/4001
Swarm announcing /ip4/2.83.194.251/tcp/60663
Swarm announcing /ip6/::1/tcp/4001
API server listening on /ip4/127.0.0.1/tcp/5001
Gateway (readonly) server listening on /ip4/127.0.0.1/tcp/8080
Daemon is ready
``` 


### Using your plugin

1. Copy your plugin's package with dependencies (target/ipfs-plugin-1.0.0-SNAPSHOT-jar-with-dependencies.jar)
   to the "Plugins" folder inside the root folder of Dicoogle.

2. Run Dicoogle. The plugin will be automatically included.


### Configuration 

You can configure the IPFS endpoint in the Plugin settings. Check here: 
    

```
| => cat Plugins/settings/IPFS.xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<configuration>
    <ipfs>
        <endpoint>/ip4/127.0.0.1/tcp/5001</endpoint>
    </ipfs>
</configuration>
```

### Check the current status


Status Web service available at: ipfs/status?action=status
    
    URL: http://localhost:8082/ipfs/status?action=status 


### Troubleshooting 

By default Dicoogle and IPFS Daemon open the 8080 tcp port, so one of application should change the port to avoid 
problems.


## Use case

1) Store medical imaging over IPFS
2) Failures
3) Avoid changes in medical imaging 
4) Allow tracebility 




## Support and consulting

[<img src="https://raw.githubusercontent.com/wiki/BMDSoftware/dicoogle/images/bmd.png" height="64" alt="BMD Software">](https://www.bmd-software.com)

Please contact [BMD Software](https://www.bmd-software.com) for professional support and consulting services.



Platforms
----------

Dicoogle IPFS has been tested in:

- Windows
- Linux
- Mac OS X
- FreeBSD and OpenBSD

For more information, please visit http://www.dicoogle.com


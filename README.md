Dicoogle IPFS Storage Plugin 
========================

This is a Dicoogle plugin to support the storage of files over IPFS. 

Getting Started
---------------

### Installing and running Dicoogle

1. Go to http://www.dicoogle.com/?page_id=67
2. Download version 2 (or later) of Dicoogle
3. Extract your contents to its own directory (e.g. "~/dicoogle" or "C:\dicoogle", depending on the platform).
4. Run Dicoogle with: sh Dicoogle.sh (OSX / Linux) or Dicoogle.bat (Windows).
5. You should see your web browser opening the Dicoogle user interface. Is it running? You're ok!

### Downloading and building the plugin

Maven is required in order to build the project. An IDE with Maven support such as Netbeans may also help.

1. Clone the git repository at https://github.com/bioinformatics-ua/dicooglePluginSample.git

2. Go to the project's base directory in a command line and run `mvn install`. Alternatively, open
   the Maven project of the plugin with your IDE, then force it to build your project.

3. If the building task is successful, you will have a new jar with dependencies in the target
   folder (target/dicoogle-plugin-sample-2.0-jar-with-dependencies.jar).


### Using your plugin

1. Copy your plugin's package with dependencies (target/dicoogle-plugin-sample-2.0-jar-with-dependencies.jar)
   to the "Plugins" folder inside the root folder of Dicoogle.

2. Run Dicoogle. The plugin will be automatically included.


Web service plugin : 
--------------------------------------

To test the webservice plugin, you may open your browser and navigate to these URLs:


Platforms
----------

Dicoogle IPFS has been tested in:

- Windows
- Linux
- Mac OS X
- FreeBSD and OpenBSD

For more information, please visit http://www.dicoogle.com


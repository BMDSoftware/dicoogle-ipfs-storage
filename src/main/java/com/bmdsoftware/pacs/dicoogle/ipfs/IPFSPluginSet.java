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
import net.xeoh.plugins.base.annotations.PluginImplementation;
import org.apache.commons.configuration.XMLConfiguration;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ua.dicoogle.sdk.*;
import pt.ua.dicoogle.sdk.settings.ConfigurationHolder;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 * The main plugin set.
 *
 * This is the entry point for all plugins.
 *
 * @author Luís A. Bastião Silva - <bastiao@bmd-software.com>
 * @author Eriksson Monteiro - <eriksson.monteiro@bmd-software.com>
 */
@PluginImplementation
public class IPFSPluginSet implements PluginSet {
    // use slf4j for logging purposes
    private static final Logger logger = LoggerFactory.getLogger(IPFSPluginSet.class);

    // We will list each of our plugins as an attribute to the plugin set

    private final IPFSJettyPlugin jettyWeb;
    private final IPFSStore storage;
    private String endpoint = "/ip4/127.0.0.1/tcp/5001";
    private IPFS ipfs;
    private ConfigurationHolder settings;

    public IPFSPluginSet() throws IOException {
        logger.info("Initializing IPFS Plugin Set");

        // construct all plugins here
        logger.info("IPFS connecting to .. " + endpoint);
        try{
            IPFS ipfs = new IPFS(endpoint);
        }
        catch(Exception e){
            logger.error("IPFS is not ready to connect. Please, configure IPFS daemon.");
        }
        // Initialize other plugins and services
        this.jettyWeb = new IPFSJettyPlugin(ipfs);
        this.storage = new IPFSStore(ipfs);
        logger.info("IPFS Plugin Set was initialized.");
    }


    @Override
    public Collection<IndexerInterface> getIndexPlugins() {

        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<QueryInterface> getQueryPlugins() {
        return Collections.EMPTY_LIST;
    }

    /**
     * This method is used to retrieve a name for identifying the plugin set. Keep it as a constant value.
     *
     * @return a unique name for the plugin set
     */
    @Override
    public String getName() {
        return "IPFS";
    }

    @Override
    public Collection<ServerResource> getRestPlugins() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<JettyPluginInterface> getJettyPlugins() {
        return Collections.singleton(this.jettyWeb);
    }

    @Override
    public void shutdown() {
    }

    @Override
    public Collection<StorageInterface> getStoragePlugins() {
        return Collections.singleton(this.storage);
    }

    @Override
    public ConfigurationHolder getSettings() {
        return this.settings;
    }

    @Override
    public void setSettings(ConfigurationHolder xmlSettings) {
        this.settings = xmlSettings;
        XMLConfiguration cnf = this.settings.getConfiguration();
        cnf.setThrowExceptionOnMissing(true);

        try {
            endpoint = cnf.getString("ipfs.endpoint", "/ip4/127.0.0.1/tcp/5001");

            if (!cnf.containsKey("ipfs.endpoint")) {
                cnf.setProperty("ipfs.endpoint", "/ip4/127.0.0.1/tcp/5001");
            }
        } catch (Exception ex) {
            logger.warn("Failed to configure plugin: required fields are missing!", ex);
        }
    }


}
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
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ua.dicoogle.sdk.JettyPluginInterface;
import pt.ua.dicoogle.sdk.core.DicooglePlatformInterface;
import pt.ua.dicoogle.sdk.core.PlatformCommunicatorInterface;
import pt.ua.dicoogle.sdk.settings.ConfigurationHolder;

/** IPFS Jetty Servlet plugin.
 *
 * @author Luís A. Bastião Silva - <bastiao@bmd-software.com>
 * @author Eriksson Monteiro - <eriksson.monteiro@bmd-software.com>
 */
public class IPFSJettyPlugin implements JettyPluginInterface, PlatformCommunicatorInterface {
    private static final Logger logger = LoggerFactory.getLogger(IPFSJettyPlugin.class);
    
    private boolean enabled;
    private ConfigurationHolder settings;
    private DicooglePlatformInterface platform;
    private final IPFSJettyWebService webService;
    private IPFS ipfs;
    
    public IPFSJettyPlugin(IPFS ipfs) {
        this.ipfs = ipfs;
        this.webService = new IPFSJettyWebService(ipfs);
        this.enabled = true;
    }

    @Override
    public void setPlatformProxy(DicooglePlatformInterface pi) {
        this.platform = pi;
        // since web service is not a plugin interface, the platform interface must be provided manually
        this.webService.setPlatformProxy(pi);
    }

    @Override
    public String getName() {
        return "RSI";
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
    public void setSettings(ConfigurationHolder settings) {
        this.settings = settings;
        // use settings here
    }

    @Override
    public ConfigurationHolder getSettings() {
        return settings;
    }


    @Override
    public HandlerList getJettyHandlers() {

        ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath("/ipfs");
        handler.addServlet(new ServletHolder(this.webService), "/status");
        handler.addServlet(new ServletHolder(this.webService), "/list");
        handler.addServlet(new ServletHolder(this.webService), "/chain");
        handler.addServlet(new ServletHolder(this.webService), "/hash");
        HandlerList l = new HandlerList();
        l.addHandler(handler);


        return l;
    }

}

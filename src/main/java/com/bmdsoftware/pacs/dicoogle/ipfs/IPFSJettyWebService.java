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
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ua.dicoogle.sdk.core.DicooglePlatformInterface;
import pt.ua.dicoogle.sdk.core.PlatformCommunicatorInterface;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

/**
 * IPFS Jetty Plugin - Status
 *
 * @author Luís A. Bastião Silva - <bastiao@bmd-software.com>
 * @author Eriksson Monteiro - <eriksson.monteiro@bmd-software.com>
 * @author Rui Lebre - <ruilebre@ua.pt>
 */
public class IPFSJettyWebService extends HttpServlet implements PlatformCommunicatorInterface {
    private static final Logger logger = LoggerFactory.getLogger(IPFSJettyWebService.class);

    private DicooglePlatformInterface platform;
    private IPFS ipfs = null;

    public IPFSJettyWebService(IPFS ipfs) {
        this.ipfs = ipfs;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {

        String action = req.getParameter("action");
        if (action == null) {
            response.sendError(HttpStatus.BAD_REQUEST_400, "No action provided");
            return;
        }

        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        if (action.equals("status")) {
            response.setStatus(HttpStatus.OK_200);

            out.print("{\"action\":\"status\", \"host\":\"" + ipfs.host + "\",  \"status\":" + ipfs.stats.bw().toString() + "\"}");
        } else if (action.equals("filelist")) {
            response.setStatus(HttpStatus.OK_200);
            String localFileList = ipfs.refs.local().stream().map(Object::toString).collect(Collectors.joining("\", \"", "[\"", "\"]"));

            out.print("{\"action\":\"filelist\", \"host\":\"" + ipfs.host + "\",  \"file_list\":" + localFileList + "}");
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST_400);
            out.print("{\"action\":\"no action provided\"}");
        }
    }

    @Override
    public void setPlatformProxy(DicooglePlatformInterface core) {
        this.platform = core;
    }

}

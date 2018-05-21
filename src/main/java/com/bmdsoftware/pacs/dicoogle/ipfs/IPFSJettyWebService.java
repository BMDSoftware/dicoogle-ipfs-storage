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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ua.dicoogle.sdk.core.DicooglePlatformInterface;
import pt.ua.dicoogle.sdk.core.PlatformCommunicatorInterface;
import pt.ua.dicoogle.sdk.datastructs.SearchResult;
import pt.ua.dicoogle.sdk.task.Task;

/** Sample Jetty servlet-based web service.
 *
 * @author Luís A. Bastião Silva - <bastiao@ua.pt>
 */
public class IPFSJettyWebService extends HttpServlet implements PlatformCommunicatorInterface {
    private static final Logger logger = LoggerFactory.getLogger(IPFSJettyWebService.class);
    
    private DicooglePlatformInterface platform;

    public IPFSJettyWebService() {
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response)
                    throws ServletException, IOException {

        String SOPInstanceUID = req.getParameter("uid");
        if (SOPInstanceUID == null) {
                response.sendError(402, "No UID");
                return;
        }
        HashMap<String, String> extraFields = new HashMap<>();
        //attaches the required extrafields

        extraFields.put("PatientName", "PatientName");
        extraFields.put("PatientID", "PatientID");
        extraFields.put("Modality", "Modality");
        extraFields.put("StudyDate", "StudyDate");
        extraFields.put("SeriesInstanceUID", "SeriesInstanceUID");
        extraFields.put("StudyID", "StudyID");
        extraFields.put("StudyInstanceUID", "StudyInstanceUID");
        extraFields.put("Thumbnail", "Thumbnail");
        extraFields.put("SOPInstanceUID", "SOPInstanceUID");

        // Kind of filtering: 
        //Task<Iterable<SearchResult>> result = IPFSPluginSet.coreDicoogle.query("lucene", "StudyInstanceUID:234567", extraFields);
        // Return all: 
        Task<Iterable<SearchResult>> result = this.platform.query("lucene", "*:*", extraFields);
        try {
            Iterable<SearchResult> rr = result.get();
                    
        } catch (InterruptedException | ExecutionException ex) {
            logger.warn("Operation failed", ex);
        }
        
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out=response.getWriter();
        out.print("{\"action\":\"test\"}");
    }

    @Override
    public void setPlatformProxy(DicooglePlatformInterface core) {
        this.platform = core;
    }

}

package org.led.hellorest.inbound;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class AccessLogHandler extends AbstractHandler{
	
	public void handle(String paramString, Request paramRequest,
            HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) paramHttpServletRequest;

        StringBuffer requestUrl = httpRequest.getRequestURL();
        if (httpRequest.getQueryString() != null && !httpRequest.getQueryString().trim().equals("")) {
            requestUrl.append("?");
            requestUrl.append(httpRequest.getQueryString());
        }

        // record access log
        //BmcAccessMessage accessLog = new BmcAccessMessage(httpRequest.getRemoteAddr(),
        //        httpRequest.getMethod(), requestUrl.toString(), httpRequest.getProtocol(), null,
        //        null);

        String msgType = httpRequest.getMethod();
        // filter options from monitor
        if (msgType != null && !"OPTIONS".equals(msgType)) {
            //TODO log
        }

        HttpServletResponse httpResponse = (HttpServletResponse) paramHttpServletResponse;
        //accessLog.setResultCode(httpResponse.getStatus());
        //accessLog.setDescription(BmcHttpStatus.getStatusDescription(httpResponse.getStatus()));
        // record reply access log
        // filter options from monitor
        if (msgType != null && !"OPTIONS".equals(msgType)) {
            //BmcLogger.access(accessLog);
        }

        return;

    }
}

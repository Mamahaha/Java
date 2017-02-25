package org.led.hellorest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.led.hellorest.dao.GuideDao;
import org.led.hellorest.inbound.GuideResource;
import org.led.hellorest.inbound.RestServer;
import org.led.hellorest.model.guide.Guide;

public class MainApp {
	private static RestServer server = null;
	
	public static void startServer() {
		server = new RestServer();
		server.activate();
	}
	
	public static void startGuideServer() {
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
 
        Server jettyServer = new Server(8077);
        jettyServer.setHandler(context);
 
        ServletHolder jerseyServlet = context.addServlet(
             org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
 
        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter(
           "jersey.config.server.provider.classnames",
           GuideResource.class.getCanonicalName());
 
        try {
            jettyServer.start();
            jettyServer.join();
        } catch (Exception e) {
        	
        } finally {
            jettyServer.destroy();
        }
		
		
		
	}
	public static void main(String[] args) {
		//startServer();
		startGuideServer();
	}
}

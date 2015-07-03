package org.led.hellorest.inbound;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;

import org.eclipse.jetty.http.HttpParser.HttpHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jetty.JettyHttpContainer;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class RestServer extends ResourceConfig{
	
	private static final String ADDRESS = "http://localhost/";

    private int port = 8077;

    private Server server;
    
    public RestServer() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        packages("org.led.hellorest.inbound");
    }
    
	public void start1() {
				
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
 
        server = new Server(8077);
        server.setHandler(context);
 
        ServletHolder jerseyServlet = context.addServlet(
             org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
 
        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter(
           "jersey.config.server.provider.classnames",
           HelloRest.class.getCanonicalName());
                
        try {
            server.start();
            server.join();
        } catch (Exception e) {
        	
        }
	}
	
	public void start2() {
		URI baseUri = UriBuilder.fromUri(ADDRESS).port(port).build();
		server = JettyHttpContainerFactory.createServer(baseUri, false);
		
		
		try {
            server.start();
            server.join();
        } catch (Exception e) {
        	
        }
	}
	public void stop() {
		try {
			server.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void activate() {
		this.start1();
	}
	
	public void deactivate() {
		this.stop();
	}
}

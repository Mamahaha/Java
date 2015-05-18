package org.led.hellorest.inbound;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
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
    
	public void start() {
		URI baseUri = UriBuilder.fromUri(ADDRESS).port(port).build();
		server = JettyHttpContainerFactory.createServer(baseUri, false);
		
		JettyHttpContainer container = (JettyHttpContainer) ContainerFactory.createContainer(
                JettyHttpContainer.class, this);
		
		HandlerCollection handlers = new HandlerCollection();

        handlers.addHandler(container);
        //handlers.addHandler(new AccessLogHandler());

        server.setHandler(handlers);
        
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
		this.start();
	}
	
	public void deactivate() {
		this.stop();
	}
}

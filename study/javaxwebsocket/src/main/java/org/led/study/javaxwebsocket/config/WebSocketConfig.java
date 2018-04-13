package org.led.study.javaxwebsocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.*;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.net.URI;
import java.util.Set;


@Configuration
public class WebSocketConfig {
//    @Bean
//    public  ServerContainer serverContainer() {
//        return new ServerContainer() {
//            @Override
//            public void addEndpoint(Class<?> aClass) throws DeploymentException {
//
//            }
//
//            @Override
//            public void addEndpoint(ServerEndpointConfig serverEndpointConfig) throws DeploymentException {
//
//            }
//
//            @Override
//            public long getDefaultAsyncSendTimeout() {
//                return 0;
//            }
//
//            @Override
//            public void setAsyncSendTimeout(long l) {
//
//            }
//
//            @Override
//            public Session connectToServer(Object o, URI uri) throws DeploymentException, IOException {
//                return null;
//            }
//
//            @Override
//            public Session connectToServer(Class<?> aClass, URI uri) throws DeploymentException, IOException {
//                return null;
//            }
//
//            @Override
//            public Session connectToServer(Endpoint endpoint, ClientEndpointConfig clientEndpointConfig, URI uri) throws DeploymentException, IOException {
//                return null;
//            }
//
//            @Override
//            public Session connectToServer(Class<? extends Endpoint> aClass, ClientEndpointConfig clientEndpointConfig, URI uri) throws DeploymentException, IOException {
//                return null;
//            }
//
//            @Override
//            public long getDefaultMaxSessionIdleTimeout() {
//                return 0;
//            }
//
//            @Override
//            public void setDefaultMaxSessionIdleTimeout(long l) {
//
//            }
//
//            @Override
//            public int getDefaultMaxBinaryMessageBufferSize() {
//                return 0;
//            }
//
//            @Override
//            public void setDefaultMaxBinaryMessageBufferSize(int i) {
//
//            }
//
//            @Override
//            public int getDefaultMaxTextMessageBufferSize() {
//                return 0;
//            }
//
//            @Override
//            public void setDefaultMaxTextMessageBufferSize(int i) {
//
//            }
//
//            @Override
//            public Set<Extension> getInstalledExtensions() {
//                return null;
//            }
//        };
//    }


    @Bean
    //public ServerEndpointExporter serverEndpointExporter(@Qualifier("serverContainer")ServerContainer serverContainer) {
    public ServerEndpointExporter serverEndpointExporter() {
        ServerEndpointExporter exporter = new ServerEndpointExporter();
        //exporter.setServerContainer(serverContainer);
        return exporter;
    }
}

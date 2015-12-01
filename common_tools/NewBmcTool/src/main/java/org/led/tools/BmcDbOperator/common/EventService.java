/**
 * 
 */
package org.led.tools.BmcDbOperator.common;

/**
 * @author exiaowu
 * 
 */
public interface EventService {

    void postEvent(Event event, String topic);

    void registerHandler(EventHandler handler, String[] topics);
    
    int getQueueSize(String type);
}

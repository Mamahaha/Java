package org.led.tools.bmcDbOperator.common;

import org.led.tools.bmcDbOperator.entity.Broadcast;
import org.led.tools.bmcDbOperator.entity.Cache;
import org.led.tools.bmcDbOperator.entity.Content;
import org.led.tools.bmcDbOperator.entity.Service;


public final class ContentPostStatusChangeUtil {
    
    private ContentPostStatusChangeUtil(){}

    public static void postCacheChange(Cache cache) {
        if (cache == null) {
            return;
        }
        Broadcast broadcast = cache.getOnRequest().getUserServiceInstance().getBroadcast();
        Service service = broadcast.getService();
        postChangeToBroadcast(service.getName(), broadcast.getName());
    }

    public static void postChangeToBroadcast(String serviceName, String broadcastName) {
        BMCEvent event = new BMCEvent();
        event.setTopic(GlobalConst.WEB_SOCKECT_BROADCAST_DETAIL_CHANGED_EVENT_TOPIC);
        event.getOptions().put("ServiceName", serviceName);
        event.getOptions().put("BroadcastName", broadcastName);
        if (eventService == null) {
            return;
        }
        eventService.postEvent(event, event.getTopic());
    }

    public static void postContentChange(Content content) {
        if (content == null) {
            return;
        }
        Broadcast broadcast = content.getOnRequest().getUserServiceInstance().getBroadcast();
        Service service = broadcast.getService();
        postChangeToBroadcast(service.getName(), broadcast.getName());
    }

    public static void setEventService(EventService eventService) {
        ContentPostStatusChangeUtil.eventService = eventService;
    }

    private static EventService eventService;
}

package org.led.helloxml.utils;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Mashaller {
	
	private static final String CLASS_PACKAGE = "org.led.helloxml.data.ServiceAnnouncementFile";
	
	private static Marshaller marshaller = null;
	private static Object marshallerLock = new Object();
	
	private static void init() {
		try {
            marshaller = JAXBContext
                    .newInstance(
                    		CLASS_PACKAGE,
                    		org.led.helloxml.data.ObjectFactory.class
                                    .getClassLoader()).createMarshaller();
        } catch (JAXBException e) {
            System.out.println("error: " + e);
        }
	}
	
	public static String mashaler(Object payload) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            synchronized (marshallerLock) {
                marshaller.marshal(payload, os);
            }
        } catch (JAXBException e) {
        	System.out.println("error: " + e);
        }
        return os.toString();
    }
}

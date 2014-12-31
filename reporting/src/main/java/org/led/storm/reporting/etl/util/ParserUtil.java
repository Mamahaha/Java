package org.led.storm.reporting.etl.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

import com.ericsson.bmc.oam.common.constant.ErrorCode;
import com.ericsson.bmc.oam.logging.BmcLogger;
import com.ericsson.bmc.reporting.common.BMCReportingRuntimeException;

public final class ParserUtil {
    private static final String RECEPTIONREPORT_CLASS_PACKAGE = "com.ericsson.bmc.reporting.etl.schema";
    private static Marshaller marshaller = null;
    private static Unmarshaller unmarshaller = null;
    private static Object marshallerLock = new Object();
    private static Object unmarshallerLock = new Object();

    private ParserUtil(){
    }
    
    public static void init() {
        if (marshaller == null) {
            initMarshaller();
        }
        if (unmarshaller == null) {
            initUnmarshaller();
        }
    }

    private static void initUnmarshaller() {
        try {
            unmarshaller = JAXBContext.newInstance(
                    RECEPTIONREPORT_CLASS_PACKAGE,
                    com.ericsson.bmc.reporting.etl.schema.ObjectFactory.class
                            .getClassLoader()).createUnmarshaller();
        } catch (JAXBException e) {
            
        }
    }

    private static void initMarshaller() {
        try {
            marshaller = JAXBContext.newInstance(
                    RECEPTIONREPORT_CLASS_PACKAGE,
                    com.ericsson.bmc.reporting.etl.schema.ObjectFactory.class
                            .getClassLoader()).createMarshaller();
        } catch (JAXBException e) {

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
        }
        return os.toString();
    }

    /**
     * @param filePath
     * @return
     */
    public static Object unmarshaller(String filePath) {
        try {
            synchronized (unmarshallerLock) {
                return unmarshaller.unmarshal(new FileInputStream(filePath));
            }
        } catch (JAXBException e) {

        } catch (FileNotFoundException e) {
        }
    }

    public static Object receptionReportUnmarshaller(
            XMLStreamReader xmlStreamReader) {
        try {
            synchronized (unmarshallerLock) {
                return unmarshaller
                        .unmarshal(
                                xmlStreamReader,
                                com.ericsson.bmc.reporting.etl.schema.ReceptionReportType.class);
            }
        } catch (JAXBException e) {

        }

    }

    public static Object ueReceptionReportUnmarshaller(
            XMLStreamReader xmlStreamReader) {
        try {
            synchronized (unmarshallerLock) {
                return unmarshaller
                        .unmarshal(
                                xmlStreamReader,
                                com.ericsson.bmc.reporting.etl.schema.UeReceptionReportType.class);
            }
        } catch (JAXBException e) {

        }

    }

}

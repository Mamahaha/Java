package org.led.storm.reporting.etl.rawdata;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import javax.xml.bind.JAXBElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.ericsson.bmc.oam.common.constant.ErrorCode;
import com.ericsson.bmc.oam.logging.BmcLogger;
import com.ericsson.bmc.reporting.common.BMCReportingRuntimeException;
import com.ericsson.bmc.reporting.etl.schema.UeReceptionReportType;
import com.ericsson.bmc.reporting.etl.util.ParserUtil;

public class UeLogReader {
    protected static final String SEPERATOR = "</ueReceptionReport>";

    private XMLInputFactory mXmlIf;
    private BufferedReader mBufReader;

    public UeLogReader() {}

    public boolean open(ByteArrayInputStream inputStream) {
        try {
            mBufReader = new BufferedReader(new InputStreamReader(
                    new DataInputStream(inputStream)));
            mXmlIf = XMLInputFactory.newInstance();
        } catch (Exception e) {
            BmcLogger.eventError(BmcLogger.DOMAIN_ETL,
                    ErrorCode.UNMARSHALL_XML_FAILED, e.getMessage());
            BmcLogger.eventDebug(BmcLogger.DOMAIN_ETL, this.getClass()
                    .getName(), e);
            return false;
        }

        return true;
    }

    public void close() {
        try {
            if (mBufReader != null) {
                mBufReader.close();
            }
        } catch (IOException e) {
            BmcLogger.eventInfo(BmcLogger.DOMAIN_ETL, "Close IO: %s Failed", "mBufReader");
        }
    }

    public UeReceptionReportType readReport() {
        UeReceptionReportType report = null;

        try {
            report = getReport();
        } catch (IOException e) {
            BmcLogger.eventError(BmcLogger.DOMAIN_ETL,
                    ErrorCode.UNMARSHALL_XML_FAILED, e.getMessage());
            BmcLogger.eventDebug(BmcLogger.DOMAIN_ETL, this.getClass()
                    .getName(), e);
            return null;
        } catch (BMCReportingRuntimeException e) {
            BmcLogger.eventError(BmcLogger.DOMAIN_ETL,
                    ErrorCode.UNMARSHALL_XML_FAILED, e.getMessage());
            BmcLogger.eventDebug(BmcLogger.DOMAIN_ETL, this.getClass()
                    .getName(), e);
            return null;
        } catch (XMLStreamException e) {
            BmcLogger.eventError(BmcLogger.DOMAIN_ETL,
                    ErrorCode.UNMARSHALL_XML_FAILED, e.getMessage());
            BmcLogger.eventDebug(BmcLogger.DOMAIN_ETL, this.getClass()
                    .getName(), e);
            return null;
        }

        return report;
    }

    private UeReceptionReportType getReport() throws IOException, XMLStreamException {
        UeReceptionReportType report = null;
        StringBuilder strBuilder = new StringBuilder();
        String line = null;
        while ((line = mBufReader.readLine()) != null) {
            strBuilder.append(line.trim());

            if (line.toLowerCase().contains(SEPERATOR.toLowerCase())) {

                StringReader reader = new StringReader(strBuilder.toString());
                XMLStreamReader xsr = mXmlIf.createXMLStreamReader(reader);

                if (xsr.hasNext()) {
                    xsr.nextTag();
                    if (xsr.getLocalName().equalsIgnoreCase("ueReceptionReport")) {
                        @SuppressWarnings("unchecked")
                        JAXBElement<UeReceptionReportType> element = (JAXBElement<UeReceptionReportType>) ParserUtil
                                .ueReceptionReportUnmarshaller(xsr);
                        report = (UeReceptionReportType) element.getValue();
                    }
                }

                xsr.close();
                return report;
            }
        }
        return report;
    }
}

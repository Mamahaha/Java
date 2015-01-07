package org.led.storm.reporting.etl.rawdata;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import com.ericsson.bmc.oam.common.constant.ErrorCode;
import com.ericsson.bmc.oam.logging.BmcLogger;
import com.ericsson.bmc.reporting.common.BMCReportingRuntimeException;
import com.ericsson.bmc.reporting.etl.schema.ReceptionReportType;
import com.ericsson.bmc.reporting.etl.util.ParserUtil;

public class ReceptionReportReader {
    private static final String START_PATTERN = "(<[^/<>:]+:|<)receptionReport";
    private static final String END_PATTERN = "(</[^/<>:]+:|</)receptionReport>";

    private XMLInputFactory mXmlIf;

    public boolean init() {
        try {
            mXmlIf = XMLInputFactory.newInstance();
        } catch (Exception e) {

            return false;
        }

        return true;
    }

    public ReceptionReportType readReport(List<String> xmlStrList) {
        String xmlString = null;
        if ((xmlStrList == null) || (xmlStrList.size() != 1)) {
            return null;
        }

        xmlString = xmlStrList.remove(0);
        if (xmlString == null) {
            return null;
        }

        ReceptionReportType report = null;

        int startIndex = xmlString.split(START_PATTERN, 2)[0].length();
        String[] tmp = xmlString.split(END_PATTERN, 2);
        int offset = tmp.length < 2 ? 0 : tmp[1].length();
        int endIndex = xmlString.length() - offset;

        if ((startIndex == -1) || (endIndex == -1) || (endIndex <= startIndex)) {
            return null;
        }
        String str = xmlString.substring(startIndex, endIndex);

        if (offset != 0) {
            // prepare for dealing with next ReceptionReport under the same ueReceptionReport
            String s = xmlString.substring(endIndex + 1);
            xmlStrList.add(s);
        }

        StringReader reader = new StringReader(str);

        XMLStreamReader xsr;
        try {
            xsr = mXmlIf.createXMLStreamReader(reader);

            if (xsr.hasNext()) {
                xsr.nextTag();
                if (xsr.getLocalName().equals("receptionReport")) {

                    @SuppressWarnings("unchecked")
                    JAXBElement<ReceptionReportType> element = (JAXBElement<ReceptionReportType>) ParserUtil
                            .receptionReportUnmarshaller(xsr);
                    report = (ReceptionReportType) element.getValue();

                }
            }

            xsr.close();
        } catch (Exception e) {

            return null;
        }

        return report;
    }
}

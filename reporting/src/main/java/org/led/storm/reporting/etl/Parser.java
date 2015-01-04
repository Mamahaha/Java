package org.led.storm.reporting.etl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;

import com.ericsson.bmc.oam.common.constant.ErrorCode;
import com.ericsson.bmc.oam.logging.BmcLogger;
import com.ericsson.bmc.reporting.avro.FileUriRec;
import com.ericsson.bmc.reporting.avro.ReceptionReport;
import com.ericsson.bmc.reporting.common.util.TimeUtil;
import com.ericsson.bmc.reporting.download.common.EtlException;
import com.ericsson.bmc.reporting.etl.rawdata.ReceptionReportReader;
import com.ericsson.bmc.reporting.etl.rawdata.UeLogReader;
import com.ericsson.bmc.reporting.etl.schema.FileUriType;
import com.ericsson.bmc.reporting.etl.schema.ReceptionReportType;
import com.ericsson.bmc.reporting.etl.schema.SessionTypeType;
import com.ericsson.bmc.reporting.etl.schema.StarType;
import com.ericsson.bmc.reporting.etl.schema.UeReceptionReportType;

public class Parser {
    private String mBroadcastId;

    private DataFileWriter<ReceptionReport> mReceptionReportFileWriter;

    private String mReceptionReportOutputFilename;
    private String mLocalReceptionReportOutputFilepath;
    private String mBmscId;
    private String mAdfHost;
    private String mAvroRootPath;
    private boolean isContinous = true;

    private int mTotalCount;
    private static final int COMPRESSION_LEVEL = 9;

    public Parser() {}

    public boolean init(String outputPath, String broadcastId, String bmscId,
            String adfHost, String avroRootPath, boolean compressFlag) {
        String mLocalOutputPath = outputPath;
        mBroadcastId = broadcastId;
        mBmscId = bmscId;
        mAdfHost = adfHost.replace(':', '_');
        mAvroRootPath = avroRootPath;
        boolean mCompressFlag = compressFlag;
        mTotalCount = 0;

        mReceptionReportOutputFilename = mBroadcastId + "_" + mBmscId + "_"
                + mAdfHost + ".avro";

        if (!mLocalOutputPath.endsWith(File.separator)) {
            mLocalOutputPath = mLocalOutputPath + File.separator;
        }
        mLocalReceptionReportOutputFilepath = mLocalOutputPath
                + mReceptionReportOutputFilename;

        if (!checkAndDeleteExistingFile(mLocalReceptionReportOutputFilepath)) {
            
            return false;
        }

        if (!mAvroRootPath.endsWith(File.separator)) {
            mAvroRootPath = mAvroRootPath + File.separator;
        }

        try {
            File mReceptionReportOutputFile = new File(
                    mLocalReceptionReportOutputFilepath);

            // for report file
            DatumWriter<ReceptionReport> mReceptionReportDatumWriter = new SpecificDatumWriter<ReceptionReport>(
                    ReceptionReport.class);
            mReceptionReportFileWriter = new DataFileWriter<ReceptionReport>(
                    mReceptionReportDatumWriter);
            if (mCompressFlag) {
                // set compression method
                mReceptionReportFileWriter.setCodec(CodecFactory
                        .deflateCodec(Parser.COMPRESSION_LEVEL));
            }

            mReceptionReportFileWriter.create(ReceptionReport.getClassSchema(),
                    mReceptionReportOutputFile);
            return true;

        } catch (IOException e) {
            
            return false;
        }
    }

    private boolean checkAndDeleteExistingFile(String filePath) {
        try {
            File f = new File(filePath);
            if (f.exists()) {
                f.delete();
            }
        } catch (Exception e) {
            
            return false;
        }
        return true;
    }

    public void close() throws EtlException {
        try {
            mReceptionReportFileWriter.close();


        } catch (IOException e) {
            
            throw new EtlException(e);
        }
    }

    public String getLocalOutputFilepath() {
        return mLocalReceptionReportOutputFilepath;
    }

    public void parseXmlAndWriteAvro(ByteArrayInputStream bin, Long dsiId,
            String reportType) {


        int count = 0;

        try {
            long startTime = System.currentTimeMillis();

            count = parseLog(bin, dsiId, reportType);

            mReceptionReportFileWriter.flush();

            mTotalCount += count;

            long stopTime = System.currentTimeMillis();

            
        } catch (Exception e) {
            
        }
    }

    public int parseLog(ByteArrayInputStream inputStream, Long dsiId,
            String reportType) {
        UeLogReader ueLogReader = new UeLogReader();
        if (!ueLogReader.open(inputStream)) {
            return 0;
        }

        ReceptionReportReader rrReader = new ReceptionReportReader();
        if (!rrReader.init()) {
            return 0;
        }
        // the count of parsed reception report
        int count = 0;
        try {
            // from xml
            UeReceptionReportType ueReport;
            // from xml
            ReceptionReportType report;
            while ((ueReport = ueLogReader.readReport()) != null) {
                String xmlStr = ueReport.getRequestXML();
                List<String> strList = new ArrayList<String>();
                strList.add(xmlStr);

                while ((report = rrReader.readReport(strList)) != null) {
                    // xml
                    List<StarType> statisticalReport = report
                            .getStatisticalReport();
                    if (statisticalReport.size() == 0) {
                        
                        continue;
                    }

                    if (processStatisticalReport(statisticalReport, dsiId,
                            reportType) > 0) {
                        count++;
                    }
                }
            }
        } catch (IOException e) {
            
            return 0;
        }

        return count;
    }

    public int processStatisticalReport(List<StarType> statisticalReport,
            Long dsiId, String reportType) throws IOException {
        int numValidStatisticalReport = 0;
        for (StarType statistics : statisticalReport) {
            ReceptionReport avroRR = new ReceptionReport();

            if ((statistics.getClientId() == null)
                    || statistics.getClientId().isEmpty()) {
                
                continue;
            }

            if ((statistics.getServiceId() == null)
                    || statistics.getServiceId().isEmpty()) {
                
                continue;
            }

            if ((statistics.getQoeMetrics() == null)
                    || (statistics.getQoeMetrics().getSessionStartTime() == null)) {
                
                continue;
            }

            if ((statistics.getQoeMetrics() == null)
                    || (statistics.getQoeMetrics().getSessionStopTime() == null)) {
                
                continue;
            }

            if ((reportType != null) && (reportType.equalsIgnoreCase("STAR_ONLY"))) {
                if (statistics.getQoeMetrics().getNumberOfReceivedObjects()
                        .size() == 0) {
                    
                    continue;
                }
                if (statistics.getQoeMetrics().getNumberOfLostObjects().size() == 0) {
                    
                    continue;
                }

            }

            if (!buildReceptionReport(avroRR, statistics, dsiId)) {
                continue;
            }

            mReceptionReportFileWriter.append(avroRR);
            numValidStatisticalReport += 1;
        }
        return numValidStatisticalReport;
    }

    public boolean buildReceptionReport(ReceptionReport receptionReport,
            StarType statistics, Long dsiId) {
        List<CharSequence> cellIds = new ArrayList<CharSequence>();
        List<Integer> receivedObjs = new ArrayList<Integer>();
        List<Integer> lostObjs = new ArrayList<Integer>();

        receptionReport.setClientId(statistics.getClientId());
        receptionReport
                .setServiceId((statistics.getServiceId() != null) ? statistics
                        .getServiceId() : "");
        receptionReport
                .setServiceURI((statistics.getServiceURI() != null) ? statistics
                        .getServiceURI() : "");
        receptionReport
                .setSessionType((statistics.getSessionType() != null) ? statistics
                        .getSessionType().toString() : "");
        // if the RR report doesn't contains the session type, set the value
        // according to the PUSH request 
        if (receptionReport.getSessionType().toString().isEmpty()) {
            receptionReport.setSessionType(isContinous ?
                    SessionTypeType.STREAMING.value() : SessionTypeType.DOWNLOAD.value());
        }
        receptionReport.setSessionStartTime(0L);
        receptionReport.setSessionStopTime(0L);
        receptionReport.setSessionId("");
        if (statistics.getQoeMetrics() != null) {
            if (statistics.getQoeMetrics().getSessionStartTime() != null) {
                receptionReport.setSessionStartTime(TimeUtil
                        .getTimeFromNtp(statistics.getQoeMetrics()
                                .getSessionStartTime().longValue()));
            }
            if (statistics.getQoeMetrics().getSessionStopTime() != null) {
                receptionReport.setSessionStopTime(TimeUtil
                        .getTimeFromNtp(statistics.getQoeMetrics()
                                .getSessionStopTime().longValue()));
            }

            if ((receptionReport.getSessionStartTime() > 0)
                    && (receptionReport.getSessionStopTime() > 0)
                    && (receptionReport.getSessionStartTime() >= receptionReport
                            .getSessionStopTime())) {
                
                return false;
            }
            if (statistics.getQoeMetrics().getMedialevelQoeMetrics().size() > 0) {
                receptionReport.setSessionId(statistics.getQoeMetrics()
                        .getMedialevelQoeMetrics().get(0).getSessionId());
            }

            if (!buildCellIds(statistics, cellIds)) {
                
                return false;
            }
            buildReceiveLostObjs(statistics, receivedObjs, lostObjs);
        }

        receptionReport.setNetworkResourceCellId(cellIds);

        receptionReport.setDeliverySessionInstanceInfoId(dsiId);

        receptionReport.setNumOfReceivedObjects(receivedObjs);

        receptionReport.setNumOfLostObjects(lostObjs);

        receptionReport.setFileUriList(buildFileUris(statistics));

        return true;
    }

    public boolean buildCellIds(StarType statistics, List<CharSequence> cellIds) {
        for (String id : statistics.getQoeMetrics().getNetworkResourceCellId()) {
            if (id.equals("=")) {
                if (cellIds.size() < 1) {
                    return false;
                } else {
                    cellIds.add(cellIds.get(cellIds.size() - 1));
                }
            } else {
                cellIds.add(id);
            }
        }

        if (cellIds.size() == 0) {
            return false;
        }

        return true;
    }

    public void buildReceiveLostObjs(StarType statistics,
            List<Integer> receivedObjs, List<Integer> lostObjs) {
        for (BigInteger num : statistics.getQoeMetrics()
                .getNumberOfReceivedObjects()) {
            receivedObjs.add(num.intValue());
        }
        for (BigInteger num : statistics.getQoeMetrics()
                .getNumberOfLostObjects()) {
            lostObjs.add(num.intValue());
        }
    }

    @SuppressWarnings("restriction")
    public List<FileUriRec> buildFileUris(StarType statistics) {

        List<FileUriType> srcFileUriList = statistics.getFileURI();
        List<FileUriRec> destFileUriList = new ArrayList<FileUriRec>();
        for (FileUriType fileUri : srcFileUriList) {
            FileUriRec fur = new FileUriRec();
            fur.setFileURI(fileUri.getValue());
            fur.setReceptionSuccess(fileUri.isReceptionSuccess());
            fur.setContentMD5(new sun.misc.BASE64Encoder().encode(fileUri
                    .getContentMD5()));
            destFileUriList.add(fur);
        }
        return destFileUriList;
    }

    public boolean isContinous() {
        return isContinous;
    }

    public void setContinous(boolean isContinous) {
        this.isContinous = isContinous;
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.net.Socket;
import java.net.InetAddress;
import java.util.Map;
import java.util.HashMap;

import com.tailf.cdb.Cdb;
import com.tailf.conf.ConfBuf;
import com.tailf.conf.ConfDatetime;
import com.tailf.conf.ConfException;
import com.tailf.conf.ConfIdentityRef;
import com.tailf.conf.ConfKey;
import com.tailf.conf.ConfPath;
import com.tailf.maapi.Maapi;
import com.tailf.maapi.MaapiUserSessionFlag;
import com.tailf.ncs.ApplicationComponent;
import com.tailf.ncs.NcsMain;
import com.tailf.ncs.alarmman.common.Alarm;
import com.tailf.ncs.alarmman.common.AlarmId;
import com.tailf.ncs.alarmman.common.ManagedDevice;
import com.tailf.ncs.alarmman.common.ManagedObject;
import com.tailf.ncs.alarmman.common.PerceivedSeverity;
import com.tailf.ncs.alarmman.producer.AlarmSink;

public class AlarmOperator {
    private static final int hash = 959898198;
    private static final String RAISE = "raise";
    private static final String CLEAR = "clear";

    private static int mode = 0;
    private static String managedDevice;
    private static String managedObj;
    private static int alarmId;
    private static PerceivedSeverity severity = PerceivedSeverity.MAJOR;
    private static String alarmText;
    private static Map alarmMap = new HashMap();
    
    public static void main(String[] args) {
        if (!initAlarmList()) {
            System.exit(1);
        }
        
        if (!parseParams(args)) {
            System.exit(1);
        }

        switch (mode) {
            case 1:
                raiseAlarm();
                break;
            case 2:
                clearAlarm();
                break;
            default:
                System.out.println("ERROR: The alarm operation is invalid.");
                break;
        }
    }

    private static boolean initAlarmList() {
        String alarmFile = "./alarm_definition.txt";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(alarmFile));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                String[] subLines = line.split("=");
                alarmMap.put(Integer.parseInt(subLines[1]), subLines[0]);
                line = br.readLine();
            }
            br.close();
            System.out.println("Load and initialize alarm list successfully.");
            return true;
        } catch (Exception e) {
            try {
                br.close();
            } catch (IOException e1) {
                System.out.println("ERROR: Failed to close file.");
            }
            System.out.println("ERROR: Failed to load and initialize alarm list : " + e);
            return false;
        } 
    }
    
    private static boolean parseParams(String[] args) {
        /*if (args.length != 6) {
            //displayUsage();
            System.out.println("ERROR: not enough args: " + args.length);
            return false;
        }*/
        
        if (args[0].equals(RAISE)) {
            mode = 1;
            severity = PerceivedSeverity.valueOf(args[4]);            
        } else if (args[0].equals(CLEAR)) {
            mode = 2;
        } else {        	  
            //displayUsage();
            return false;
        }
        
        managedDevice = args[1];
        managedObj = args[2];
        try {
          	alarmId = Integer.parseInt(args[3]);
          	if (!alarmMap.containsKey(alarmId)) {
            		System.out.println("ERROR: The alarm ID is invalid : " + alarmId);
            		//displayUsage();
            		return false;
          	}
        } catch(Exception e) {
        	  System.out.println("ERROR: The alarm ID is invalid : " + alarmId);
    		    //displayUsage();
        	  return false;
        }        
        
        alarmText = args[5];
        
        return true;
    } 

    private static void displayUsage() {
        System.out.println("Usage: ./alarmOperator.sh <raise/clear> <managed device> <managed object> <alarm ID> <severity> <\"alarm text\">");
        System.out.println("Example: ./alarmOperator.sh raise BMC SDCH MAJOR 449153262 \"Setting is missing sa service/class.\"");
    }
    
    private static void raiseAlarm() {
        try {
            if (!operateAlarm(severity)) {
                System.out.println("ERROR: Failed to raise alarm");
            }
        } catch (Exception e) {
            System.out.println("ERROR: Failed to raise alarm:" + e);
        }
    }
    
    private static void clearAlarm() {
        try {
            if (!operateAlarm(PerceivedSeverity.CLEARED)) {
                System.out.println("ERROR: Failed to clear alarm");
            }
        } catch (Exception e) {
            System.out.println("ERROR: Failed to clear alarm:" + e);
        }
    }
    
    private static boolean operateAlarm(PerceivedSeverity svt) throws Exception {
        Socket socket = null;
        System.out.println("Start sending alarm: ID<" + alarmId + ">, Type<" +
                alarmMap.get(alarmId) + ">");
        try {
            socket = new Socket(NcsMain.getInstance().getNcsHost(), 
                NcsMain.getInstance().getNcsPort() );
            Cdb cdb = new Cdb(AlarmOperator.class.getName(), socket);
            AlarmSink alarmSink = new AlarmSink(cdb);
            Alarm alarm = new Alarm(new ManagedDevice(managedDevice), 
                    new ManagedObject(managedObj),
                    new ConfIdentityRef(hash, alarmId), 
                    svt,
                    false, alarmText, 
                    new ArrayList<ManagedObject>(), 
                    new ArrayList<AlarmId>(), 
                    new ArrayList<ManagedObject>(),
                    ConfDatetime.getConfDatetime());
                    
            alarmSink.submitAlarm(alarm);
            
            socket.close();
            System.out.println("Send alarm successfully");
            return true;
        } catch (Exception e1) {
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println("ERROR: close socket failed: " + e);
            }
            System.out.println("ERROR: Failed to operate alarm: " + e1);
            return false;
        }
    }    
}

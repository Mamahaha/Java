#!/usr/bin/python

import sys
import subprocess

def run_cmd(cmd):
  '''
  use subprocess.popen() to run commands
  '''
  try:
    p = subprocess.Popen(cmd, shell=True, stdin=subprocess.PIPE, stdout=subprocess.PIPE)
    (result, err) = p.communicate(cmd)
    return (result, err)
  except Exception, ex:
    print 'ERROR: Failed to run command <%s>' %cmd
    raise ex

def display_usage():
  print '\n*=======================================================================================================================*'
  print '\033[7m* Name        : Alarm Operator'
  print '* Description : This tool is used to raise/clear an appointed BMC Alarm from BMC to OSS.'
  print '* Usage       : ./alarm_operator.py <operation> <managed device> <managed object> <alarm ID> <severity> <"alarm text">'
  print '*               allowed values of <operation> : raise / clear'
  print '*               allowed values of <severity>  : INDETERMINATE / CRITICAL / MAJOR / MINOR / WARNING'
  print '* Example     : ./alarm_operator.py raise BMC SDCH 449153262 MAJOR "Setting is missing sa service/class."'
  print '* Note        : The "alarm ID" is defined in file "alarm_definition.txt".\033[0m'
  print '*=======================================================================================================================*\n'

def validate_mode(mode):
  if not mode in ('raise', 'clear'):
    return False
  return True

def validate_severity(severity):
  if not severity in ('INDETERMINATE', 'CRITICAL', 'MAJOR', 'MINOR', 'WARNING'):
    return False
  return True

def operate_alarm(mode, managedDevice, managedObj, alarmId, severity, alarmText):
  #cmd = 'java -cp .:/home/led/work/temp/bmc_alarm_tool/lib/* -jar AlarmOperator.jar %s %s %s %s %s "%s" 2>&1' %(mode, managedDevice, managedObj, alarmId, severity, alarmText)
  cmd = 'java -jar AlarmOperator.jar %s %s %s %s %s "%s" 2>&1' %(mode, managedDevice, managedObj, alarmId, severity, alarmText)
  (result, err) = run_cmd(cmd)
  lines = result.split('\n')
  for line in lines:
    if not 'log4j:' in line:
      print line
      
if __name__ == '__main__':
  if len(sys.argv) != 7:
    display_usage()
    #print 'ERROR: parameter count is wrong:', len(sys.argv)
    exit(1)

  mode = sys.argv[1].lower()
  if not validate_mode(mode):
    #display_usage()
    print 'ERROR: operation is wrong:', sys.argv[1]
    exit(1)
    
  managedDevice = sys.argv[2]
  managedObj = sys.argv[3]
  alarmId = sys.argv[4]
  severity = sys.argv[5].upper()
  if not validate_severity(severity):
    #display_usage()
    print 'ERROR: severity is wrong:', sys.argv[5]
    exit(1)
    
  alarmText = sys.argv[6]
  
  operate_alarm(mode, managedDevice, managedObj, alarmId, severity, alarmText)
  
  

  

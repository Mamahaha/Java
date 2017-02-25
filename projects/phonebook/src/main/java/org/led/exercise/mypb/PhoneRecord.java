
 

package org.led.exercise.mypb;

/**
 * phone record structure and related operation
 * 
 */
public final class PhoneRecord {
	//Both name and number are keys for a record
	public String name;
	public String number;
	public String address;
	
	public PhoneRecord() {
		
	}
	
	/**
	 * init a record attributes with necessary info
	 * 
	 * @param iName
	 *            user name
	 * @param iNumber
	 *            user number
	 * @param iAddress
	 *            user address            
	 * @return null
	 */
	public PhoneRecord(String iName, String iNumber, String iAddress) {
		name = iName;
		number = iNumber;
		address = iAddress;
	}

	/**
	 * set a record attributes with necessary info
	 * 
	 * @param iName
	 *            user name
	 * @param iNumber
	 *            user number
	 * @param iAddress
	 *            user address            
	 * @return null
	 */
	public void setRecord(String iName, String iNumber, String iAddress) {
		name = iName;
		number = iNumber;
		address = iAddress;
	}
	
	/**
	 * set a record attributes with an input record
	 * 
	 * @param iRecord
	 *            input record         
	 * @return null
	 */
	public void setRecord(PhoneRecord iRecord) {
		name = iRecord.name;
		number = iRecord.number;
		address = iRecord.address;
	}
	
	/**
	 * transfer record info to string for display
	 * 
	 * @param 
	 *                  
	 * @return record info string
	 */
	public String toString() { 
		return name + " | " + number + " | " + address;
	}
}



// Package
package org.led.exercise.mypb;

// Imports
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * Phone book structure and related operations
 * 
 */
public final class PhoneBook {
	//"records" that set to "public" is just for unit test
	public List<PhoneRecord> records;	
	private static Noter noter = new Noter();
	
	public PhoneBook() {
		records = new ArrayList<PhoneRecord>();
	}
	
	/**
	 * display all input phone records info in a certain format
	 * 
	 * @param recs
	 *        phone record list that needs to show    
	 * @return null
	 */
	public void displayRecords(List<PhoneRecord> recs) {
		if(null != recs) {
			noter.show("Total record size: " + recs.size() + "");
			for (int i=0; i<recs.size(); i++) {
				noter.show(i + ": " + recs.get(i).toString());
			}
		}
	}
	
	/**
	 * load and parse a phone book xml file into memory. The file is always in the current folder.
	 * 
	 * @param xmFile
	 *            xml file name.
	 * @return true if loading succeeded
	 */
	public boolean xml2List(String xmlFile) {
		boolean result = false;

		if(!new File(xmlFile).exists()) {
			Document doc = DocumentHelper.createDocument();
			doc.addElement("phonebook");
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("GBK");     
			XMLWriter writer = null;
			try {
				writer = new XMLWriter(new FileWriter(xmlFile),format);
				writer.write(doc);
				writer.close();
				result = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				SAXReader reader = new SAXReader();
				Document doc = null;
				doc = reader.read(new File(xmlFile));
				
				Element root = doc.getRootElement();
				@SuppressWarnings("unchecked")
				List<Element> nodes = root.elements("record");
				for(Iterator<Element> it = nodes.iterator(); it.hasNext();) {
					Element elm = (Element)it.next();
					String name = elm.elementText("name");
					if(null != name && "" != name) {
						String number = elm.elementText("number");
						String address = elm.elementText("address");
						records.add(new PhoneRecord(name, number, address));
					}
				}
				result = true;
			} catch (DocumentException e) {
				e.printStackTrace();
			}		
		}
		
		return result;
	}
	
	/**
	 * save phone book info into xml file. The file is always in the current folder.  
	 * If the xml file does not exist, the application will create a new one
	 * 
	 * @param xmlFile 
	 *            xml file name
	 * @return true if saving succeeded
	 */
	public boolean list2Xml(String xmlFile) {
		boolean result = false;

		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("phonebook");
		for(int i=0; i<records.size(); i++) {
			Element recordElm = root.addElement("record");
			Element nameElm = recordElm.addElement("name");
			nameElm.setText(records.get(i).name);
			Element numberElm = recordElm.addElement("number");
			numberElm.setText(records.get(i).number);
			Element addressElm = recordElm.addElement("address");
			addressElm.setText(records.get(i).address);
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GBK");       
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new FileWriter(xmlFile),format);
			writer.write(doc);
			writer.close();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		           		
		return result;
	}
	
	/**
	 * display all record info in the phone book
	 * 
	 * @param 
	 *            
	 * @return true if operation succeeded
	 */
	public boolean displayAll() {
		boolean result = false;
		String errCode = null;
		
		if(null == records) {
			errCode = "[Error]: Phone book does not exist";
		} else {
			displayRecords(records);
			result = true;
		}
		
		if(!result) {
			System.out.println(errCode);
		}
		return result;
	}
	
	/**
	 * add a record into phone book
	 * 
	 * @param name
	 *            user name
	 * @param number
	 *            user number
	 * @param address
	 *            user address            
	 * @return true if operation succeeded
	 */
	public boolean add(String name, String number, String address) {
		boolean result = false;
		String errCode = null;
		
		if(null == name || null == number) {
			errCode = "[Error]: Invalid input";
		} else {
			PhoneRecord record = new PhoneRecord();
			record.setRecord(name, number, address);
			boolean isExist = false;
			for(PhoneRecord rec : records) {
				if(rec.name.equals(record.name) || rec.number.equals(record.number)) {
					isExist = true;
					break;
				}
			}
			if(isExist) {
				errCode = "[Error]: A record with the name or number has already existed";
			} else {
				result = true;
				records.add(record);
			}
		}

		if(!result) {
			noter.show(errCode);
		}
		return result;
	}

	/**
	 * edit a record of assigned name
	 * 
	 * @param name
	 *            user name
	 * @param newNumber
	 *            new user number
	 * @param newAddress
	 *            new user address            
	 * @return true if operation succeeded
	 */
	public boolean editByName(String name, String newNumber, String newAddress) {
		boolean result = false;
		String errCode = null;
		
		if(null == name || null == newNumber) {
			errCode = "[Error]: Invalid input";
		} else {
			PhoneRecord record = new PhoneRecord();
			record.setRecord(name, newNumber, newAddress);
			boolean isExist = false;
			for(PhoneRecord rec : records) {
				if(rec.name.equals(record.name)) {
					isExist = true;
					rec.setRecord(record);
					break;
				}
			}
			if(isExist) {
				result = true;
			} else {
				errCode = "[Error]: Record with input name does not exist";
			} 
		}
		
		if(!result) {
			noter.show(errCode);
		}		
		return result;
	} 
	
	/**
	 * query records that match assigned name
	 * 
	 * @param name
	 *            queried name prefix. "*" matches all records
	 * @return true if operation succeeded
	 */
	public List<PhoneRecord> queryByName(String name) {
		boolean result = false;
		String errCode = null;
		List<PhoneRecord> subRecords = new ArrayList<PhoneRecord>();
		
		if(null == name || "" == name) {
			errCode = "[Error]: Invalid input";
		} else {
			if(name.equals("*")) {
				//subRecords = pb.records;
				for(PhoneRecord rec : records) {
					subRecords.add(rec);
				}
				result = true;
			} else {
				boolean isExist = false;
				for(PhoneRecord rec : records) {
					if(rec.name.startsWith(name)) {
						isExist = true;
						subRecords.add(rec);
					}
				}
				if(isExist) {
					result = true;
				} else{
					errCode = "[Error]: No matched record exists";
				} 
			}
		}

		if(!result) {
			noter.show(errCode);
		}
		
		displayRecords(subRecords);
		return subRecords;
	}
	
	/**
	 * remove records that match assigned name
	 * 
	 * @param name
	 *            assigned name prefix. "*" matches all records
	 * @return true if operation succeeded
	 */
	public boolean removeByName(String name) {
		boolean result = false;
		String errCode = null;
		int i = 0;
		
		if(null == name) {
			errCode = "[Error]: Invalid input";
		} else {
			if(name.equals("*")) {
				i = records.size();
				records.clear();
			} else {
				List<PhoneRecord> tempRecords = new ArrayList<PhoneRecord>();
				for(PhoneRecord rec : records) {
					if(rec.name.startsWith(name)) {
						tempRecords.add(rec);
					}
				}
				
				i = tempRecords.size();
				if(i > 0) {
					records.removeAll(tempRecords);
					result = true;
				} else {
					errCode = "[Error]: No matched records exists";
				} 
			}
		}
		
		if(!result) {
			noter.show(errCode);
		}
		
		noter.show("Totally " + i + " matched records are removed");
		return result;
	}
}

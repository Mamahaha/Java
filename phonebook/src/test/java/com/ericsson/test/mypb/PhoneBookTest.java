package com.ericsson.test.mypb;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ericsson.mvn.mypb.PhoneBook;
import com.ericsson.mvn.mypb.PhoneRecord;

public class PhoneBookTest {
	private final String pbXml = "myphonebook.xml";
	private PhoneBook pb = new PhoneBook();
	
	@Before
	public void setUp() throws Exception {
		pb.records.clear();
	}

	@Test
	public void testXml2List() {
		String name = "bbc";
		String number = "21321";
		String address = "ddd";
		pb.add(name, number, address);
		pb.list2Xml(pbXml);
		
		pb.records.clear();
		boolean result = pb.xml2List(pbXml);
		assertEquals(true, result);
		assertEquals(name, pb.records.get(0).name);
		assertEquals(number, pb.records.get(0).number);
		assertEquals(address, pb.records.get(0).address);
	}

	@Test
	public void testList2Xml() {
		String name = "bbc";
		String number = "21321";
		String address = "ddd";
		pb.add(name, number, address);
		boolean result = pb.list2Xml(pbXml);
		assertEquals(true, result);
		
		pb.records.clear();
		pb.xml2List(pbXml);
		assertEquals(name, pb.records.get(0).name);
		assertEquals(number, pb.records.get(0).number);
		assertEquals(address, pb.records.get(0).address);
	}

	@Test
	public void testDisplayAll() {
		boolean result = pb.displayAll();
		assertEquals(true, result);		
	}

	@Test
	public void testAdd1() {
		String name = "bbc";
		String number = "21321";
		String address = "ddd";
		//correct case
		boolean result = pb.add(name, number, address);
		assertEquals(true, result);
		assertEquals(name, pb.records.get(0).name);
		assertEquals(number, pb.records.get(0).number);
		assertEquals(address, pb.records.get(0).address);	
	}
	
	@Test
	public void testAdd2() {
		String name = "bbc";
		String number = "21321";
		String address = "ddd";
		//wrong case : duplicate name or number
		pb.add(name, number, address);				
		assertEquals(false, pb.add(name, "33333", address));
		assertEquals(false, pb.add("bbd", number, address));		
	}

	@Test
	public void testEditByName1() {
		String name = "bbc";
		String number = "21321";
		String address = "ddd";
		String newNumber = "34343";
		String newAddress = "fdaee";
		pb.add(name, number, address);
		
		//correct case
		boolean result = pb.editByName(name, newNumber, newAddress);
		assertEquals(true, result);
		assertEquals(name, pb.records.get(0).name);
		assertEquals(newNumber, pb.records.get(0).number);
		assertEquals(newAddress, pb.records.get(0).address);
	}
	
	@Test
	public void testEditByName2() {
		String name = "bbc";
		String number = "21321";
		String address = "ddd";
		String newNumber = "34343";
		String newAddress = "fdaee";
		pb.add(name, number, address);
		
		//wrong case : no such name
		assertEquals(false, pb.editByName("bbd", newNumber, newAddress));
	}

	@Test
	public void testQueryByName1() {
		String name1 = "bbc";
		String number1 = "21321";
		String address1 = "ddd";
		String name2 = "bbd";
		String number2 = "1321";
		String address2 = "daa";
		pb.add(name1, number1, address1);
		pb.add(name2, number2, address2);
		
		List<PhoneRecord> subRecords = null;
		//correct case
		subRecords = pb.queryByName("bb");
		assertEquals(name1, subRecords.get(0).name);
		assertEquals(number1, subRecords.get(0).number);
		assertEquals(address1, subRecords.get(0).address);
		assertEquals(name2, subRecords.get(1).name);
		assertEquals(number2, subRecords.get(1).number);
		assertEquals(address2, subRecords.get(1).address);
	}

	@Test
	public void testQueryByName2() {
		String name1 = "bbc";
		String number1 = "21321";
		String address1 = "ddd";
		String name2 = "bbd";
		String number2 = "1321";
		String address2 = "daa";
		pb.add(name1, number1, address1);
		pb.add(name2, number2, address2);
		//wrong case : no such name
		List<PhoneRecord> subRecords = null;		
		subRecords = pb.queryByName("");
		assertEquals(0, subRecords.size());
	}
	
	@Test
	public void testRemoveByName1() {
		String name1 = "bbc";
		String number1 = "21321";
		String address1 = "ddd";
		String name2 = "bbd";
		String number2 = "1321";
		String address2 = "daa";
		pb.add(name1, number1, address1);
		pb.add(name2, number2, address2);
		
		//correct case
		boolean result = pb.removeByName("bb");
		assertEquals(true, result);
		assertEquals(0, pb.records.size());
	}
	
	@Test
	public void testRemoveByName2() {
		String name1 = "bbc";
		String number1 = "21321";
		String address1 = "ddd";
		String name2 = "bbd";
		String number2 = "1321";
		String address2 = "daa";
		pb.add(name1, number1, address1);
		pb.add(name2, number2, address2);

		//wrong case : no such name
		boolean result = pb.removeByName("bbb");
		assertEquals(false, result);
		assertEquals(2, pb.records.size());
	}

}

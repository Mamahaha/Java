package org.led.helloxml;

import org.led.helloxml.handlers.GuildMarshaller;
import org.led.helloxml.handlers.GuildUnmarshaller;



public class MainApp 
{
	public static void testGuildMarshaller() {
		GuildMarshaller m = new GuildMarshaller();
		m.init();
		m.marshal();
	}
	
	public static void testGuildUnmarshaller() {
		GuildUnmarshaller m = new GuildUnmarshaller();
		m.unmarshal();
	}
	
    public static void main( String[] args )
    {
    	//testGuildMarshaller();
    	testGuildUnmarshaller();
    }
}

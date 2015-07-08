package org.led.helloxml.handlers;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.led.helloxml.data.Guild;

public class GuildUnmarshaller {
	
	public void unmarshal() {		
		try {
			File file = new File("guild.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Guild.class);
			
			Unmarshaller um = jaxbContext.createUnmarshaller();
			Guild obj = (Guild)um.unmarshal(file);
			System.out.println(obj.toString());
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	}
}

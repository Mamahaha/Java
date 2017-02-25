package org.led.helloxml.handlers;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.led.helloxml.data.Guild;

public class GuildMarshaller {
	Guild guild = new Guild();
	
	public void init(){
		guild.setName("ACE");
		guild.setLevel(4);
		guild.setFlag("Griffen");
		guild.setToken("ACE_Token");
		guild.setDescription("I'm who I am, an abnormal flower.");
	}
	
	public void marshal() {
		try {
			File file = new File("guild.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Guild.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(guild,  file);
			jaxbMarshaller.marshal(guild, System.out);
			
		} catch (JAXBException e) {

			e.printStackTrace();
		}
	}
}

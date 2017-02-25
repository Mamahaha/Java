package org.led.osgi;

import org.apache.felix.gogo.commands.Command; 
import org.apache.karaf.shell.console.OsgiCommandSupport; 

@Command(scope = "test", name = "hello", description="Says hello") 
public class Hello extends OsgiCommandSupport { 
	@Override 
	protected Object doExecute() throws Exception { 
		System.out.println("Executing Hello command"); 
		return null; 
	} 
}

package org.led.hellorest.inbound;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.led.hellorest.dao.GuideDao;
import org.led.hellorest.model.guide.Guide;

@Path("/guide")

public class GuideResource {
	private GuideDao dao = new GuideDao();
	

	@POST
	@Consumes("application/xml")
	public void createGuide(Guide guide) {
		dao.addGuide(guide);
	}
	
	@GET
	@Path("{name}")
	@Produces("application/xml")
	public Guide getGuide(@PathParam("name") String name) {
		return dao.getGuidebyName(name);
	}
}

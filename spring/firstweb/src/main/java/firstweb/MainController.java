package firstweb;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	@RequestMapping("/hello")
	public ModelAndView hello() {
		String message = "Hello 1, Spring 3.0!";
		System.out.println(message);
		return new ModelAndView("11", "message", message);
	}
	
	@RequestMapping("/hello/11")
	public String hello1(@RequestParam("username")String username, HttpServletRequest request) {
		if (!"admin".equals(username)) {
			return "22";
		} else {
			return "23";
		}
	}
	
	@RequestMapping("/hello/2{bid}")
	public String hello2(@PathVariable int bid) {
		if (bid == 3) {
			return "22";
		} else {
			return "23";
		}
	}

}
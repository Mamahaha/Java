package firstweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

 @RequestMapping("/hello")
 public ModelAndView helloWorld() {

  String message = "Hello World, Spring 3.0!";
  System.out.println(message);
  return new ModelAndView("index2", "message", message);
 }

}
package xxx.epicteddybear.awesome.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(method = RequestMethod.GET)
public class HomeController {

    @Autowired
    MongoOperations dbOps;

    @RequestMapping(value={"/home"}, method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "Hello world!");
		return "index";
	}

    @RequestMapping(value={"/home/addNotebook"}, method = RequestMethod.GET)
    public String addFlashCard(ModelMap model) throws Exception {

        return "index";
    }
}
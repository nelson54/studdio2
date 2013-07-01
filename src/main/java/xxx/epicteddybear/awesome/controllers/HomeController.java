package xxx.epicteddybear.awesome.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xxx.epicteddybear.awesome.domain.FlashCard;
import xxx.epicteddybear.awesome.domain.Notebook;

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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String user = auth.getName();

        Notebook book = Notebook.create(user, "test list");

        book.addFlashCard(new FlashCard("Card 1", "Back"));
        book.addFlashCard(new FlashCard("Card 2", "Back"));
        book.addFlashCard(new FlashCard("Card 3", "Back"));

        dbOps.insert(book);

        model.addAttribute("message", book);

        return "index";
    }
}
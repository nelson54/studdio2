package xxx.epicteddybear.awesome.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xxx.epicteddybear.awesome.domain.FlashCard;
import xxx.epicteddybear.awesome.domain.Notebook;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: dnelson
 * Date: 7/1/13
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */


@Controller
public class NotebookController {

    @Autowired
    MongoOperations dbOps;

    @RequestMapping("/notebooks")
    @ResponseBody
    List<Notebook> allNotebooksByCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String user = auth.getName();

        Query getNotebooksByUser = new Query();
        getNotebooksByUser.addCriteria(Criteria.where("owner").is(user));

        return dbOps.find(getNotebooksByUser, Notebook.class);
    }

    @RequestMapping("/notebooks/{id}")
    @ResponseBody
    Notebook singleNotebooksByCurrentUser(@PathVariable String id){
        return allNotebooksByCurrentUser().get(0);
    }

    @RequestMapping("/notebooks/{id}/cards")
    @ResponseBody
    List<FlashCard> allFlashcardsByCurrentUser(
            @PathVariable String id
    ){
        return singleNotebooksByCurrentUser(id).getFlashCards();
    }

    @RequestMapping("/notebooks/{id}/cards/{index}")
    @ResponseBody
    FlashCard singleFlashcardByCurrentUser(
            @PathVariable String id,
            @PathVariable int index
    ){
        return singleNotebooksByCurrentUser(id).getFlashCards().get(index);
    }
}

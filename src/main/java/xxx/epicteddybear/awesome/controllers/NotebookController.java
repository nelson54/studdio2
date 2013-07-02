package xxx.epicteddybear.awesome.controllers;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xxx.epicteddybear.awesome.domain.FlashCard;
import xxx.epicteddybear.awesome.domain.Notebook;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


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

//    @RequestMapping("/notebooks/new")
//    @ResponseBody
//    String addNotebook(@RequestParam String title){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        String user = auth.getName();
//
//        Notebook notebook = Notebook.create(user, title);
//
//        dbOps.insert(notebook);
//
//        return "Insert Success";
//    }

    @RequestMapping("/notebooks/new")
    @ResponseBody
    String addNotebook(@RequestParam String json) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String user = auth.getName();

        ObjectMapper mapper = new ObjectMapper();


        Notebook notebook = mapper.readValue(json, Notebook.class);

        notebook.setId(UUID.randomUUID().toString());
        notebook.setOwner(user);

        dbOps.insert(notebook);

        return "Insert Success";
    }

    @RequestMapping("/notebook/{id}")
    @ResponseBody
    Notebook notebookById(@PathVariable String id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String user = auth.getName();

        Query getNotebooksByUser = new Query();
        getNotebooksByUser.addCriteria(Criteria
                .where("owner").is(user)
                .and("id").is(id)).limit(1);

        return dbOps.find(getNotebooksByUser, Notebook.class).get(0);

    }

//    @RequestMapping("/notebooks/{id}")
//    @ResponseBody
//    String updateNotebook(@PathVariable String id, @RequestParam String title){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        String user = auth.getName();
//
//        Query getNotebooksByUser = new Query();
//        getNotebooksByUser.addCriteria(Criteria
//                .where("owner").is(user)
//                .and("id").is(id)).limit(1);
//
//        List<Notebook> notebooks = dbOps.find(getNotebooksByUser, Notebook.class);
//
//        if (notebooks.size() > 0){
//            Notebook notebook = notebooks.get(0);
//
//            notebook.setUpdated(DateTime.now());
//            notebook.setTitle(title);
//        }
//
//        return "Success";
//    }

    @RequestMapping("/notebooks/{id}/cards")
    @ResponseBody
    List<FlashCard> flashcardsInNotebook(
            @PathVariable String id
    ){
        return notebookById(id).getFlashCards();
    }

    @RequestMapping("/notebooks/{id}/cards/{index}")
    @ResponseBody
    FlashCard flashcardInNotebook(
            @PathVariable String id,
            @PathVariable int index
    ){
        return flashcardsInNotebook(id).get(index);
    }
}

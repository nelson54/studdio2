package xxx.epicteddybear.awesome.api;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    //
    // REST API
    //

    // GET Request Mapping
    @RequestMapping(value = "/notebook/{id}", method = RequestMethod.GET)
    @ResponseBody
    Notebook getNotebook(@PathVariable String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String user = auth.getName();

        Query getNotebooksByUser = new Query();
        getNotebooksByUser.addCriteria(Criteria
                .where("owner").is(user)
                .and("id").is(id)).limit(1);

        return dbOps.find(getNotebooksByUser, Notebook.class).get(0);

    }

    // PUT Request Mapping
    @RequestMapping(value = "/notebook/{id}", method = {RequestMethod.PUT, RequestMethod.POST})
    @ResponseBody
    Notebook putNotebook(@PathVariable String id, @RequestBody Notebook notebook) throws IOException {

        if (StringUtils.equalsIgnoreCase(id, "new"))
            notebook.setId(UUID.randomUUID().toString());

        if (StringUtils.isEmpty(notebook.getOwner())) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = auth.getName();

            notebook.setOwner(user);
        }

        dbOps.insert(notebook);

        return notebook;
    }

    // DELETE Request Mapping
    @RequestMapping(value = "/notebook/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    String deleteNotebook(@PathVariable String id, @RequestBody Notebook notebook) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String user = auth.getName();

        Query getNotebooksByUser = new Query()
                .addCriteria(Criteria
                        .where("owner").is(user)
                        .and("id").is(id)).limit(1);

        List<Notebook> notebooks = dbOps.find(getNotebooksByUser, Notebook.class);

        if (notebooks.size() > 0) {
            dbOps.remove(notebooks.get(0));
        }

        return "Success";
    }

    //
    // END REST API
    //

    @RequestMapping("/notebook/{id}/cards")
    @ResponseBody
    List<FlashCard> getFlashCards(
            @PathVariable String id
    ) {
        return getNotebook(id).getFlashCards();
    }

    @RequestMapping("/notebooks/{id}/cards/{index}")
    @ResponseBody
    FlashCard getFlashCard(
            @PathVariable String id,
            @PathVariable int index
    ) {
        return getFlashCards(id).get(index);
    }

    @RequestMapping("/notebooks")
    @ResponseBody
    List<Notebook> getNotebooks() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String user = auth.getName();

        Query getNotebooksByUser = new Query();
        getNotebooksByUser.addCriteria(Criteria.where("owner").is(user));

        return dbOps.find(getNotebooksByUser, Notebook.class);
    }
}

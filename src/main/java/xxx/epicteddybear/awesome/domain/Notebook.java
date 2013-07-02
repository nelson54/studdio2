package xxx.epicteddybear.awesome.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: dnelson
 * Date: 6/26/13
 * Time: 12:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class Notebook {

    private String id;
    private String owner;

    private DateTime created;
    private DateTime updated;

    private String title;

    private List<FlashCard> flashCards;

    @JsonIgnore
    public static Notebook create(String user, String title){
        String uuid = UUID.randomUUID().toString();
        DateTime created = DateTime.now();
        return new Notebook(uuid, user, created, created, title, new ArrayList<FlashCard>());
    }

    public Notebook() {
    }

    public Notebook(String id, String owner, DateTime created, DateTime updated, String title,
                    List<FlashCard> flashCards) {
        this.id = id;
        this.owner = owner;
        this.created = created;
        this.updated = updated;
        this.title = title;
        this.flashCards = flashCards;
    }


    public void addFlashCard(FlashCard card){
        this.flashCards.add(card);
    }

    public void removeFlashCard(FlashCard card){
        this.flashCards.remove(card);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<FlashCard> getFlashCards() {
        return flashCards;
    }

    public void setFlashCards(List<FlashCard> flashCards) {
        this.flashCards = flashCards;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }

    public DateTime getUpdated() {
        return updated;
    }

    public void setUpdated(DateTime updated) {
        this.updated = updated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

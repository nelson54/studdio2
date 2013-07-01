package xxx.epicteddybear.awesome.domain;

import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: dnelson
 * Date: 6/26/13
 * Time: 12:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class FlashCard {
    private String ownerId;

    private DateTime created;
    private DateTime updated;

    private String front;
    private String back;

    public FlashCard(String front, String back) {
        this.created = DateTime.now();

        this.front = front;
        this.back = back;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.updated = DateTime.now();

        this.front = front;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.updated = DateTime.now();

        this.back = back;
    }
}

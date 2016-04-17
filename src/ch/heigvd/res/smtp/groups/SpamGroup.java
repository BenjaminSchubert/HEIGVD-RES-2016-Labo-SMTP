package ch.heigvd.res.smtp.groups;

import java.util.ArrayList;

/**
 * @author Benjamin Schubert and Basile Vu
 */
public class SpamGroup {
    private String sender;
    private ArrayList<String> recipients = new ArrayList<>();

    public String getSender() {
        return sender;
    }

    public ArrayList<String> getRecipients() {
        return recipients;
    }

    public void addUser(String o) {
        if(null == sender) {
            sender = o;
        } else {
            recipients.add(o);
        }
    }
}

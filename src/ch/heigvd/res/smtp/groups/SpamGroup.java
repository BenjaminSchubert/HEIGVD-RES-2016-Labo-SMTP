package ch.heigvd.res.smtp.groups;

import java.util.ArrayList;

/**
 * @author Benjamin Schubert and Basile Vu
 *
 * This represents a group for sending / receiving spam
 */
public class SpamGroup {
    private String sender;
    private ArrayList<String> recipients = new ArrayList<>();

    /**
     * Gets the sender of the email. This will be the first people added to the group
     *
     * @return email of the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Gets the recipients for the email. This will be all people but the first added to the group
     *
     * @return list of emails for recipients
     */
    public ArrayList<String> getRecipients() {
        return recipients;
    }

    /**
     * Adds a user to the group. If the user is the first added to the group,
     * he will become the sender. Otherwise they are added to recipients
     * @param o: user to add
     */
    public void addUser(String o) {
        if(null == sender) {
            sender = o;
        } else {
            recipients.add(o);
        }
    }
}

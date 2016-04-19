package ch.heigvd.res.smtp;

import ch.heigvd.res.smtp.groups.SpamGroup;
import ch.heigvd.res.smtp.smtp.SMTPClient;
import ch.heigvd.res.smtp.smtp.SMTPClientFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Benjamin Schubert and Basile Vu
 *
 * Class to send email campaigns.
 */
public class EmailSender {
    /**
     * Sends random emails to multiple people
     *
     * @param host: host to connect in order to send emails
     * @param port: port on the host to connect to
     * @param groups: groups used to send emails
     * @param emails: list of emails that can be sent
     * @param protocol: protocol to use for connection
     */
    public static void sendEmailCampaign(String host, int port, SpamGroup[] groups, ArrayList<Email> emails,
                                         SMTPClientFactory.ApplicationLayerProtocol protocol) {
        try(SMTPClient mailClient =
                    SMTPClientFactory.createSMTPClient(host, port, protocol)) {
            sendEmails(mailClient, groups, emails);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Sends random emails to multiple people
     *
     * @param host: host to connect in order to send emails
     * @param port: port on the host to connect to
     * @param groups: groups used to send emails
     * @param emails: list of emails that can be sent
     * @param username: username for authentication against the mail server
     * @param password: password for authentication against the mail server
     * @param protocol: protocol to use for connection
     */
    public static void sendEmailCampaign(
            String host, int port, SpamGroup[] groups, ArrayList<Email> emails, String username, String password,
            SMTPClientFactory.ApplicationLayerProtocol protocol) {
        try(SMTPClient mailClient =
                    SMTPClientFactory.createSMTPClient(
                            host, port, protocol, username, password)) {
            sendEmails(mailClient, groups, emails);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Sends random emails to given clients
     *
     * @param mailClient: mail client used to send emails
     * @param groups: groups to send emails to
     * @param emails: email pool to use for spam
     * @throws IOException
     */
    private static void sendEmails(SMTPClient mailClient, SpamGroup[] groups, ArrayList<Email> emails)
            throws IOException {
        Random random = new Random();

        for(SpamGroup group: groups) {
            mailClient.sendEmail(group, emails.get(random.nextInt(emails.size())));
        }
    }
}

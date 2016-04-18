package ch.heigvd.res.smtp;

import ch.heigvd.res.smtp.groups.SpamGroup;
import ch.heigvd.res.smtp.smtp.SMTPClient;
import ch.heigvd.res.smtp.smtp.SMTPClientFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Benjamin Schubert and Basile Vu
 */
public class EmailSender {
    public static void sendEmailCampaign(String host, int port, SpamGroup[] groups, ArrayList<Email> emails) {
        try(SMTPClient mailClient =
                    SMTPClientFactory.createSMTPClient(host, port, SMTPClientFactory.ApplicationLayerProtocol.PLAIN)) {
            Random random = new Random();

            for(SpamGroup group: groups) {
                mailClient.sendEmail(group, emails.get(random.nextInt(emails.size())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

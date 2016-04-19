package ch.heigvd.res.smtp;

import ch.heigvd.res.smtp.emailMessageParser.EmailListFactory;
import ch.heigvd.res.smtp.groups.SpamGroup;
import ch.heigvd.res.smtp.groups.SpamGroupGenerator;
import ch.heigvd.res.smtp.smtp.SMTPClientFactory;

import java.util.ArrayList;

/**
 * @author Benjamin Schubert and Basile Vu
 *
 * Main entry point for the Spam sender
 */
public class ConsoleRunner {
    private final static String usage =
            "Usage : java ch.heigvd.res.smtp.ConsoleRunner host port email_addresses_file"
            + "groups email_list [username] [password] [--ssl]";

    /**
     * Setup and sends an email campaign
     *
     * @param args list of arguments as define in usage.
     */
    public static void main(String... args) {
        if(args.length < 5 || args.length > 8) {
            System.err.println(usage);
            System.exit(2);
        }

        SpamGroup[] spamGroups = SpamGroupGenerator.generate(args[2], Integer.parseInt(args[3]));
        ArrayList<Email> emailList = EmailListFactory.getEmails(args[4]);
        SMTPClientFactory.ApplicationLayerProtocol protocol = SMTPClientFactory.ApplicationLayerProtocol.PLAIN;


        if(args.length <= 6) {
            if(args.length == 6 && args[5].equals("--ssl")) {
                protocol = SMTPClientFactory.ApplicationLayerProtocol.SSL;
            }
            else if(args.length != 5) {
                System.err.println(usage);
                System.exit(8);
            }
            EmailSender.sendEmailCampaign(args[0], Integer.parseInt(args[1]), spamGroups, emailList, protocol);
        }
        else {
            if(args.length == 8 && args[7].equals("--ssl")) {
                protocol = SMTPClientFactory.ApplicationLayerProtocol.SSL;
            }
            else if(args.length != 7) {
                System.err.println(usage);
                System.exit(12);
            }
            EmailSender.sendEmailCampaign(
                    args[0], Integer.parseInt(args[1]), spamGroups, emailList, args[5], args[6], protocol);
        }
    }
}

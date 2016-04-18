package ch.heigvd.res.smtp;

import ch.heigvd.res.smtp.emailMessageParser.EmailMessageParserFactory;
import ch.heigvd.res.smtp.groups.SpamGroup;
import ch.heigvd.res.smtp.groups.SpamGroupGenerator;

import java.util.ArrayList;

/**
 * @author Benjamin Schubert and Basile Vu
 */
public class ConsoleRunner {
    public static void main(String... args) {
        if(args.length != 5 && args.length != 7) {
            System.err.println(
                    "Usage : java -jar INSERT host port email_addresses_file groups email_list [username] [password]");
            System.exit(2);
        }

        SpamGroup[] spamGroups = SpamGroupGenerator.generate(args[2], Integer.parseInt(args[3]));
        ArrayList<Email> emailList = EmailMessageParserFactory.getEmails(args[4]);

        if(args.length == 5) {
            EmailSender.sendEmailCampaign(args[0], Integer.parseInt(args[1]), spamGroups, emailList);
        }
        else {
            EmailSender.sendEmailCampaign(args[0], Integer.parseInt(args[1]), spamGroups, emailList, args[5], args[6]);
        }
    }
}

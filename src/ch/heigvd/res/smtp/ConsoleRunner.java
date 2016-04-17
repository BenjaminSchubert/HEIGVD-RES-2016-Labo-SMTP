package ch.heigvd.res.smtp;

import ch.heigvd.res.smtp.emailMessageParser.EmailMessageParserFactory;
import ch.heigvd.res.smtp.groups.SpamGroup;
import ch.heigvd.res.smtp.groups.SpamGroupGenerator;
import ch.heigvd.res.smtp.smtp.SMTPClient;
import ch.heigvd.res.smtp.smtp.SMTPClientFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Benjamin Schubert and Basile Vu
 */
public class ConsoleRunner {
    public static void main(String... args) throws IOException {
        if(args.length < 4) {
            System.err.println("Usage : java -jar INSERT host port email_addresses_file groups email_list");
            System.exit(2);
        }

        SMTPClient mailClient =
                SMTPClientFactory.createSMTPClient(args[0], Integer.parseInt(args[1]), SMTPClientFactory.ApplicationLayerProtocol.PLAIN);

        SpamGroup[] spamGroups = SpamGroupGenerator.generate(args[2], Integer.parseInt(args[3]));
        ArrayList<Email> emailList = EmailMessageParserFactory.getEmails(args[4]);
        Random random = new Random();

        for(SpamGroup group: spamGroups) {
            mailClient.sendEmail(group, emailList.get(random.nextInt(emailList.size())));
        }
    }
}

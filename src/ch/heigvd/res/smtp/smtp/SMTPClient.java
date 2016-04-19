package ch.heigvd.res.smtp.smtp;

import ch.heigvd.res.smtp.Email;
import ch.heigvd.res.smtp.groups.SpamGroup;

import java.io.*;

/**
 * @author Benjamin Schubert and Basile Vu
 *
 * This class implements the basis for SMTP exchanges to send emails
 */
public class SMTPClient implements Closeable {
    protected final BufferedReader input;
    protected final BufferedWriter output;
    private boolean authenticated = false;

    /**
     * Creates a new SMTPClient which connects through the given input and output.
     * @param input: the input on which to read data
     * @param output: the output on which to send data
     * @throws IOException
     */
    SMTPClient(BufferedReader input, BufferedWriter output) throws IOException {
        this.input = input;
        this.output = output;
    }

    /**
     *
     * Authenticates the clients with the server
     *
     * @throws IOException
     */
    public void authenticate() throws IOException {
        checkData("220", "Likely not a SMTP Server");
        output.write("EHLO anonymous.com\r\n");
        output.flush();

        // comsume input we don't care about
        String in;
        //noinspection StatementWithEmptyBody
        while((in = input.readLine()).startsWith("250-")) {}

        if(!in.startsWith("250")) {
            throw new IOException(String.format("authentication not successful. Got %s", in));
        }
    }

    /**
     * Helper function to check that the data received from the server is indeed correct
     *
     * @param s: string to search in the data received from the server
     * @param message: message to join to the exeption in case of problem
     * @throws IOException
     */
    protected void checkData(String s, String message) throws IOException {
        String data = input.readLine();
        if(!data.toLowerCase().contains(s.toLowerCase())) {
            throw new IOException(message + ": Received " + data);
        }
    }

    /**
     * sends the email header
     *
     * @param group: group representing the sender and recipients of the email
     * @throws IOException
     */
    private void sendHeader(SpamGroup group) throws IOException {
        output.write(String.format("MAIL FROM: %s\r\n", group.getSender()));
        for(String rcpt: group.getRecipients()) {
            output.write(String.format("RCPT TO: %s\r\n", rcpt));
        }
        output.flush();

        checkData("250", "Couldn't validate sender");

        for(String ignored : group.getRecipients()) {
            checkData("250", "Couldn't validate receiver");
        }
    }

    /**
     * Sends the content of the mail
     *
     * @param group: group representing the sender and recipients of the mail
     * @param email: email to send
     * @throws IOException
     */
    private void sendBody(SpamGroup group, Email email) throws IOException {
        output.write("DATA\r\n");
        output.flush();
        checkData("354", "Didn't get authorization to send email");

        output.write(String.format("From: %s\r\n", group.getSender()));

        String users = "";
        for(String rcpt: group.getRecipients()) {
            users += rcpt + ", ";
        }
        output.write(String.format("To: %s\r\n", users));
        if(email.subject != null) {
            output.write(String.format("Subject: %s\r\n", email.subject));
        }
        if(email.header != null) {
            output.write(email.header);
        }
        output.write(email.content);
        output.write("\r\n.\r\n");
        output.flush();

        checkData("250", "Couldn't send mail body");
    }

    /**
     * Authenticates with the server if needed and send the email to the given group
     *
     * @param group: group to send the email to
     * @param email: email to send
     * @throws IOException
     */
    public void sendEmail(SpamGroup group, Email email) throws IOException {
        if(!authenticated) {
            authenticate();
            authenticated = true;
        }
        sendHeader(group);
        sendBody(group, email);
    }

    /**
     * Says goodbye to the server and closes the streams
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        output.write("quit\r\n");
        output.flush();
        input.readLine();
        input.readLine();
        output.close();
        input.close();
    }
}

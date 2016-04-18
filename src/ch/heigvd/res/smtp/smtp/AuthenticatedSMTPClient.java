package ch.heigvd.res.smtp.smtp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Base64;

/**
 * @author Benjamin Schubert and Basile Vu
 *
 * This class implements part of RFC 2554 defining SMTP authentication
 */
public class AuthenticatedSMTPClient extends SMTPClient {
    private String username;
    private String password;

    AuthenticatedSMTPClient(BufferedReader input, BufferedWriter output, String username, String password)
            throws IOException {
        super(input, output);
        this.username = Base64.getEncoder().encodeToString(username.getBytes());
        this.password = Base64.getEncoder().encodeToString(password.getBytes());
    }

    public void authenticate() throws IOException {
        check_data("220", "Likely not a SMTP Server");
        output.write("EHLO anonymous.com\r\n");
        output.flush();

        String in;

        while(!(in = input.readLine()).contains("250-AUTH ")) {}
        if(!in.contains("LOGIN")) {
            System.err.println(
                    String.format("The authentification method 'LOGIN' is not available. Only %s where found", in)
            );

            System.exit(5);
        }
        check_data("250", "didn't get size");

        output.write("AUTH LOGIN\r\n");
        output.flush();

        check_data("334", "didn't get asked for username");
        output.write(username + "\r\n");
        output.flush();

        check_data("334", "didn't get asked for password");
        output.write(password + "\r\n");
        output.flush();

        check_data("235", "Couldn't authenticate");
    }
}

package ch.heigvd.res.smtp.emailAddressParser;

import java.io.InputStream;

/**
 * Created by Benjamin Schubert on 4/6/16.
 */
public abstract class BaseEmailAddressParser {
    public abstract InputStream getInputStream();

    public String[] parseEmails() {

    }
}

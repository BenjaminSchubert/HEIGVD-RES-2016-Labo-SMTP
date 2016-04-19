package ch.heigvd.res.smtp.emailMessageParser;

import ch.heigvd.res.smtp.Email;

import java.util.ArrayList;

/**
 * @author Benjamin Schubert and Basile Vu
 *
 * Class to represent an email parser
 */
public abstract class BaseEmailParser {
    public abstract ArrayList<Email> parse();
}


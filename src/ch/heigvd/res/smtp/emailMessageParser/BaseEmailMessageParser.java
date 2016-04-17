package ch.heigvd.res.smtp.emailMessageParser;

import ch.heigvd.res.smtp.Email;

import java.util.ArrayList;

/**
 * @author Benjamin Schubert and Basile Vu
 */
public abstract class BaseEmailMessageParser {
    public abstract ArrayList<Email> parse();
}


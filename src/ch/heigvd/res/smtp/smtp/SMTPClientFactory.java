package ch.heigvd.res.smtp.smtp;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;

/**
 * @author Benjamin Schubert and Basile Vu
 *
 * This handled the creation of SMTPClients with or without authentication
 */
public class SMTPClientFactory {
    /**
     * Enum containing the different protocols accepted on the application layer
     */
    public enum ApplicationLayerProtocol { PLAIN, SSL }

    /**
     * Creates a new unauthenticated SMTP client
     *
     * @param host: host to connect to
     * @param port: port to connect to on the host
     * @param protocol: communication protocol to use
     * @return an already configured SMTPClient
     * @throws IOException
     */
    public static SMTPClient createSMTPClient(String host, int port, ApplicationLayerProtocol protocol)
            throws IOException {

        Socket socket = getSocket(host, port, protocol);

        return new SMTPClient(
                new BufferedReader(new InputStreamReader(new BufferedInputStream(socket.getInputStream()))),
                new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(socket.getOutputStream())))
        );
    }

    /**
     * Creates a new authenticated SMTP client
     *
     * @param host: host to connect to
     * @param port: port to connect to on the host
     * @param protocol: protocol to use for communication
     * @param username: username to use for authentication
     * @param password: password to use for authentication
     * @return an already configured SMTPClient
     * @throws IOException
     */
    public static SMTPClient createSMTPClient(String host, int port, ApplicationLayerProtocol protocol,
                                                           String username, String password) throws IOException {
        Socket socket = getSocket(host, port, protocol);

        return new AuthenticatedSMTPClient(
                new BufferedReader(new InputStreamReader(socket.getInputStream())),
                new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                username,
                password
        );
    }

    /**
     * Creates a new socket based on the protocol asked
     *
     * @param host: host to connect to
     * @param port: port to connect to on the host
     * @param protocol: protocol to use for communication
     * @return a new socket already bound to the host
     * @throws IOException
     */
    private static Socket getSocket(String host, int port, ApplicationLayerProtocol protocol) throws IOException {
        Socket socket;

        switch (protocol) {
            case SSL:
                SSLSocketFactory factory=(SSLSocketFactory) SSLSocketFactory.getDefault();
                socket = factory.createSocket(host, port);
                break;
            case PLAIN:
            default:
                socket = new Socket(host, port);
        }
        return socket;
    }
}

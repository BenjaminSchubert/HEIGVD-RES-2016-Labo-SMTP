package ch.heigvd.res.smtp.smtp;

import java.io.*;
import java.net.Socket;

/**
 * @author Benjamin Schubert and Basile Vu
 */
public class SMTPClientFactory {
    public enum ApplicationLayerProtocol { PLAIN }

    public static SMTPClient createSMTPClient(String host, int port, ApplicationLayerProtocol protocol)
            throws IOException {
        Socket socket;

        switch (protocol) {
            case PLAIN:
            default:
                socket = new Socket(host, port);
        }

        return new SMTPClient(
                new BufferedReader(new InputStreamReader(new BufferedInputStream(socket.getInputStream()))),
                new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(socket.getOutputStream())))
        );
    }
}

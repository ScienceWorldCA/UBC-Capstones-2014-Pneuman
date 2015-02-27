package S71200URLCommunicator.Interfaces;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public interface URLConnectionFactory {
    URLConnection createConnection(URL address) throws IOException;
}

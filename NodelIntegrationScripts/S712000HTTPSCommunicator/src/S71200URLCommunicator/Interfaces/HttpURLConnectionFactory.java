package S71200URLCommunicator.Interfaces;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public interface HttpURLConnectionFactory {
    HttpURLConnection createConnection(URL address) throws IOException;
}

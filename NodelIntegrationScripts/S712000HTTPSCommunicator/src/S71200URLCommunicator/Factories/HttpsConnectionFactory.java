package S71200URLCommunicator.Factories;

import S71200URLCommunicator.Interfaces.URLConnectionFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;

public class HttpsConnectionFactory implements URLConnectionFactory{
    public HttpsURLConnection createConnection(URL address) throws IOException{
        return (HttpsURLConnection)address.openConnection();
    }
}

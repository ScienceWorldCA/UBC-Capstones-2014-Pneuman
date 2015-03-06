package S71200URLCommunicator.Factories;

import S71200URLCommunicator.Interfaces.HttpClientFactory;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;


public class HttpsClientFactory implements HttpClientFactory{
    public CloseableHttpClient createHttpClient(CookieStore defaultStore) {
        HostnameVerifier acceptAll = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        CloseableHttpClient client = HttpClients.custom().setSSLHostnameVerifier(acceptAll).setDefaultCookieStore(defaultStore).build();

        return client;
    }
}

package S71200URLCommunicator.Interfaces;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.CloseableHttpClient;

public interface HttpClientFactory {
    CloseableHttpClient createHttpClient(CookieStore defaultStore);
}

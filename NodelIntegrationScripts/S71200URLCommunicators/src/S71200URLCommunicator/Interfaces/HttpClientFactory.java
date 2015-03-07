package S71200URLCommunicator.Interfaces;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public interface HttpClientFactory {
    CloseableHttpClient createHttpClient(CookieStore defaultStore) throws IOException;
}

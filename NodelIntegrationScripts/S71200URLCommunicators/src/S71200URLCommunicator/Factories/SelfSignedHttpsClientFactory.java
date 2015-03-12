package S71200URLCommunicator.Factories;

import S71200URLCommunicator.Interfaces.HttpClientFactory;
import S71200URLCommunicator.Interfaces.SSLContextFactory;
import org.apache.http.client.CookieStore;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.IOException;

//Creates a CLoseableHttpClient instance which can be used with SelfSignedCertificates. In order to achieve this
// a custom hostname verifier must be used which disregards the host name so that the PLC may be hosted on any IP.
public class SelfSignedHttpsClientFactory implements HttpClientFactory {

    private SSLContextFactory _sslContextFactory;

    public SelfSignedHttpsClientFactory(SSLContextFactory sslContextFactory){
        _sslContextFactory = sslContextFactory;
    }

    public CloseableHttpClient createHttpClient(CookieStore defaultStore) throws IOException {

        HostnameVerifier acceptAll = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        SSLContext sslContext;

        try {
            sslContext = _sslContextFactory.getContext();
        }catch (Exception e){
            throw new IOException(e);
        }

        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, acceptAll);

        return HttpClients.custom()
                .setSSLHostnameVerifier(acceptAll)
                .setDefaultCookieStore(defaultStore)
                .setSSLSocketFactory(socketFactory)
                .build();
    }
}

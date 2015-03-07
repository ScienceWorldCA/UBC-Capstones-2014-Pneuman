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


public class SelfSignedHttpsClientFactory implements HttpClientFactory {

    private SSLContextFactory _sslContextFactory;

    public SelfSignedHttpsClientFactory(SSLContextFactory sslContextFactory){
        _sslContextFactory = sslContextFactory;
    }

    public CloseableHttpClient createHttpClient(CookieStore defaultStore){

        HostnameVerifier acceptAll = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        SSLContext sslContext = _sslContextFactory.getContext();

        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, acceptAll);

        return HttpClients.custom()
                .setSSLHostnameVerifier(acceptAll)
                .setDefaultCookieStore(defaultStore)
                .setSSLSocketFactory(socketFactory)
                .build();
    }
}

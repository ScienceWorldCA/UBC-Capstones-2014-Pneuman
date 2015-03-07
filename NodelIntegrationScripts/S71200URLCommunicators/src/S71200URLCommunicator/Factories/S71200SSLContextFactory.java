package S71200URLCommunicator.Factories;

import S71200URLCommunicator.Interfaces.SSLContextFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.KeyStore;

public class S71200SSLContextFactory implements SSLContextFactory{

    private static String DEFAULT_KEY_STORE_PASS = "changeit";
    private static String TRUST_STORE_PATH = "S71200URLCommunicator/Resources/S71200TrustStore.store";

    public SSLContext getContext() throws Exception {

        KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());

        InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(TRUST_STORE_PATH);

        try {
            trustStore.load(inStream, DEFAULT_KEY_STORE_PASS.toCharArray());
        } finally {
            if (inStream != null) {
                inStream.close();
            }
        }

        return SSLContexts.custom()
                .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                .build();

    }
}

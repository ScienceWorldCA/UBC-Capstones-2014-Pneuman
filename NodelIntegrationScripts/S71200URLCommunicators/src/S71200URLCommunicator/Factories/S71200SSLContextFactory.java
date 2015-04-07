package S71200URLCommunicator.Factories;

import S71200URLCommunicator.Interfaces.SSLContextFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.KeyStore;

//Creates SSL Context for S71200 PLC. created context uses a custom trust store,
// S71200TrustStore.store, found in the Resources folder. The custom trust store
// must contain the certificate provided by the S71200 PLC.
public class S71200SSLContextFactory implements SSLContextFactory{

    private static String DEFAULT_KEY_STORE_PASS = "changeit";
    private static String TRUST_STORE_PATH = "S71200TrustStore.store";

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

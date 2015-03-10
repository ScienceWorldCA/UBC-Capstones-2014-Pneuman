package S71200URLCommunicator.Factories;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

import java.net.CookieHandler;
import java.net.CookieManager;

public class CookieStoreFactory {
    public CookieStore createCookieStore(){
        CookieStore cookieStore = new BasicCookieStore();
        return cookieStore;
    }
}

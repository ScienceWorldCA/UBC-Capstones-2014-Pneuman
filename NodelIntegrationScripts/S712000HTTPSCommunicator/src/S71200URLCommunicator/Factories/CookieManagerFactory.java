package S71200URLCommunicator.Factories;

import java.net.CookieHandler;
import java.net.CookieManager;

/**
 * Created by macke_000 on 2/26/2015.
 */
public class CookieManagerFactory {
    public CookieManager createCookieManager(){
        CookieManager handler = new CookieManager();
        CookieHandler.setDefault(handler);
        return handler;
    }
}

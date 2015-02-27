package S71200URLCommunicator.Factories;

import java.net.CookieHandler;
import java.net.CookieManager;

public class CookieManagerFactory {
    public CookieManager createCookieManager(){
        CookieManager handler = new CookieManager();
        CookieHandler.setDefault(handler);
        return handler;
    }
}

package S71200URLCommunicator.Communicators;

import S71200URLCommunicator.Enums.S71200HttpsFields;
import S71200URLCommunicator.Factories.CookieManagerFactory;
import S71200URLCommunicator.Interfaces.URLConnectionFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URL;
import java.net.URLConnection;

public abstract class S71200URLCommunicator {

    private static String PLC_AUTHENTICATION_COOKIE_NAME = "siemens_ad_session";

    private String _plcIpAddress;
    private String _plcLogin;
    private String _plcPassword;

    protected URLConnectionFactory connectionFactory;
    protected CookieManagerFactory cookieManagerFactory;

    public S71200URLCommunicator(String plcIpAddress, String plcLogin, String plcPassword,
                                   URLConnectionFactory connectionFactory, CookieManagerFactory cookieManagerFactory){
        _plcIpAddress = plcIpAddress;
        _plcLogin = plcLogin;
        _plcPassword = plcPassword;
        this.connectionFactory = connectionFactory;
        this.cookieManagerFactory = cookieManagerFactory;
    }

    public void sendPlcCommand(HttpsURLConnection httpsCommandConnection) throws IOException {
        CookieManager sessionCookieManager = cookieManagerFactory.createCookieManager();

        if(!loginToPlc(sessionCookieManager)){
            throw new IOException("Could authenticate with PLC!");
        }

        connectGetContent(httpsCommandConnection);

        if(!logoutOfPlc(sessionCookieManager)){
            throw new IOException("Could not logout of PLC!");
        }
    }

    private boolean isAuthenticatedToPlc(CookieManager sessionCookieManager){
        for(HttpCookie cookie : sessionCookieManager.getCookieStore().getCookies()){
            if(cookie.getName().equals(PLC_AUTHENTICATION_COOKIE_NAME)){
                return true;
            }
        }
        return false;
    }

    private boolean loginToPlc(CookieManager sessionCookieManager) throws IOException{
        URLConnection loginCon = connectionFactory.createConnection(getPlcLoginUrl());
        loginCon.setRequestProperty(S71200HttpsFields.Redirection.toString(), "");
        loginCon.setRequestProperty(S71200HttpsFields.Login.toString(), _plcLogin);
        loginCon.setRequestProperty(S71200HttpsFields.Password.toString(), _plcPassword);
        connectGetContent(loginCon);
        return isAuthenticatedToPlc(sessionCookieManager);
    }

    private boolean logoutOfPlc(CookieManager sessionCookieManager) throws IOException{
        URLConnection logoutCon = connectionFactory.createConnection(getPlcLogoutUrl());
        logoutCon.setRequestProperty(S71200HttpsFields.Redirection.toString(), "");
        connectGetContent(logoutCon);
        return !isAuthenticatedToPlc(sessionCookieManager);
    }

    private void connectGetContent(URLConnection urlConnection){
        try {
            urlConnection.connect();
            urlConnection.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract URL getPlcLoginUrl();

    protected abstract URL getPlcLogoutUrl();

    public URL createPlcUrl(String template){
        try{
            return new URL(String.format(template, _plcIpAddress));
        }catch (java.net.MalformedURLException e){
            e.printStackTrace();
        }
        return null;
    }
}

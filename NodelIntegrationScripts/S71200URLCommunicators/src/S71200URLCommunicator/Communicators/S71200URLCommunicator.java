package S71200URLCommunicator.Communicators;

import S71200URLCommunicator.Enums.S71200HttpsFields;
import S71200URLCommunicator.Factories.CookieStoreFactory;
import S71200URLCommunicator.Interfaces.HttpClientFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

//Base class for http communication with the S71200 PLC.
public abstract class S71200URLCommunicator {

    private static String PLC_AUTHENTICATION_COOKIE_NAME = "siemens_ad_session";

    private String _plcIpAddress;
    private String _plcLogin;
    private String _plcPassword;

    protected HttpClientFactory clientFactory;
    protected CookieStoreFactory cookieStoreFactory;

    public S71200URLCommunicator(String plcIpAddress, String plcLogin, String plcPassword,
                                   HttpClientFactory clientFactory, CookieStoreFactory cookieStoreFactory){
        _plcIpAddress = plcIpAddress;
        _plcLogin = plcLogin;
        _plcPassword = plcPassword;
        this.clientFactory = clientFactory;
        this.cookieStoreFactory = cookieStoreFactory;
    }

    //Sends the supplied HttpPost method to the S71200 PLC by first logging in, sending command, and logging out.
    public void sendPlcCommand(HttpPost postMethod) throws IOException {
        CookieStore sessionStore = cookieStoreFactory.createCookieStore();
        if(!loginToPlc(sessionStore)){
            throw new IOException("Could not authenticate with PLC! IP: " + _plcIpAddress + " User: " + _plcLogin + "Pass: " + _plcPassword);
        }
        try(CloseableHttpClient commandClient = clientFactory.createHttpClient(sessionStore)) {
            connectGetContent(commandClient, postMethod);
        }
        logoutOfPlc(sessionStore);
    }

    //Creates PLC URL from template.
    //NOTE: Can not use String.Format due to compatibility issue running in Nodel.
    public String createPlcUrl(String template){
        return template.replace("%s", _plcIpAddress);
    }

    protected abstract String getPlcLoginUrl();

    protected abstract String getPlcLogoutUrl();

    //Checks whether the supplied cookie store contains an authentication cooke from the S71200 PLC.
    protected boolean isAuthenticatedToPlc(CookieStore sessionCookieStore){
        for(Cookie cookie : sessionCookieStore.getCookies()){
            if(cookie.getName().equals(PLC_AUTHENTICATION_COOKIE_NAME)){
                return true;
            }
        }
        return false;
    }

    //Logs into the PLC using supplied username and password.
    // Supplied cookieManager is used to capture cookies from session.
    private boolean loginToPlc(CookieStore sessionCookieManager) throws IOException{
        try(CloseableHttpClient loginClient = clientFactory.createHttpClient(sessionCookieManager)) {
            HttpPost postMethod = createLoginPostMethod();
            connectGetContent(loginClient, postMethod);
        }
        return isAuthenticatedToPlc(sessionCookieManager);
    }

    //Logs out of PLC.
    private boolean logoutOfPlc(CookieStore sessionCookieManager) throws IOException{
        try(CloseableHttpClient logoutClient = clientFactory.createHttpClient(sessionCookieManager)) {
            HttpPost postMethod = createLogoutPostMethod();
            connectGetContent(logoutClient, postMethod);
        }
        return !isAuthenticatedToPlc(sessionCookieManager);
    }

    //Connects and sends the supplied HttpPost method using the supplied httpClient.
    private void connectGetContent(CloseableHttpClient httpClient, HttpPost httpPost) throws IOException {
        boolean attemptConnect = true;
        int retries = 10;
        while(attemptConnect) {
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                entity.getContent();
                EntityUtils.consume(entity);
                attemptConnect = false;
            } catch (org.apache.http.NoHttpResponseException e) {
                if (retries <= 0) {
                    throw new IOException(e);
                } else {
                    attemptConnect = true;
                    retries--;
                }
            }
        }
    }

    private HttpPost createLoginPostMethod() throws UnsupportedEncodingException {
        HttpPost postMethod = new HttpPost(getPlcLoginUrl());
        List<NameValuePair> postParameters = new ArrayList<>();
        postParameters.add(new BasicNameValuePair(S71200HttpsFields.Redirection.toString(), ""));
        postParameters.add(new BasicNameValuePair(S71200HttpsFields.Login.toString(), _plcLogin));
        postParameters.add(new BasicNameValuePair(S71200HttpsFields.Password.toString(), _plcPassword));
        postMethod.setEntity(new UrlEncodedFormEntity(postParameters));
        return postMethod;
    }

    private HttpPost createLogoutPostMethod() throws UnsupportedEncodingException {
        HttpPost postMethod = new HttpPost(getPlcLogoutUrl());
        List<NameValuePair> postParameters = new ArrayList<>();
        postParameters.add(new BasicNameValuePair(S71200HttpsFields.Redirection.toString(), "."));
        postMethod.setEntity(new UrlEncodedFormEntity(postParameters));
        return postMethod;
    }
}

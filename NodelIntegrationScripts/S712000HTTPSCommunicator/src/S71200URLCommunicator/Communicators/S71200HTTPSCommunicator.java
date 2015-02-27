package S71200URLCommunicator.Communicators;

import S71200URLCommunicator.Factories.CookieManagerFactory;
import S71200URLCommunicator.Interfaces.URLConnectionFactory;

import java.net.URL;

public class S71200HTTPSCommunicator extends S71200URLCommunicator{

    private static String PLC_LOGIN_ADDRESS_TEMPLATE = "HTTPS://%1$s/FormLogin";
    private static String PLC_LOGOUT_ADDRESS_TEMPLATE = "HTTPS://%1$s/FormLogin?LOGOUT";

    public S71200HTTPSCommunicator(String plcIpAddress, String plcLogin, String plcPassword,
                                 URLConnectionFactory connectionFactory, CookieManagerFactory cookieManagerFactory){
        super(plcIpAddress, plcLogin, plcPassword, connectionFactory, cookieManagerFactory);
    }

    protected URL getPlcLoginUrl(){
        return createPlcUrl(PLC_LOGIN_ADDRESS_TEMPLATE);
    }

    protected URL getPlcLogoutUrl(){
        return createPlcUrl(PLC_LOGOUT_ADDRESS_TEMPLATE);
    }
}


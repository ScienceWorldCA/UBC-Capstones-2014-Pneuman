package S71200URLCommunicator.Communicators;

import S71200URLCommunicator.Factories.CookieStoreFactory;
import S71200URLCommunicator.Interfaces.HttpClientFactory;

public class S71200HTTPSCommunicator extends S71200URLCommunicator{

    private static String PLC_LOGIN_ADDRESS_TEMPLATE = "HTTPS://%s/FormLogin";
    private static String PLC_LOGOUT_ADDRESS_TEMPLATE = "HTTPS://%s/FormLogin?LOGOUT";

    public S71200HTTPSCommunicator(String plcIpAddress, String plcLogin, String plcPassword,
                                   HttpClientFactory clientFactory, CookieStoreFactory cookieStoreFactory){
        super(plcIpAddress, plcLogin, plcPassword, clientFactory, cookieStoreFactory);
    }

    protected String getPlcLoginUrl(){
        return createPlcUrl(PLC_LOGIN_ADDRESS_TEMPLATE);
    }

    protected String getPlcLogoutUrl(){
        return createPlcUrl(PLC_LOGOUT_ADDRESS_TEMPLATE);
    }
}


package S71200URLCommunicator.Implementations;

import S71200URLCommunicator.Communicators.S71200HTTPSCommunicator;
import S71200URLCommunicator.Communicators.S71200URLCommunicator;
import S71200URLCommunicator.Enums.BlackoutCommand;
import S71200URLCommunicator.Factories.CookieStoreFactory;
import S71200URLCommunicator.Factories.S71200SSLContextFactory;
import S71200URLCommunicator.Factories.SelfSignedHttpsClientFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PneumanBlackoutCommunicator{

    private static String PNEUMAN_BLACKOUT_ADDRESS_TEMPLATE = "HTTP://%s/awp/Pneuman%20Control%20Panel/BlackoutPage.html";

    private S71200URLCommunicator _plcCommunicator;
    private SelfSignedHttpsClientFactory _clientFactory;

    public void configure(String plcIpAddress, String plcLogin, String plcPassword) throws Exception{
        _clientFactory = new SelfSignedHttpsClientFactory(new S71200SSLContextFactory());
        _plcCommunicator = new S71200HTTPSCommunicator(plcIpAddress, plcLogin, plcPassword,
                _clientFactory, new CookieStoreFactory());
    }

    public void startBlackout() throws IOException {
        sendBlackoutCommand(BlackoutCommand.Enable);
    }

    public void stopBlackout() throws IOException, Exception {
        sendBlackoutCommand(BlackoutCommand.Disable);
    }

    private void sendBlackoutCommand(BlackoutCommand blackoutCommand) throws IOException {
        HttpPost postMethod = createBlackoutPostMethod(blackoutCommand);
        _plcCommunicator.sendPlcCommand(postMethod);
    }

    private HttpPost createBlackoutPostMethod(BlackoutCommand blackoutCommand) throws UnsupportedEncodingException {
        HttpPost postMethod = new HttpPost(_plcCommunicator.createPlcUrl(PNEUMAN_BLACKOUT_ADDRESS_TEMPLATE));
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("Blackout", String.valueOf(blackoutCommand.ordinal())));
        postMethod.setEntity(new UrlEncodedFormEntity(postParameters));
        return postMethod;
    }
}


package S71200URLCommunicator.Implementations;

import S71200URLCommunicator.Communicators.S71200HTTPSCommunicator;
import S71200URLCommunicator.Communicators.S71200URLCommunicator;
import S71200URLCommunicator.Factories.CookieManagerFactory;
import S71200URLCommunicator.Factories.HttpsConnectionFactory;
import S71200URLCommunicator.Interfaces.URLConnectionFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;

/**
 * Created by macke_000 on 2/26/2015.
 */
public class PneumanBlackoutCommunicator{

    private static String PNEUMAN_BLACKOUT_ADDRESS_TEMPLATE = "HTTP://%1$s/awp/Pneuman%20Control%20Panel/BlackoutPage.html";

    private S71200URLCommunicator _plcCommunicator;
    private URLConnectionFactory _connectionFactory;

    public PneumanBlackoutCommunicator(String plcIpAddress, String plcLogin, String plcPassword){
        _connectionFactory = new HttpsConnectionFactory();
        _plcCommunicator = new S71200HTTPSCommunicator(plcIpAddress, plcLogin, plcPassword,
                _connectionFactory, new CookieManagerFactory());
    }

    public void startBlacout(){
        sendBlackoutCommand(BlackoutCommand.Enable);
    }

    public void stopBlackout(){
        sendBlackoutCommand(BlackoutCommand.Disable);
    }

    private void sendBlackoutCommand(BlackoutCommand blackoutCommand){
        try {
            HttpsURLConnection startBlackoutCon = createBlackoutConnection();
            addBlackoutParameters(blackoutCommand, startBlackoutCon);
            _plcCommunicator.sendPlcCommand(startBlackoutCon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addBlackoutParameters(BlackoutCommand blackoutCommand, HttpsURLConnection blackoutConnection){
        blackoutConnection.addRequestProperty("Blackout", String.valueOf(blackoutCommand.ordinal()));
    }

    private HttpsURLConnection createBlackoutConnection() throws IOException{
        return (HttpsURLConnection)_connectionFactory.createConnection(_plcCommunicator.createPlcUrl(PNEUMAN_BLACKOUT_ADDRESS_TEMPLATE));
    }
}

enum BlackoutCommand{
    Disable,
    Enable
}

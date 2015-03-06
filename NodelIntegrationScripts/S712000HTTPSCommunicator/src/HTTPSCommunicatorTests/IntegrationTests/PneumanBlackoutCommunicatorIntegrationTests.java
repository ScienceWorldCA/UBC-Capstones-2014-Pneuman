package HTTPSCommunicatorTests.IntegrationTests;

import S71200URLCommunicator.Implementations.PneumanBlackoutCommunicator;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PneumanBlackoutCommunicatorIntegrationTests {

    private static String TEST_USER = "admin";
    private static String TEST_PASSWORD = "";
    private static String TEST_IP = "192.168.0.1";

    @Test
    public void TestStartBlackout() {
        boolean callSuccessfull = true;
        PneumanBlackoutCommunicator testComm = new PneumanBlackoutCommunicator(TEST_IP, TEST_USER, TEST_PASSWORD);
        try{
            for(int i = 0; i < 20; i++){
                testComm.startBlackout();
            }

        }catch(Exception e){
            callSuccessfull = false;
        }
        assertTrue(callSuccessfull);
    }

    @Test
    public void TestStopBlackout() {
        boolean callSuccessfull = true;
        PneumanBlackoutCommunicator testComm = new PneumanBlackoutCommunicator(TEST_IP, TEST_USER, TEST_PASSWORD);
        try{
            testComm.stopBlackout();
        }catch(Exception e){
            callSuccessfull = false;
        }
        assertTrue(callSuccessfull);
    }
}

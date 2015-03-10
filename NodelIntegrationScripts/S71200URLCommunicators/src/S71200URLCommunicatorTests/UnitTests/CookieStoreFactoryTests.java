package S71200URLCommunicatorTests.UnitTests;

import S71200URLCommunicator.Factories.CookieStoreFactory;
import org.junit.Test;
import static org.junit.Assert.*;

public class CookieStoreFactoryTests {
    @Test
    public void TestCookieStoreReturned(){
        CookieStoreFactory testFactory = new CookieStoreFactory();
        assertNotNull(testFactory.createCookieStore());
    }
}

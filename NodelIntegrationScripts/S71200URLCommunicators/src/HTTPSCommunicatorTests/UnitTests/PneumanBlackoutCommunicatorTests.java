package HTTPSCommunicatorTests.UnitTests;

import S71200URLCommunicator.Factories.CookieStoreFactory;
import S71200URLCommunicator.Factories.SelfSignedHttpsClientFactory;
import S71200URLCommunicator.Communicators.S71200HTTPSCommunicator;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import org.apache.http.client.CookieStore;

public class PneumanBlackoutCommunicatorTests {

    private static String AUTHENTICATION_COOKIE_NAME = "siemens_ad_session";

    private static String TEST_USER = "TEST";
    private static String TEST_PASSWORD = "TEST";
    private static String TEST_IP = "192.168.0.1";

    private Cookie _correctAuthenticationCookie;

    private CloseableHttpClient _mockLoginClient;
    private CloseableHttpClient _mockLogoutClient;
    private CloseableHttpClient _mockCommandClient;
    private SelfSignedHttpsClientFactory _mockClientFactory;
    private CookieStore _mockStore;
    private CookieStoreFactory _mockCookieStoreFactory;
    private HttpPost _mockCommand;
    private CloseableHttpResponse _mockResponse;

    private S71200HTTPSCommunicator _testCommunicator;

    @Before
    public void setUpMocks() throws Exception {
        _mockResponse = mock(CloseableHttpResponse.class);
        when(_mockResponse.getEntity()).thenReturn(mock(HttpEntity.class));
        _mockLoginClient = mock(CloseableHttpClient.class);
        when(_mockLoginClient.execute(any(HttpPost.class))).thenReturn(_mockResponse);
        _mockLogoutClient = mock(CloseableHttpClient.class);
        when(_mockLogoutClient.execute(any(HttpPost.class))).thenReturn(_mockResponse);
        _mockCommandClient = mock(CloseableHttpClient.class);
        when(_mockCommandClient.execute(any(HttpPost.class))).thenReturn(_mockResponse);

        _mockClientFactory = mock(SelfSignedHttpsClientFactory.class);
        when(_mockClientFactory.createHttpClient(any(CookieStore.class))).thenReturn(_mockLoginClient).thenReturn(_mockCommandClient).thenReturn(_mockLogoutClient);

        _mockCommand = new HttpPost();

        _correctAuthenticationCookie = new BasicClientCookie(AUTHENTICATION_COOKIE_NAME, "");

        _mockStore = mock(CookieStore.class);

        _mockCookieStoreFactory = mock(CookieStoreFactory.class);
        when(_mockCookieStoreFactory.createCookieStore()).thenReturn(_mockStore);

        _testCommunicator = new S71200HTTPSCommunicator(TEST_IP, TEST_USER, TEST_PASSWORD, _mockClientFactory, _mockCookieStoreFactory);
    }


    @Test(expected=IOException.class)
    public void TestFailedLogin() throws Exception {

        List<Cookie> emptyCookieList = new ArrayList<>();

        when(_mockStore.getCookies()).thenReturn(emptyCookieList);

        _testCommunicator.sendPlcCommand(_mockCommand);
    }

    @Test
    public void TestSuccessfulLogin() throws Exception {

        List<Cookie> emptyCookieList = new ArrayList<>();
        List<Cookie> authenticatedCookieList = new ArrayList<>();
        authenticatedCookieList.add(_correctAuthenticationCookie);

        when(_mockStore.getCookies()).thenReturn(authenticatedCookieList).thenReturn(emptyCookieList);

        _testCommunicator.sendPlcCommand(_mockCommand);

        //verify(_mockLoginClient).setRequestProperty(S71200HttpsFields.Login.toString(), TEST_USER);
        //verify(_mockLoginClient).setRequestProperty(S71200HttpsFields.Password.toString(), TEST_PASSWORD);
        //verify(_mockLoginClient).connect();
        //verify(_mockLoginClient).getContent();
    }

    @Test
    public void TestSuccessfulCommand() throws Exception {

        List<Cookie> emptyCookieList = new ArrayList<>();
        List<Cookie> authenticatedCookieList = new ArrayList<>();
        authenticatedCookieList.add(_correctAuthenticationCookie);

        when(_mockStore.getCookies()).thenReturn(authenticatedCookieList).thenReturn(emptyCookieList);

        _testCommunicator.sendPlcCommand(_mockCommand);

        //verify(_mockCommandClient).connect();
        //verify(_mockCommandClient).getContent();
    }

    @Test
    public void TestSuccessfulLogout() throws Exception {

        List<Cookie> emptyCookieList = new ArrayList<>();
        List<Cookie> authenticatedCookieList = new ArrayList<>();
        authenticatedCookieList.add(_correctAuthenticationCookie);

        when(_mockStore.getCookies()).thenReturn(authenticatedCookieList).thenReturn(emptyCookieList);

        _testCommunicator.sendPlcCommand(_mockCommand);

        //verify(_mockLogoutClient).connect();
        //verify(_mockLogoutClient).getContent();
    }

    @Test(expected=IOException.class)
    public void TestFailedConnection() throws Exception {

        //doThrow(new IOException()).when(_mockLoginClient).getContent();

        _testCommunicator.sendPlcCommand(_mockCommand);
    }
}
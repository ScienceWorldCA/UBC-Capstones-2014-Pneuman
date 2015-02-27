package HTTPSCommunicatorTests;

import S71200URLCommunicator.Factories.CookieManagerFactory;
import S71200URLCommunicator.Factories.HttpsConnectionFactory;
import S71200URLCommunicator.Communicators.S71200HTTPSCommunicator;
import S71200URLCommunicator.Enums.S71200HttpsFields;
import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class S71200HTTPSCommunicatorTests {

    private static String AUTHENTICATION_COOKIE_NAME = "siemens_ad_session";

    private static String TEST_USER = "TEST";
    private static String TEST_PASSWORD = "TEST";
    private static String TEST_IP = "192.168.0.1";

    private HttpCookie _correctAuthenticationCookie;

    private HttpsURLConnection _mockLoginConnection;
    private HttpsURLConnection _mockLogoutConnection;
    private HttpsURLConnection _mockCommandConnection;
    private HttpsConnectionFactory _mockConnectionFactory;
    private CookieStore _mockStore;
    private CookieManager _mockCookieManager;
    private CookieManagerFactory _mockCookieManagerFactory;

    private S71200HTTPSCommunicator _testCommunicator;

    @Before
    public void setUpMocks() throws IOException {
        _mockLoginConnection = mock(HttpsURLConnection.class);
        _mockLogoutConnection = mock(HttpsURLConnection.class);
        _mockCommandConnection = mock(HttpsURLConnection.class);
        _mockConnectionFactory = mock(HttpsConnectionFactory.class);
        when(_mockConnectionFactory.createConnection(any(URL.class))).thenReturn(_mockLoginConnection).thenReturn(_mockLogoutConnection);

        _correctAuthenticationCookie = new HttpCookie(AUTHENTICATION_COOKIE_NAME, "");

        _mockStore = mock(CookieStore.class);
        _mockCookieManager = mock(CookieManager.class);
        when(_mockCookieManager.getCookieStore()).thenReturn(_mockStore);

        _mockCookieManagerFactory = mock(CookieManagerFactory.class);
        when(_mockCookieManagerFactory.createCookieManager()).thenReturn(_mockCookieManager);

        _testCommunicator = new S71200HTTPSCommunicator(TEST_IP, TEST_USER, TEST_PASSWORD,
                _mockConnectionFactory, _mockCookieManagerFactory);
    }


    @Test(expected=IOException.class)
    public void TestFailedLogin() throws IOException {

        List<HttpCookie> emptyCookieList = new ArrayList<HttpCookie>();

        when(_mockStore.getCookies()).thenReturn(emptyCookieList);

        _testCommunicator.sendPlcCommand(_mockCommandConnection);
    }

    @Test
    public void TestSuccessfulLogin() throws IOException {

        List<HttpCookie> emptyCookieList = new ArrayList<HttpCookie>();
        List<HttpCookie> authenticatedCookieList = new ArrayList<HttpCookie>();
        authenticatedCookieList.add(_correctAuthenticationCookie);

        when(_mockStore.getCookies()).thenReturn(authenticatedCookieList).thenReturn(emptyCookieList);

        _testCommunicator.sendPlcCommand(_mockCommandConnection);

        verify(_mockLoginConnection).setRequestProperty(S71200HttpsFields.Login.toString(), TEST_USER);
        verify(_mockLoginConnection).setRequestProperty(S71200HttpsFields.Password.toString(), TEST_PASSWORD);
        verify(_mockLoginConnection).connect();
        verify(_mockLoginConnection).getContent();
    }

    @Test
    public void TestSuccessfulCommand() throws IOException {

        List<HttpCookie> emptyCookieList = new ArrayList<HttpCookie>();
        List<HttpCookie> authenticatedCookieList = new ArrayList<HttpCookie>();
        authenticatedCookieList.add(_correctAuthenticationCookie);

        when(_mockStore.getCookies()).thenReturn(authenticatedCookieList).thenReturn(emptyCookieList);

        _testCommunicator.sendPlcCommand(_mockCommandConnection);

        verify(_mockCommandConnection).connect();
        verify(_mockCommandConnection).getContent();
    }

    @Test
    public void TestSuccessfulLogout() throws IOException {

        List<HttpCookie> emptyCookieList = new ArrayList<HttpCookie>();
        List<HttpCookie> authenticatedCookieList = new ArrayList<HttpCookie>();
        authenticatedCookieList.add(_correctAuthenticationCookie);

        when(_mockStore.getCookies()).thenReturn(authenticatedCookieList).thenReturn(emptyCookieList);

        _testCommunicator.sendPlcCommand(_mockCommandConnection);

        verify(_mockLogoutConnection).connect();
        verify(_mockLogoutConnection).getContent();
    }

    @Test(expected=IOException.class)
    public void TestFailedLogout() throws IOException {

        List<HttpCookie> authenticatedCookieList = new ArrayList<HttpCookie>();
        authenticatedCookieList.add(_correctAuthenticationCookie);

        when(_mockStore.getCookies()).thenReturn(authenticatedCookieList);

        _testCommunicator.sendPlcCommand(_mockCommandConnection);
    }

    @Test(expected=IOException.class)
    public void TestFailedConnection() throws IOException {

        doThrow(new IOException()).when(_mockLoginConnection).getContent();

        _testCommunicator.sendPlcCommand(_mockCommandConnection);
    }
}
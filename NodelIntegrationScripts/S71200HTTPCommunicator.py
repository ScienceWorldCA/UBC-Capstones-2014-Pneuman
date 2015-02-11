import urllib
import urllib2
import cookielib


class S71200HTTPCommunicator:

    IP_ADDRESS_REPLACEMENT_TOKEN = "IP_ADDRESS"
    PLC_LOGIN_ADDRESS_TEMPLATE = "HTTP://" + IP_ADDRESS_REPLACEMENT_TOKEN + "/FormLogin"
    PLC_LOGOUT_ADDRESS_TEMPLATE = "HTTP://" + IP_ADDRESS_REPLACEMENT_TOKEN + "/FormLogin?LOGOUT"
    
    PLC_AUTHENTICATION_COOKIE_NAME = "siemens_ad_session"
    HTTP_COOKIE_INDEX = 7

    def __init__(self):
        self.__plc_ip_address = None
        self.__plc_protocol = None
        self.__plc_login = None
        self.__plc_password = None
        self.__plc_login_address = None
        self.__plc_logout_address = None
        self.__configured = False

    def set_configuration(self, plc_ip_address, plc_login, plc_password):
        self.__plc_login = plc_login or ''
        self.__plc_password = plc_password or ''
        self.__plc_ip_address = plc_ip_address
        self.__plc_login_address = self.replace_address_tokens(S71200HTTPSCommunicator.PLC_LOGIN_ADDRESS_TEMPLATE,
                                                         plc_ip_address)
        self.__plc_logout_address = self.replace_address_tokens(S71200HTTPSCommunicator.PLC_LOGOUT_ADDRESS_TEMPLATE,
                                                          plc_ip_address)
        self.__configured = True

    def send_command(self, address, payload):
        if self.__configured:
            url_opener = self.__get_url_opener()
            self.__login_to_plc(url_opener)
            if self.__is_authenticated(url_opener):
                self.__send_command(url_opener, address, payload)
                self.__logout_of_plc(url_opener)
            else:
                raise Exception("Could not authenticate with PLC. Please check PLC settings.")
        else:
            raise Exception("Configure Pneuman Blackout Controller!")
        
    def replace_address_tokens(self, template, ipaddress):
        return template.replace(S71200HTTPSCommunicator.IP_ADDRESS_REPLACEMENT_TOKEN, ipaddress)

    def __get_url_opener(self):
        return urllib2.build_opener(urllib2.HTTPCookieProcessor(cookielib.CookieJar()))

    def __is_authenticated(self, url_opener):
        htp_cookie_jar = url_opener.handlers[S71200HTTPSCommunicator.HTTP_COOKIE_INDEX].cookiejar
        is_authenticated = False
        for cookie in htp_cookie_jar:
            if cookie.name == S71200HTTPSCommunicator.PLC_AUTHENTICATION_COOKIE_NAME:
                is_authenticated = True
        return is_authenticated

    def __send_command(self, url_opener, address, payload):
        data = urllib.urlencode(payload)
        request = urllib2.Request(address, data)
        url_opener.open(request)

    def __login_to_plc(self, url_opener):
        payload = {'Redirection': '', 'Login': self.__plc_login, 'Password': self.__plc_password}
        self.__send_command(url_opener, self.__plc_login_address, payload)

    def __logout_of_plc(self, url_opener):
        payload = {'Redirection': '.'}
        self.__send_command(url_opener, self.__plc_logout_address, payload)



from S71200HTTPSCommunicator import S71200HTTPSCommunicator


class PneumanBlackoutController(S71200HTTPSCommunicator):

    PLC_BLACKOUT_ADDRESS_TEMPLATE = "HTTPS://" + \
                                    S71200HTTPSCommunicator.IP_ADDRESS_REPLACEMENT_TOKEN + \
                                    "/awp/Pneuman%20Control%20Panel/Pages/BlackoutPage.html"
    PNEUMAN_BLACKOUT_START_FLAG = 1
    PNEUMAN_BLACKOUT_STOP_FLAG = 0

    def __init__(self):
        S71200HTTPSCommunicator.__init__(self)
        self.__plc_blackout_address = None
        self.__configured = False

    def set_configuration(self, plc_ip_address, plc_login, plc_password):
        S71200HTTPSCommunicator.set_configuration(self, plc_ip_address, plc_login, plc_password)
        self.__plc_blackout_address = S71200HTTPSCommunicator.replace_address_tokens(self,
            PneumanBlackoutController.PLC_BLACKOUT_ADDRESS_TEMPLATE,plc_ip_address)
        self.__configured = True

    def start_blackout(self):
        package = self.__create_blackout_package(PneumanBlackoutController.PNEUMAN_BLACKOUT_START_FLAG)
        S71200HTTPSCommunicator.send_command(self, self.__plc_blackout_address, package)

    def stop_blackout(self):
        package = self.__create_blackout_package(PneumanBlackoutController.PNEUMAN_BLACKOUT_STOP_FLAG)
        S71200HTTPSCommunicator.send_command(self, self.__plc_blackout_address, package)

    def __create_blackout_package(self, blackout_flag):
        return {'Blackout': blackout_flag}

test = PneumanBlackoutController()
test.set_configuration("192.168.0.1", "admin", "")
#test.start_blackout()
test.stop_blackout()
from PneumanBlackoutController import PneumanBlackoutController

param_ipAddress = Parameter('{"title":"IP Address", "desc":"The IP address to connect to.", "schema":{"type":"string"}}')
param_plcLogin = Parameter('{"title":"PLC Login", "desc":"The login name for the PLC.", "schema":{"type":"string"}}')
param_plcPassword = Parameter('{"title":"PLC Password", "desc":"The password for the PLC (Leave blank if no password).", "schema":{"type":"string"}}')
local_event_Error = LocalEvent('{"title":"Error","desc":"Error state."}')

blackout_controller = PneumanBlackoutController()

def main():
    if param_ipAddress and param_plcLogin:
        blackout_controller.set_configuration(param_ipAddress, param_plcLogin, param_plcPassword)
    else:
        local_event_Error.emit('Configuration not set!')

def local_action_Start(arg):
    '''{"title":"Start","desc":"Starts Pneumans Blackout Period.", "group":"Blackout"}'''
    try:
        blackout_controller.start_blackout()
    except Exception, e:
        local_event_Error.emit(e)

def local_action_Stop(arg):
    '''{"title":"Stop","desc":"Starts Pneumans Blackout Period.", "group":"Blackout"}'''
    try:
        blackout_controller.stop_blackout()
    except Exception, e:
        local_event_Error.emit(e)
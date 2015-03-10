package S71200URLCommunicator.Interfaces;

import javax.net.ssl.SSLContext;

public interface SSLContextFactory {
    SSLContext getContext() throws Exception;
}

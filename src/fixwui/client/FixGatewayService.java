package fixwui.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("FixGatewayService")
public interface FixGatewayService extends RemoteService {
    
    ArrayList<String> getSessionList() throws IllegalArgumentException;
    void connectSession(String session) throws IllegalArgumentException;
    void disconnectSession(String session) throws IllegalArgumentException;
    void sendOrder(String session) throws IllegalArgumentException;
}

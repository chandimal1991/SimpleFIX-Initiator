package fixwui.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface FixGatewayServiceAsync {
    
    void getSessionList(AsyncCallback<ArrayList<String>> callback);
    void connectSession(String session,AsyncCallback<Void> callback);
    void disconnectSession(String session,AsyncCallback<Void> callback);
}

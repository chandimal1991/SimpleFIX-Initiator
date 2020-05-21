package fixwui.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface FixGatewayServiceAsync {
    
    void getSessionList(AsyncCallback<ArrayList<String>> callback);
    void connectSession(AsyncCallback<Void> callback);
    void disconnectSession(AsyncCallback<Void> callback);
}

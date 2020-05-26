package fixwui.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Fix_wui implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String          SERVER_ERROR      = "An error occurred while "
	    + "attempting to contact the server. Please check your network "
	    + "connection and try again.";
    
    /**
     * Create a remote service proxy to talk to the server-side Greeting
     * service.
     */
    private final FixGatewayServiceAsync fixGatewayService = GWT.create(FixGatewayService.class);
    
    private ListBox                      sessionList;
    
    private ListBox                      msgTypeList;
    
    private Button                       btnConnect;
    
    private Button                       btnDisconnect;
    
    private Button                       btnSend;
    
    /**
     * This is the entry point method.
     */
    @Override
    public void onModuleLoad() {
	// Use RootPanel.get() to get the entire body element
	RootPanel mainPanel = RootPanel.get("mainArea");
	mainPanel.setSize("1024", "768");
	mainPanel.getElement().getStyle().setPosition(Position.RELATIVE);
	
	sessionList = new ListBox();
	mainPanel.add(sessionList, 630, 10);
	sessionList.setSize("258px", "18px");
	sessionList.setName("sessionlist");
	
	msgTypeList = new ListBox();
	mainPanel.add(msgTypeList, 630, 42);
	msgTypeList.setSize("258px", "18px");
	
	btnConnect = new Button("Connect");
	mainPanel.add(btnConnect, 630, 100);
	
	btnDisconnect = new Button("Disconnect");
	mainPanel.add(btnDisconnect, 720, 100);
	
	btnSend = new Button("Send");
	mainPanel.add(btnSend, 830, 100);
	
	msgTypeList.addItem("New Single Order (35=D)");
	msgTypeList.addItem("Order Replace Request (35=G)");
	msgTypeList.addItem("Order Cancel Request (35=F)");
	
	fixGatewayService.getSessionList(new AsyncCallback<ArrayList<String>>() {
	    
	    @Override
	    public void onFailure(final Throwable caught) {
		// TODO Auto-generated method stub
		
	    }
	    
	    @Override
	    public void onSuccess(final ArrayList<String> result) {
		for ( String session : result ) {
		    sessionList.addItem(session);
		}
		;
	    }
	    
	});
	

	// Listen for mouse events on the connect button.
	btnConnect.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
    	  String session = sessionList.getSelectedValue();
    	  fixGatewayService.connectSession(session,new AsyncCallback<Void>() {
    		  
    		  @Override
    		    public void onFailure(final Throwable caught) {
    			// TODO Auto-generated method stub
    			
    		    }
    		    
				@Override
				public void onSuccess(Void result) {
					// TODO Auto-generated method stub
					
				}
    	  });
   
      }
    });
	
	// Listen for mouse events on the Disconnect button.
		btnDisconnect.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	    	  String session = sessionList.getSelectedValue();
	    	  fixGatewayService.disconnectSession(session,new AsyncCallback<Void>() {
	    		  
	    		  @Override
	    		    public void onFailure(final Throwable caught) {
	    			// TODO Auto-generated method stub
	    			
	    		    }
	    		    
	    		    @Override
	    		    public void onSuccess(Void result) {
	    		    	System.out.println(result);
	    		    }
	    	  });
	   
	      }
	    });
		
	// Listen for mouse events on the Send button.
		btnSend.addClickHandler(new ClickHandler() {
		   public void onClick(ClickEvent event) {
			    String session = sessionList.getSelectedValue();
			    fixGatewayService.sendOrder(session,new AsyncCallback<Void>() {
			    		  
			        @Override
			    	public void onFailure(final Throwable caught) {
			    	// TODO Auto-generated method stub
			    			
			    	}
			    		    
			        @Override
			    	public void onSuccess(Void result) {
			    		  System.out.println(result);
			        }
			    });
			   
			}
		  });
		
		
	
    }
    
    
     
}

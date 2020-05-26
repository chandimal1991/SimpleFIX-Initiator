package fixwui.server;

import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import simplefix.Application;
import simplefix.Engine;
import simplefix.EngineFactory;
import simplefix.Message;
import simplefix.MsgType;
import simplefix.Session;
import simplefix.Tag;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fixwui.client.FixGatewayService;

/**
 * The server side implementation of the RPC service.
 */

@SuppressWarnings("serial")
public class FixGatewayServiceImpl extends RemoteServiceServlet implements
FixGatewayService {
    
    private static EngineFactory _engineFact;
    private Engine _engine;
    
    @Override
    public void init(final ServletConfig config) throws ServletException {
	super.init(config);
	
	try {
	    
	    Class<?> classobj = Class.forName("simplefix.quickfix.EngineFactory");
	    Object engineobj = classobj.newInstance();
	    
	    if ( engineobj instanceof EngineFactory ) {
		
		_engineFact = (EngineFactory) engineobj;
		_engine = _engineFact.createEngine();
		_engine.initEngine("banzai.cfg");
		
		Application application = new _Application();
		
		_engine.startInProcess(application);
		
		
	    }
	} catch ( Exception e ) {
	    e.printStackTrace();
	}
	
    }
    
    @Override
    public ArrayList<String> getSessionList() throws IllegalArgumentException {
    	
	ArrayList<String> sessions = new ArrayList<String>();
	
	for ( Session session : _engine.getAllSessions() ) {
	    sessions.add(session.getSenderCompID() + "<-->" + session.getTargetCompID());
	}
	
	Collections.sort(sessions);
	return sessions;
    }
    
    @Override
    public void connectSession(String sessionId) throws IllegalArgumentException{
    	
    	Message ordMsg = _engineFact.createMessage(MsgType.ORDER_SINGLE);

        ordMsg.setValue(Tag.ClOrdID, "Cld-1234");
        ordMsg.setValue(Tag.Symbol, "6758");
        ordMsg.setValue(Tag.Side, "1");
        ordMsg.setValue(Tag.OrderQty, "1000");
        ordMsg.setValue(Tag.Price, "123.45");
        ordMsg.setValue(Tag.OrdType, "2");
        ordMsg.setValue(Tag.HandlInst, "3");
        ordMsg.setValue(Tag.TransactTime,"20200508-04:36:42");
        
    	for ( Session session : _engine.getAllSessions() ) {
    		if(sessionId.equals(session.getSenderCompID() + "<-->" + session.getTargetCompID())) {
    			
    			//_engine.lookupSession(session.getSenderCompID(),session.getTargetCompID());
    			quickfix.Session.lookupSession(session.getSenderCompID(),session.getTargetCompID()).logon();
    			//application.onLogon(session);
    			//session.sendAppMessage(ordMsg);
    		}
    	}
    		
    }
    
    @Override
    public void disconnectSession(String sessionId) throws IllegalArgumentException{
    	try {
    		
    		for ( Session session : _engine.getAllSessions() ) {
    			
        		if(sessionId.equals(session.getSenderCompID() + "<-->" + session.getTargetCompID())) {
        			
        			if(quickfix.Session.lookupSession(session.getSenderCompID(),session.getTargetCompID()).isLoggedOn()) {
        				
        				quickfix.Session.lookupSession(session.getSenderCompID(),session.getTargetCompID()).logout();
        			}
        		}
        	}
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    private static class _Application implements Application {
	
	public _Application() {
	}
	
	@Override
	public void onAppMessage(final Message arg0, final Session arg1) {
	    // TODO Auto-generated method stub
	    
	}
	
	@Override
	public void onLogon(final Session session) {
		
		System.out.println("LoggedOn==>" + session.getSenderCompID() + "<-->" + session.getTargetCompID());
         
	}
	
	@Override
	public void onLogout(final Session session) {
	    // TODO Auto-generated method stub
		System.out.println("logout==>" + session.getSenderCompID() + "<-->" + session.getTargetCompID());
	    
	}
    };
    
}

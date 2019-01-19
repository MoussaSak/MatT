package unv.skikda.View;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

/**
 * This is the monitor agent it launches the application
 * for testing a multi-agent system 
 */

public class TestMonitor extends Agent{
	
	private static final long serialVersionUID = 440326977904006807L;
	
	@Override
	protected void setup() {
		new Main();
	}

	public static void main(String[] args) throws Exception{
		ContainerController container = null;
		AgentController agentController = null;
		Properties properties = new ExtendedProperties();
		properties.setProperty(Profile.MAIN, "true");
		Profile profile = new ProfileImpl(properties);
                
        Runtime.instance().setCloseVM(true);
        Runtime runtime = Runtime.instance();
        

        if (profile.isMain()) {
            container = runtime.createMainContainer(profile);
        } else {
            container = runtime.createAgentContainer(profile);
        }
        TestMonitor monitor = new TestMonitor();
		agentController = container.acceptNewAgent("TestMonitor", monitor);
        agentController.start();
	}
}

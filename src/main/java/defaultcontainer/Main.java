package defaultcontainer;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Main {

    public static void main(String[] args) throws StaleProxyException {

        Runtime runtime = Runtime.instance();

        Properties properties = new ExtendedProperties();
        properties.setProperty(Profile.GUI, "true");
        Profile profile = new ProfileImpl(properties);
        AgentContainer mainContainer = runtime.createMainContainer(profile);

        int M=10,N=10;
        int[][] grid = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                         { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
                         { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
                         { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                         { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                         { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
                         { 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
                         { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
                         { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
                         { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
        };
        AgentController agentsSet;
        for(int i=0;i<M;i++){
            for(int j=0;j<N;j++){
                agentsSet = mainContainer.createNewAgent("agent"+i+j, "defaultcontainer.GameOfLifeAgent", new Object[]{});
                agentsSet.start();
            }
        }


    }
}

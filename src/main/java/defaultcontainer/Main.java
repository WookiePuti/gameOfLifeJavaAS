package defaultcontainer;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

import java.util.concurrent.ThreadLocalRandom;


public class Main {

    public static void main(String[] args) throws ControllerException {

        Runtime runtime = Runtime.instance();

        Properties properties = new ExtendedProperties();
        properties.setProperty(Profile.GUI, "true");
        Profile profile = new ProfileImpl(properties);
        AgentContainer mainContainer = runtime.createMainContainer(profile);
        int M= 10;
        int N= M;

        int[][] grid = new int[M][N];
        for(int i=0;i<M;i++){
            for (int j=0;j<N;j++){
                if (Math.random()<0.5){
                    grid[i][j]=1;
                }
                else{
                    grid[i][j]=0;
                }
            }
        }
        
        for(int i=0;i<M;i++){
            for(int j=0;j<N;j++){
                mainContainer.createNewAgent("agent"+i+j, "defaultcontainer.GameOfLifeAgent", new Object[]{grid[i][j] == 1,i,j,M,N});
            }
        }
        for(int i=0;i<M;i++){
            for(int j=0;j<N;j++){
                mainContainer.getAgent("agent"+i+j).start();
            }
        }



    }
}

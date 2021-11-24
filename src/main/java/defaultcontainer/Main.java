package defaultcontainer;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;

import java.util.Map;


public class Main {

    public static void main(String[] args) throws ControllerException {

        Runtime runtime = Runtime.instance();

        Properties properties = new ExtendedProperties();
        properties.setProperty(Profile.GUI, "false");
        Profile profile = new ProfileImpl(properties);
        AgentContainer mainContainer = runtime.createMainContainer(profile);
        Map<String, String> env = System.getenv();
        String size = env.get("size");
        int M;
        if (size != null && !size.equals("")) {
            M = Integer.valueOf(size);
            System.out.println("Using size from env: " + M);
        } else {
            System.out.println("Env not set, using default (10)");
            M = 10;
        }

        int N = M;

        int[][] grid = new int[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (Math.random() < 0.5) {
                    grid[i][j] = 1;
                } else {
                    grid[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                mainContainer.createNewAgent("agent" + i + j, "defaultcontainer.GameOfLifeAgent", new Object[]{grid[i][j] == 1, i, j, M, N});
            }
        }
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                mainContainer.getAgent("agent" + i + j).start();
            }
        }


    }
}

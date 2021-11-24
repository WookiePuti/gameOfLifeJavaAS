package defaultcontainer;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

import java.util.ArrayList;
import java.util.List;

public class GameOfLifeAgent extends Agent {

    private boolean isAlive;

    /*_____________
    * |_X_|_X_|_X_|
    * |_X_|_O_|_X_|
    * |_X_|_X_|_X_|
    * -------------
    */
    private GameOfLifeAgent[] neighbourhood;
    private List<AID> otherAgents = new ArrayList<>();
    @Override
    public void setup() {
        System.out.println("agent: "+this.getAID()+" started");
        Object[] args = getArguments();
        this.isAlive = (boolean)args[0];
        this.neighbourhood = (GameOfLifeAgent[])args[1];


        addBehaviour(new CyclicBehaviourUpdateState());
    }

    private void buildNeighbourhood(){
        otherAgents.add(new AID("",AID.ISLOCALNAME));
        otherAgents.add(new AID("",AID.ISLOCALNAME));
        otherAgents.add(new AID("",AID.ISLOCALNAME));
        otherAgents.add(new AID("",AID.ISLOCALNAME));
        otherAgents.add(new AID("",AID.ISLOCALNAME));
        otherAgents.add(new AID("",AID.ISLOCALNAME));
        otherAgents.add(new AID("",AID.ISLOCALNAME));
        otherAgents.add(new AID("",AID.ISLOCALNAME));
    }

    private class CyclicBehaviourUpdateState extends CyclicBehaviour {
        public void action() {
            determineIfAliveOrDead();
        }
    }

    private int countNeighbours(){
        int aliveAgents =0;
        for(GameOfLifeAgent agent:neighbourhood){
            if(agent.getAliveState()){
                aliveAgents+=1;
            }
        }
        return aliveAgents;
    }

    private void determineIfAliveOrDead(){
        if(this.isAlive && (countNeighbours()==2 || countNeighbours()==3)){
            return;
        }else if(!isAlive && countNeighbours()==3){
            this.isAlive=true;
        }else{
            this.isAlive=false;
        }
    }

    public boolean getAliveState(){
        return isAlive;
    }

}

package defaultcontainer;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameOfLifeAgent extends Agent {

    private Boolean isAlive;
    private Integer x,y,M,N,aliveNeighbours;

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
        this.x=(Integer)args[1];
        this.y=(Integer)args[2];
        this.M=(Integer)args[3];
        this.N=(Integer)args[4];

        try {
            buildNeighbourhood();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addBehaviour(new CyclicBehaviourUpdateState());
    }

    private void buildNeighbourhood() throws IOException {
        if(isCellInsideGrid(x+1,y+1)) {
            otherAgents.add(new AID("agent" + (x + 1) + (y + 1), AID.ISLOCALNAME));
        }
        if(isCellInsideGrid(x,y+1)){
            otherAgents.add(new AID("agent"+(x)+(y+1),AID.ISLOCALNAME));
        }
        if(isCellInsideGrid(x+1,y)) {
            otherAgents.add(new AID("agent" + (x + 1) + (y), AID.ISLOCALNAME));
        }
        if(isCellInsideGrid(x+1,y-1)) {
            otherAgents.add(new AID("agent" + (x + 1) + (y - 1), AID.ISLOCALNAME));
        }
        if(isCellInsideGrid(x,y-1)){
            otherAgents.add(new AID("agent"+(x)+(y-1),AID.ISLOCALNAME));
        }
        if(isCellInsideGrid(x-1,y-1)) {
            otherAgents.add(new AID("agent" + (x - 1) + (y - 1), AID.ISLOCALNAME));
        }
        if(isCellInsideGrid(x-1,y)) {
            otherAgents.add(new AID("agent" + (x - 1) + (y), AID.ISLOCALNAME));
        }
        if(isCellInsideGrid(x-1,y+1)){
            otherAgents.add(new AID("agent"+(x-1)+(y+1),AID.ISLOCALNAME));
        }

    }

    private boolean isCellInsideGrid(int x,int y){
        if (x<0 || y<0 || x>M-1 || y>N-1)
            return false;
        else
            return true;
    }

    private void informNeighbours() throws IOException {
        ACLMessage aclmsg = new ACLMessage(ACLMessage.REQUEST);
        for(AID i:otherAgents){
            aclmsg.addReceiver(i);
        }
        aclmsg.setContentObject(this.isAlive);
        send(aclmsg);
    }

    private class CyclicBehaviourUpdateState extends CyclicBehaviour {
        public void action() {
            ACLMessage msg = null;
            aliveNeighbours=0;
            try {
                informNeighbours();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(int i=0; i<otherAgents.size(); i++){
                msg = receive();

                if(msg!=null){
                    try {
                        if((boolean)msg.getContentObject()){
                            aliveNeighbours += 1;
                        }
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                }
            }
            determineIfAliveOrDead();
        }
    }

    private void determineIfAliveOrDead(){
        if(this.isAlive && (aliveNeighbours==2 || aliveNeighbours==3)){
            return;
        }else if(!isAlive && aliveNeighbours==3){
            this.isAlive=true;
        }else{
            this.isAlive=false;
        }
    }


}

package defaultcontainer;

import jade.core.Agent;

public class TestAgent extends Agent {

    @Override
    public void setup() {
        System.out.println("test test test");
    }

    @Override
    protected void takeDown(){
        System.out.println("Good bye test");
    }
}

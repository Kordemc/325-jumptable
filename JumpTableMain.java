 import java.util.HashMap;
 enum State {
    IDLE,
    STACK,
    QUEUE,
    LIST
 }

interface StateEnterExitMeth{
    public void invoke();
}

interface StateStayMeth{
    public boolean invoke();
}

 class Screen{
    private State state;
    private HashMap<State, StateEnterExitMeth> stateEnterMeths;
    private HashMap<State, StateStayMeth> stateStayMeths;
    private HashMap<State, StateEnterExitMeth> stateExitMeths;
    
    public Screen(){
        stateEnterMeths = new HashMap<>();

    }
    public void doState(){
        if (stateStayMeths.containsKey(state)) {
            stateStayMeths.get(state).invoke();
        }
    } 
 }




public class JumpTableMain{
    public static void main(String[] args){
        Screen screen = new Screen();
        boolean keepRunning = true;
        while(keepRunning){
            //keepRunning = screen.doState;
        }

    }
 }
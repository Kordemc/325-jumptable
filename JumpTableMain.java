import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;


 enum State {
    IDLE,
    STACK,
    QUEUE,
    LIST;
 }

interface StateEnterExitMeth{
    public void invoke();
}

interface StateStayMeth{
    public boolean invoke();
}

class Screen{
    private State state;
    final String LISTFILE = "C:\\Users\\korde\\Downloads\\325 jumptable\\list.txt";
    final String QUEUEFILE = "C:\\Users\\korde\\Downloads\\325 jumptable\\queue.txt";
    final String STACKFILE = "C:\\Users\\korde\\Downloads\\325 jumptable\\stack.txt";
    private HashMap<State, StateEnterExitMeth> stateEnterMeths;
    private HashMap<State, StateStayMeth> stateStayMeths;
    private HashMap<State, StateEnterExitMeth> stateExitMeths;
    Stack <Character> stack = new Stack<>();
    ArrayList<Character> list = new ArrayList<>();
    Queue<Character>queue = new PriorityQueue<>();
    
    public Screen(){
        stateEnterMeths = new HashMap<>();
        stateStayMeths = new HashMap<>();
        stateExitMeths = new HashMap<>();

        stateEnterMeths.put(State.IDLE, this::stateEnterIdle);
        stateEnterMeths.put(State.LIST, this::stateEnterList);
        stateEnterMeths.put(State.STACK, this::stateEnterStack);
        stateEnterMeths.put(State.QUEUE, this::stateEnterQueue);

        stateStayMeths.put(State.IDLE, this::stateStayIdle);
        stateStayMeths.put(State.LIST, this::stateStayList);
        stateStayMeths.put(State.STACK, this::stateStayStack);
        stateStayMeths.put(State.QUEUE, this::stateStayQueue);

        stateExitMeths.put(State.IDLE, this::stateExitIdle);
        stateExitMeths.put(State.LIST, this::stateExitList);
        stateExitMeths.put(State.STACK, this::stateExitStack);
        stateExitMeths.put(State.QUEUE, this::stateExitQueue);
        
        state = State.LIST;
        changeState(State.IDLE);
    }

    // state methods
    public void changeState(State newState){
        if (state != newState){
            state = newState;
            if (stateEnterMeths.containsKey(newState)){
                stateEnterMeths.get(newState).invoke();
            }
        }
    }

    
    public boolean  doState(){
        return stateStayMeths.get(state).invoke();
    } 
 
    private void stateEnterIdle(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Stack");
        System.out.println("2. Queue");
        System.out.println("3. List");
        System.out.println("4. Quit");
        System.out.print("? ");

        String input = scanner.nextLine();
        if (input.equals("1")){
            changeState(State.STACK);
        }
        
        else if(input.equals("2")){
            changeState(State.QUEUE);
        }

        else if(input.equals("3")){
            changeState(State.LIST);
        }
        
        else if(input.equals("4")){
            doState();
        }    
        else{System.out.println(input);}
    }

    private void stateEnterStack(){
        File file = new File(STACKFILE);
        //System.out.println(file.canRead());
        
        try (Scanner reader = new Scanner(file)){
            reader.useDelimiter(",");
            while(reader.hasNext()){
                stack.push(reader.next().charAt(0));
            }
        } catch (FileNotFoundException e) {
        }
    
        //System.out.println(stack);


    }

    private void stateEnterQueue(){
        File file = new File(QUEUEFILE);
        //System.out.println(file.canRead());
        
        try (Scanner reader = new Scanner(file)){
            reader.useDelimiter(",");
            while(reader.hasNext()){
                queue.add(reader.next().charAt(0));
            }
            System.out.println(queue);
        } catch (FileNotFoundException e) {
        }
    }

    private void stateEnterList(){System.out.println(".()");}

    private boolean stateStayIdle(){ return false;}

    private boolean stateStayStack(){ 
        while(state == State.STACK){
            for (int idx = stack.size()-1; idx > -1; idx--) {
                //System.out.print("");
                System.out.println("| " + stack.elementAt(idx) + " |");
            }

            System.out.println("|___|");
            Scanner scanner = new Scanner(System.in);
            System.out.println("1. Push");
            System.out.println("2. Pop");
            System.out.println("3. Save & Move to Queue");
            System.out.println("4. Save & Move to List");
            System.out.println("5. Save & Quit");
            System.out.print("? ");

            String input = scanner.nextLine();
            //System.out.println(input);
            switch (Character.getNumericValue(input.charAt(0))) {
                case 1:
                    stack.push(input.charAt(2));
                    break;
                case 2:
                    if(!stack.isEmpty()){
                        stack.pop();
                    }
                    break;
                case 3:
                    stateExitStack();
                    changeState(State.QUEUE);
                    stateEnterQueue();
                    break;
                case 4:
                    stateExitStack();
                    changeState(State.LIST);
                    stateEnterList();
                    break;
                case 5:
                    return false;
                default:
                    throw new AssertionError();
            }
        }
        System.out.println("\f");
        //System.out.flush();
        return true;
    }

    private boolean stateStayQueue(){ 
        while(state == State.QUEUE){
            for (int idx = queue.size()-1; idx > -1; idx--) {
                //System.out.println("|___|");
                System.out.println("| " + queue.toString().charAt(idx) + " |");
            }

            //System.out.println("|___|");
            Scanner scanner = new Scanner(System.in);
            System.out.println("1. Enqueue");
            System.out.println("2. Dequeue");
            System.out.println("3. Save & Move to Stack");
            System.out.println("4. Save & Move to List");
            System.out.println("5. Save & Quit");
            System.out.print("? ");

            String input = scanner.nextLine();
            //System.out.println(input);
            switch (Character.getNumericValue(input.charAt(0))) {
                case 1:
                    queue.add(input.charAt(2));
                    break;
                case 2:
                    if(!queue.isEmpty()){
                        queue.remove();
                    }
                    break;
                case 3:
                    stateExitStack();
                    changeState(State.STACK);
                    stateEnterQueue();
                    break;
                case 4:
                    stateExitStack();
                    changeState(State.LIST);
                    stateEnterList();
                    break;
                case 5:
                    return false;
                default:
                    throw new AssertionError();
            }
        }
        System.out.println("\f");
        //System.out.flush();
        return true;
    }


    private boolean stateStayList(){ return false;}

    private void stateExitIdle(){}

    private void stateExitStack(){
        try {
            FileWriter writer = new FileWriter(STACKFILE);
            for (int idx = 0; idx < stack.size(); idx++) {
                writer.write(stack.get(idx)+",");
                
            }
            writer.close();
            
        } catch (IOException e) {
        }
    }

    private void stateExitQueue(){}

    private void stateExitList(){}
}



public class JumpTableMain{
    public static void main(String[] args){
        Screen screen = new Screen();
        boolean keepRunning = true;
        while(keepRunning){
            keepRunning = screen.doState();
        }

    }
 }
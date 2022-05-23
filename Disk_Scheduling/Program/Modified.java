/*
Modified Algorithm is similar to Scan algorithm but with some modification.
The Processor should seek the head to the first position of the cyclinder then,
start to serve the requests as it goes from left to right.
It's a good algorithm if and only if the head position at first was near to the zero position.
*/
import java.util.ArrayList;
import java.util.Collections;

public class Modified {
    ArrayList<Integer> requests = new ArrayList<>();
    int head;
    int totalMovement = 0;
    StringBuilder output;
    Modified(ArrayList<Integer> requests, int head) {
        Assignment2.copyArray(this.requests, requests);
        this.head = head;
        output = new StringBuilder();
        output.append("============ Modified ============\n");
        //System.out.println("============ Modified ============");
        Collections.sort(this.requests);
        Run();
        output.append("\nTotal Movement of Modified = ").append(totalMovement);
        //System.out.println("\nTotal Movement of Modified = " + Run());
    }

    public void Run() {
        output.append(head);
        //System.out.print(head);
        if (head != 0){
            totalMovement += head;
            head = 0;
            output.append("  ->  ").append(head);
            //System.out.print("  ->  " + head);
        }
        while (!requests.isEmpty()) {
            totalMovement += Math.abs(requests.get(0) - head);
            head = requests.get(0);
            requests.remove(0);
            output.append("  ->  ").append(head);
            //System.out.print("  ->  " + head);
        }

    }
}

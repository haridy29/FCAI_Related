import java.util.ArrayList;
import java.util.Collections;

public class LOOK {
    ArrayList<Integer> requests = new ArrayList<>();
    int head;
    int totalMovement = 0;
    StringBuilder output;
    LOOK(ArrayList<Integer> requests, int head) {
        Assignment2.copyArray(this.requests, requests);
        this.head = head;
        output = new StringBuilder();
        output.append("============ L O O K ============\n");
        //System.out.println("============ L O O K ============");
        Collections.sort(this.requests);
        Run();
        output.append("\nTotal Movement of L O O K = ").append(totalMovement);
        //System.out.println("\nTotal Movement of L O O K = " + Run());

    }

    int getNear() {
        int nearest = -1;
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i) >= head) {
                break;
            }
            nearest = i;
        }
        return nearest;
    }

    public void Run() {
        output.append(head);
        //System.out.print(head);
        int idx = getNear();
        if (idx == -1) {
            for (int i = 0; i < requests.size(); i++) {
                totalMovement += Math.abs(requests.get(i) - head);
                head = requests.get(i);
                output.append("  ->  ").append(head);
                //System.out.print("  ->  " + head);
            }
        } else {
            for (int i = idx; i > -1; i--) {
                totalMovement += Math.abs(requests.get(i) - head);
                head = requests.get(i);
                output.append("  ->  ").append(head);
                //System.out.print("  ->  " + head);
            }
            for (int i = idx + 1; i < requests.size(); i++) {
                totalMovement += Math.abs(requests.get(i) - head);
                head = requests.get(i);
                output.append("  ->  ").append(head);
                //System.out.print("  ->  " + head);
            }
        }
    }

}

import java.util.ArrayList;
import java.util.Collections;

public class C_LOOK {
    ArrayList<Integer> requests = new ArrayList<>();
    int head;
    int totalMovement = 0;
    StringBuilder output;
    C_LOOK(ArrayList<Integer> requests, int head) {
        Assignment2.copyArray(this.requests, requests);
        this.head = head;
        output = new StringBuilder();
        output.append("============ C _ L O O K ============\n");
        //System.out.println("============ C _ L O O K ============");
        Collections.sort(this.requests);
        Run();
        output.append("\nTotal Movement of C _ L O O K = ").append(totalMovement);
        //System.out.println("\nTotal Movement of C _ L O O K = " + Run());

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
            for (int i = requests.size() - 1 ; i > idx; i--) {
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
            for (int i = requests.size()-1; i > idx; i--) {
                totalMovement += Math.abs(requests.get(i) - head);
                head = requests.get(i);
                output.append("  ->  ").append(head);
                //System.out.print("  ->  " + head);
            }
        }
    }

}

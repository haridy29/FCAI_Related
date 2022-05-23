import java.util.ArrayList;
import java.util.Collections;

public class SCAN {
    ArrayList<Integer> requests = new ArrayList<>();
    int head;
    int totalMovement = 0;
    StringBuilder output;
    SCAN(ArrayList<Integer> requests, int head) {
        Assignment2.copyArray(this.requests, requests);
        this.head = head;
        output = new StringBuilder();
        output.append("============ S C A N ============\n");
        //System.out.println("============ S C A N ============");
        Collections.sort(this.requests);
        Run();
        output.append("\nTotal Movement of S C A N = ").append(totalMovement);
        //System.out.println("\nTotal Movement of S C A N = " + Run());

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
        int firstHead = head;
        output.append(head);
        //System.out.print(head);
        int idx = getNear();
        if (idx == -1) {
            totalMovement += head;
            head = 0;
            if(firstHead != 0) {
                output.append("  ->  ").append(head);
                //System.out.print("  ->  " + head);
            }
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
            if(idx != requests.size()-1){
                totalMovement += head;
                head = 0;
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

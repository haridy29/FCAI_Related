import java.util.ArrayList;
import java.util.Collections;

public class C_SCAN {
    ArrayList<Integer> requests = new ArrayList<>();
    int head, end;
    int totalMovement = 0;
    StringBuilder output;
    C_SCAN(ArrayList<Integer> requests, int head, int end) {
        Assignment2.copyArray(this.requests, requests);
        this.head = head;
        this.end = end;
        output = new StringBuilder();
        output.append("============ C _ S C A N ============\n");
        //System.out.println("============ C _ S C A N ============");
        Collections.sort(this.requests);
        Run();
        output.append("\nTotal Movement of C _ S C A N = ").append(totalMovement);
        //System.out.println("\nTotal Movement of C _ S C A N = " + Run());

    }

    int getNear() {
        int nearest = -2;
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i) >= head) {
                break;
            }
            nearest = i;
        }
        return nearest + 1;
    }

    public void Run() {
        int firstHead = head;
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
            for (int i = idx; i < requests.size(); i++) {
                totalMovement += Math.abs(requests.get(i) - head);
                head = requests.get(i);
                output.append("  ->  ").append(head);
                //System.out.print("  ->  " + head);
            }
            if (idx != requests.size()) {
                totalMovement += (end - head);
                head = end;
                output.append("  ->  ").append(head);
                //System.out.print("  ->  " + head);
                //totalMovement += end + 1;
                totalMovement += end;
                head = 0;
                output.append("  ->  ").append(head);
                //System.out.print("  ->  " + head);
            }else{
                totalMovement += head;
                head = 0;
                output.append("  ->  ").append(head);
                //System.out.print("  ->  " + head);
            }
            for (int i = 0; i < idx; i++) {
                totalMovement += Math.abs(requests.get(i) - head);
                head = requests.get(i);
                output.append("  ->  ").append(head);
                //System.out.print("  ->  " + head);
            }
        }
    }


}

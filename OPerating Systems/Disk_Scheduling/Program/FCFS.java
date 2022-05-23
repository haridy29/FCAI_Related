import java.util.ArrayList;

public class FCFS {
    ArrayList<Integer> requests = new ArrayList<>();
    int head;
    int totalMovement = 0;

    StringBuilder output;
    FCFS(ArrayList<Integer> requests, int head) {
        Assignment2.copyArray(this.requests, requests);
        this.head = head;
        output = new StringBuilder();
        output.append("============ F C F S ============\n");
        Run();
        output.append("\nTotal Movement of F C F S = ").append(totalMovement);
        /*System.out.println("============ F C F S ============");
        System.out.println("\nTotal Movement of F C F S = " + Run());*/
    }

    public void Run() {
        output.append(head);
        //System.out.print(head);
        while (!requests.isEmpty()) {
            totalMovement += Math.abs(requests.get(0) - head);
            head = requests.get(0);
            requests.remove(0);
            output.append("  ->  ").append(head);
            //System.out.print("  ->  " + head);
        }
    }
}

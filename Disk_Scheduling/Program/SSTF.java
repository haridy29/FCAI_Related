import java.util.ArrayList;

public class SSTF {
    ArrayList<Integer> requests = new ArrayList<>();
    int head;
    int totalMovement = 0;
    StringBuilder output;
    SSTF(ArrayList<Integer> requests, int head) {
        Assignment2.copyArray(this.requests, requests);
        this.head = head;
        output = new StringBuilder();
        output.append("============ S S T F ============\n");
        Run();
        output.append("\nTotal Movement of S S T F = ").append(totalMovement);
        //System.out.println("============ S S T F ============");
        //System.out.println("\nTotal Movement of S S T F = " + Run());
    }

    int getNear() {
        int nearest = 0;
        int distance = Integer.MAX_VALUE;
        for (int i = 0; i < requests.size(); i++) {
            if (Math.abs(requests.get(i) - head) < distance) {
                distance = Math.abs(requests.get(i) - head);
                nearest = i;
            }
        }
        return nearest;
    }

    public void Run() {
        output.append(head);
        //System.out.print(head);
        while (!requests.isEmpty()) {
            int idx = getNear();
            totalMovement += Math.abs(requests.get(idx) - head);
            head = requests.get(idx);
            output.append("  ->  ").append(head);
            //System.out.print("  ->  " + head);
            requests.remove(idx);
        }
    }
}

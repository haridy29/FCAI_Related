import java.util.ArrayList;
import java.util.Random;


public class Router {
    int Maxsize;

    ArrayList<Device> MyDevices;

    semaphore semaphore;

    public Router(int s) {
        Maxsize = s;
        MyDevices = new ArrayList<>();
        for (int i = 0; i < s; i++) {
            MyDevices.add(null);
        }
        semaphore = new semaphore(s);
    }

    public void connect(Device D) {
        semaphore.Wait(D);

        for (int i = 0; i < Maxsize; i++) {
            if (MyDevices.get(i) == null) {
                MyDevices.set(i, D);
                D.setID(i + 1);
                System.out.println("Connection " + D.getID() + ": " + D.getName() + " occupied");
                return;
            }
        }

    }

    public void Disconnect(Device D) {

        int idx = MyDevices.indexOf(D);
        MyDevices.set(idx, null);
        System.out.println("Connection " + D.getID() + ": " + D.getName() + " Logged out");
        semaphore.Signal();
    }

    public void PerformActivity(Device D) {

        System.out.println("connection " + D.getID() + " : " + D.getName() + " Login\n" +
                "connection " + D.getID() + " : " + D.getName() + " Performs Online activity");

    }

}

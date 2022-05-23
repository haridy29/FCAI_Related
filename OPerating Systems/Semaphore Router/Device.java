import java.util.Random;

public class Device implements Runnable {
    private String Name;
    private String Type;
    private int ID;
    private Router Myrouter;

    public Device(String name, String type, Router myrouter) {
        this.Name = name;
        this.Type = type;
        Myrouter = new Router(0);
        this.Myrouter = myrouter;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public void run() {

        Random Rand = new Random();
        int r = Rand.nextInt(8000);

            Myrouter.connect(this);

            Myrouter.PerformActivity(this);

        try {
            Thread.sleep(r);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Myrouter.Disconnect(this);

    }

}

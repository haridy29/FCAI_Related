public class semaphore {


    protected int value = 0;


    protected semaphore(int initial) {
        value = initial;
    }

    public synchronized void Wait(Device D) {

        value--;
        if (value < 0) {
            try {
                System.out.println('(' + D.getName() + ") (" + D.getType() + ") Arrived and Waiting");
                wait();

            } catch (InterruptedException e) {
                //
            }

        }
        System.out.println('(' + D.getName() + ") (" + D.getType() + ") Arrived");


    }

    public synchronized void Signal() {
        value++;
        if (value <= 0) {
            notify();
        }
    }

}

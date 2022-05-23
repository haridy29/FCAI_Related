import javax.swing.*;
import java.util.ArrayList;

public class Network {

    public static void main(String[] args) {

        String input;

        input = JOptionPane.showInputDialog("What is number of WI-FI Connections?");

        if (input==null)
            System.exit(0);

        int MyCap = Integer.parseInt(input);

        Router router = new Router(MyCap);

        input = JOptionPane.showInputDialog("What is number of devices Clients want to connect?");

        if (input==null)
            System.exit(0);

        int Wished = Integer.parseInt(input);


        ArrayList<Thread> devices = new ArrayList<>();

        for (int i = 0; i < Wished; i++) {
            String name, type;

            JTextField Name = new JTextField(10);
            JTextField Type = new JTextField(10);

            JPanel myPanel = new JPanel();

            myPanel.add(new JLabel("Name:"));
            myPanel.add(Name);

            myPanel.add(Box.createHorizontalStrut(20));

            myPanel.add(new JLabel("Type:"));
            myPanel.add(Type);

            JOptionPane.showConfirmDialog(null, myPanel,
                    "Please Enter Name and Type", JOptionPane.OK_CANCEL_OPTION);

            name = Name.getText();
            type = Type.getText();

            devices.add(new Thread(new Device(name, type, router)));
        }


        for (int i = 0; i < Wished; i++) {
            devices.get(i).start();
        }


    }


}

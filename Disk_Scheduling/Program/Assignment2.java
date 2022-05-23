import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

public class Assignment2 extends JFrame {

    int head, end;
    ArrayList<Integer> queue = new ArrayList<>();
    private JList<Integer> jList1;
    private JPanel jPanel6;
    private JTextArea jTextArea1;
    private JTextField jTextField1, jTextField2, jTextField3;
    DefaultListModel listModel;
    ChartPanel chartPanel;
    int startCount = 0;

    public Assignment2() {
        initComponents();
    }

    private void initComponents() {
        Box.Filler filler1, filler2, filler3, filler4, filler5, filler6;
        JButton addButton, clearButton, startButton, clearSelectedButton;
        JLabel jLabel1, jLabel2, jLabel3;
        JScrollPane jScrollPane1,jScrollPane2;
        JPanel jPanel1, jPanel2, jPanel3, jPanel4, jPanel5, jPanel7, jPanel8;
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        clearButton = new JButton();
        jPanel3 = new JPanel();
        filler1 = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, 32767));
        jTextField1 = new JTextField();
        filler3 = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, 32767));
        addButton = new JButton();
        filler2 = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, 32767));
        jPanel2 = new JPanel();
        jLabel2 = new JLabel();
        startButton = new JButton();
        jPanel4 = new JPanel();
        filler4 = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, 32767));
        jTextField2 = new JTextField();
        filler5 = new Box.Filler(new Dimension(10, 0), new Dimension(10, 0), new Dimension(10, 32767));
        jPanel5 = new JPanel();
        jScrollPane1 = new JScrollPane();
        clearSelectedButton = new JButton();
        jPanel6 = new JPanel();
        jPanel7 = new JPanel();
        jScrollPane2 = new JScrollPane();
        jTextArea1 = new JTextArea();
        jPanel8 = new JPanel();
        jLabel3 = new JLabel();
        filler6 = new Box.Filler(new Dimension(20, 0), new Dimension(20, 0), new Dimension(20, 32767));
        jTextField3 = new JTextField();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setLayout(new BorderLayout());

        jLabel1.setText("Cylinders Request");
        jPanel1.add(jLabel1, BorderLayout.LINE_START);

        clearButton.setText("Clear All");
        jPanel1.add(clearButton, BorderLayout.LINE_END);
        clearButton.addActionListener(this::clearButtonActionPerformed);

        jPanel3.setLayout(new BoxLayout(jPanel3, BoxLayout.LINE_AXIS));
        jPanel3.add(filler1);
        jPanel3.add(jTextField1);
        jPanel3.add(filler3);

        addButton.setText("ADD");
        addButton.setMargin(new Insets(12, 14, 12, 14));
        addButton.addActionListener(this::addButtonActionPerformed);
        jPanel3.add(addButton);
        jPanel3.add(filler2);

        jPanel1.add(jPanel3, BorderLayout.CENTER);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 10, 390, 40);
        jPanel1.getAccessibleContext().setAccessibleName("");

        jPanel2.setLayout(new BorderLayout());

        jLabel2.setText("Head Position");
        jPanel2.add(jLabel2, BorderLayout.LINE_START);

        startButton.setText("Start");
        startButton.addActionListener(this::startButtonActionPerformed);
        jPanel2.add(startButton, BorderLayout.LINE_END);

        jPanel4.setLayout(new BoxLayout(jPanel4, BoxLayout.LINE_AXIS));
        jPanel4.add(filler4);


        jPanel4.add(jTextField2);
        jPanel4.add(filler5);

        jPanel2.add(jPanel4, BorderLayout.CENTER);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(10, 58, 390, 40);

        jPanel5.setLayout(null);

        listModel = new DefaultListModel();
        jList1 = new JList(listModel);

        jScrollPane1.setViewportView(jList1);

        jPanel5.add(jScrollPane1);
        jScrollPane1.setBounds(6, 5, 220, 230);

        clearSelectedButton.setText("Clear Selected");
        jPanel5.add(clearSelectedButton);
        clearSelectedButton.setBounds(60, 250, 120, 40);
        clearSelectedButton.addActionListener(this::clearSelectedButtonActionPerformed);

        getContentPane().add(jPanel5);
        jPanel5.setBounds(20, 160, 230, 300);

        jPanel7.setLayout(new BorderLayout());

        jTextArea1.setColumns(20);
        jTextArea1.setRows(7);
        Font f1 = new Font(Font.SERIF,Font.BOLD,16);
        jTextArea1.setFont(f1);
        jScrollPane2.setViewportView(jTextArea1);

        jPanel7.add(jScrollPane2, BorderLayout.CENTER);

        getContentPane().add(jPanel7);
        jPanel7.setBounds(260, 350, 750, 280);


        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.LINE_AXIS));

        jLabel3.setText("End Cylinder");
        jPanel8.add(jLabel3);
        jPanel8.add(filler6);
        jPanel8.add(jTextField3);

        getContentPane().add(jPanel8);
        jPanel8.setBounds(10, 110, 310, 30);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
    }

    private void messageFrame() {
        String message = "Please Enter a Valid Number.";
        String header = "Input Error";
        JFrame frame = new JFrame();
        frame.setSize(240, 125);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.BOTH;
        JLabel headingLabel = new JLabel(header);
        headingLabel.setForeground(Color.RED);
        headingLabel.setOpaque(false);
        frame.add(headingLabel, constraints);
        constraints.gridx++;
        constraints.weightx = 0f;
        constraints.weighty = 0f;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTH;
        JButton closeButton = new JButton(new AbstractAction("x") {
            @Override
            public void actionPerformed(final ActionEvent e) {
                frame.dispose();
            }
        });
        closeButton.setMargin(new Insets(1, 4, 1, 4));
        closeButton.setFocusable(false);
        frame.add(closeButton, constraints);
        constraints.gridx = 0;
        constraints.gridy++;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.BOTH;
        JLabel messageLabel = new JLabel("<HtMl>" + message);
        frame.add(messageLabel, constraints);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void addButtonActionPerformed(ActionEvent evt) {
        int input;
        String text = jTextField1.getText();
        if (isNumeric(text)) {
            input = Integer.parseInt(text);
            queue.add(input);
            if (!listModel.contains(input)) {
                int index = jList1.getSelectedIndex();
                if (index == -1) {
                    index = jList1.getLastVisibleIndex() + 1;
                } else {
                    index++;
                }
                listModel.insertElementAt(input, index);
                jList1.setSelectedIndex(index);
                jList1.ensureIndexIsVisible(index);
            }
        } else {
            messageFrame();
        }
        jTextField1.requestFocusInWindow();
        jTextField1.setText("");
    }

    private void clearButtonActionPerformed(ActionEvent evt) {
        listModel.clear();
        jTextField1.requestFocusInWindow();
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextArea1.setText("");
        jPanel6.setVisible(false);
        chartPanel.setVisible(false);
        queue.clear();
        head = 0;
        end = 0;
    }

    private void clearSelectedButtonActionPerformed(ActionEvent evt) {
        int index = jList1.getSelectedIndex();
        queue.remove(jList1.getSelectedValue());
        listModel.remove(index);
    }

    private void startButtonActionPerformed(ActionEvent evt) {
        jTextArea1.setText("");
        if (startCount != 0) {
            jPanel6.setVisible(false);
            chartPanel.setVisible(false);
        }
        String headText = jTextField2.getText();
        String endText = jTextField3.getText();
        if (isNumeric(headText) && isNumeric(endText)) {
            head = Integer.parseInt(headText);
            end = Integer.parseInt(endText);
            int max = 0;
            for (int i = 0; i < listModel.size(); i++) {
                if (Integer.parseInt(listModel.getElementAt(i).toString()) > max) {
                    max = Integer.parseInt(listModel.getElementAt(i).toString());
                }
            }
            if (head > end || max > end || end < 0 || head < 0 || queue.isEmpty()) {
                head = 0;
                end = 0;
                return;
            }
            FCFS fcfs = new FCFS(queue, head);
            SSTF sstf = new SSTF(queue, head);
            SCAN scan = new SCAN(queue, head);
            C_SCAN c_scan = new C_SCAN(queue, head, end);
            LOOK look = new LOOK(queue, head);
            C_LOOK c_look = new C_LOOK(queue, head);
            Modified modified = new Modified(queue, head);
            jTextArea1.append(fcfs.output.toString() + "\n\n");
            jTextArea1.append(sstf.output.toString() + "\n\n");
            jTextArea1.append(scan.output.toString() + "\n\n");
            jTextArea1.append(c_scan.output.toString() + "\n\n");
            jTextArea1.append(look.output.toString() + "\n\n");
            jTextArea1.append(c_look.output.toString() + "\n\n");
            jTextArea1.append(modified.output.toString() + "\n\n");
            jTextArea1.setCaretPosition(0);
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            dataset.setValue(fcfs.totalMovement, "Total Head Movements", "FCFS");
            dataset.setValue(sstf.totalMovement, "Total Head Movements", "SSTF");
            dataset.setValue(scan.totalMovement, "Total Head Movements", "SCAN");
            dataset.setValue(c_scan.totalMovement, "Total Head Movements", "C_SCAN");
            dataset.setValue(look.totalMovement, "Total Head Movements", "LOOK");
            dataset.setValue(c_look.totalMovement, "Total Head Movements", "C_LOOK");
            dataset.setValue(modified.totalMovement, "Total Head Movements", "Modified");

            JFreeChart chart = ChartFactory.createBarChart(" Disk Scheduling Algorithm", "Algorithms", "Total Head Movements", dataset, PlotOrientation.VERTICAL, false, true, false);

            CategoryPlot p = chart.getCategoryPlot();

            p.setRangeGridlinePaint(Color.black);
            chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(400, 300));
            jPanel6.add(chartPanel);
            jPanel6.setLayout(new BorderLayout());
            getContentPane().add(chartPanel);
            chartPanel.setBounds(420, 10, 600, 300);
            getContentPane().add(jPanel6);
            jPanel6.setBounds(420, 10, 600, 300);
            startCount++;
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
            if (d < 0) {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void copyArray(ArrayList<Integer> a1, ArrayList<Integer> a2) {
        a1.addAll(a2);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Assignment2::new);
    }
}

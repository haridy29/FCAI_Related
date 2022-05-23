package software;

import java.io.*;
import java.util.*;

public class Main {

    //Comparator to sort data in queue
    static class comparator implements Comparator<HuffmanNode> {


        @Override
        public int compare(HuffmanNode o1, HuffmanNode o2) {
            return o1.freq - o2.freq;
        }

    }


    //Function that make the Dictionary and write the data on the Dictionary file
    public static void printCode(HuffmanNode root, String s, FileWriter Dictionary) throws IOException {
        if (root.left == null && root.right == null && root.ch != '/') {

            Dictionary.write(root.ch + ":" + s + '\n');
            return;
        }

        printCode(root.left, s + "0", Dictionary);
        printCode(root.right, s + "1", Dictionary);


    }


    public static void compress(String Data) {

        //Hash map to put the character and its frequency in it
        HashMap<Character, Integer> mp = new HashMap<Character, Integer>();


        //But the char and frequency on the Hash
        for (int i = 0; i < Data.length(); i++) {
            if (mp.containsKey(Data.charAt(i))) {
                mp.put(Data.charAt(i), mp.get(Data.charAt(i)) + 1);
            } else {
                mp.put(Data.charAt(i), 1);
            }
        }

        //priority queue to make the Huffman tree
        PriorityQueue<HuffmanNode> Treequeue = new PriorityQueue<>(mp.size(), new comparator());


        //Convert the Hash map into setentry to extract keys and values from it
        for (Map.Entry m : mp.entrySet()) {


            HuffmanNode node = new HuffmanNode();

            node.ch = (char) m.getKey();

            node.freq = (int) m.getValue();

            Treequeue.add(node);

            //Print the character and its frequency on the consol
            System.out.println(node.ch + ": " + node.freq);

        }


        //root of the tree
        HuffmanNode Root = null;

        //Build the tree
        while (Treequeue.size() > 1) {

            HuffmanNode leftNode = Treequeue.poll(), rightNode = Treequeue.poll();

            HuffmanNode p = new HuffmanNode();

            p.left = leftNode;
            p.right = rightNode;

            p.freq = leftNode.freq + rightNode.freq;

            //means internal node
            p.ch = '/';

            Treequeue.add(p);

            Root = p;
        }

        try {
            //pass file writer into printcode to print the compressed codes on it;
            FileWriter fileWriter = new FileWriter("Dictionary.txt");

            printCode(Root, "", fileWriter);

            fileWriter.close();

            //Write The compressed file
            MakeBinary();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    //Function To make Compressed File that contains all the Data
    public static void MakeBinary() {
        File codes = new File("Dictionary.txt");
        try {

            BufferedReader Br = new BufferedReader(new FileReader(codes));

            String s;
            char ch;
            //Hash table to set the codes that readed from Dictionary on it;
            Hashtable<Character, String> myhash = new Hashtable<>();
            while ((s = Br.readLine()) != null) {
                ch = s.charAt(0);
                s = s.substring(2);
                myhash.put(ch, s);
            }
            Br.close();

            //Make the whole compressed file that contains the whole Binary data
            FileWriter Compressed = new FileWriter("Compressed.txt");
            codes = new File("Data.txt");
            Br = new BufferedReader(new FileReader(codes));

            while ((s = Br.readLine()) != null) {
                for (int i = 0; i < s.length(); i++) {
                    Compressed.write(myhash.get(s.charAt(i)));
                }
                Compressed.write('\n');
            }

            Compressed.close();
            Br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void Decompress() {

        File codes = new File("Dictionary.txt");
        try {

            BufferedReader Br = new BufferedReader(new FileReader(codes));

            String s, str;
            char ch;
            //Hash table to set the codes that readed from Dictionary on it with its characters
            Hashtable<String, Character> myhash = new Hashtable<>();
            while ((s = Br.readLine()) != null) {
                ch = s.charAt(0);
                s = s.substring(2);
                myhash.put(s, ch);
            }
            Br.close();

            codes = new File("Compressed.txt");
            Br = new BufferedReader(new FileReader(codes));

            //make Decompressed text file to put the Decompressed values on it;
            FileWriter DecompressedF = new FileWriter("Decompressed.txt");
            while ((s = Br.readLine()) != null) {
                str = "";
                for (int i = 0; i < s.length(); i++) {
                    str += s.charAt(i);
                    if (myhash.containsKey(str)) {
                        DecompressedF.write(myhash.get(str));
                        str = "";
                    }
                }
                DecompressedF.write('\n');
            }
            Br.close();
            DecompressedF.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        File myfile = new File("Data.txt");
        System.out.println("===========================================================\n");
        System.out.println("            Welcome in Huffman Standard Decoding\n");
        System.out.println("===========================================================\n");

        BufferedReader Datafile = new BufferedReader(new FileReader(myfile));

        String s, Data = "";

        while ((s = Datafile.readLine()) != null) {
            Data += s;
        }



        compress(Data);

        Decompress();


        System.out.println("Original size= " + Data.length() * 8 + " Bits");
        File calculate_length = new File("Compressed.txt");

        System.out.println("Compressed size= " + calculate_length.length() + " Bits");
        System.out.println("===========================================================");

    }
}
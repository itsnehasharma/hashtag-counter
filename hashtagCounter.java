import java.util.*;
import java.io.*;

public class hashtagCounter {

    public static void main(String[] args) throws IOException {

        // set up for reading and writing files

        // set up to read input
        String inputFileName = args[0];
        File inputFile = new File(inputFileName);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));

        // set up for potential output file
        String outputFileName = "";
        BufferedWriter writer = null;

        if (args.length == 1) {
            outputFileName = "printToConsole";
        } else if (args.length == 2) {
            outputFileName = args[1];
            File outputFile = new File(outputFileName);
            writer = new BufferedWriter(new FileWriter(outputFile));
        } else {
            System.out.println("Please specify an input file.");
            System.exit(0);
        }

        Hashtable<String, Node> hashtags = new Hashtable<String, Node>();

        String s = "";

        FibbTree tree = new FibbTree();

        // array to hold the string just read into the word and its frequency
        String split[] = new String[2];
        Node temp;
        int frequency;
        String tag;
        Node max;
        Vector<Node> reinsert = new Vector<Node>();

        s = reader.readLine(); // read first line of file into s
        while (s != null) { // while the file has a next input
            // System.out.println("here");
            // System.out.println(s);

            if (s.charAt(0) == '#') { // hashtag in the list

                String hashtagAndFreq = s.substring(1); // the word starts at char 1
                split = hashtagAndFreq.split(" "); // split the string into word and frequency
                tag = split[0]; // word
                frequency = Integer.parseInt(split[1]); // frequency

                // if the hashmap already contains the hashtag, need to increaseKey
                if (hashtags.containsKey(tag)) {

                    temp = hashtags.get(tag);

                    tree.increaseKey(temp, frequency);
                    // System.out.println("update " + update);
                    // update++;
                } else { // if the hashtag does not exist in the map

                    Node n = new Node(frequency);
                    tree.insert(n);
                    hashtags.put(tag, n);
                    // System.out.println("insert "+ insert);
                    // insert++;

                }
            } else if (s.equalsIgnoreCase("stop")) {

                System.out.println("done");

                reader.close();

                if (args.length == 2) {
                    writer.close();
                }

                // printTable(hashtags);
                System.out.println(tree.toString());

                System.exit(0);

            } else {

                int numWords = Integer.valueOf(s);

                // System.out.println("printing " + numWords + " words");
                // System.out.println(tree.toString());

                for (int i = 0; i < numWords; i++) {
                    max = tree.removeMax();

                    for (String str : hashtags.keySet()) {
                        if (hashtags.get(str).equals(max)) {

                            if (outputFileName.equals("printToConsole")) {
                                System.out.print(str);
                                if (i < numWords - 1) {
                                    System.out.print(",");
                                }
                            } else {
                                writer.append(str);
                                if (i < numWords - 1) {
                                    System.out.print(",");
                                }
                            }
                        }
                    }
                    reinsert.add(max);
                }
                if (outputFileName.equals("printToConsole")) {
                    System.out.println();
                } else {
                    writer.append("\n");
                }
                

                for (Node x : reinsert) {
                    tree.insert(x);
                }
            }

            s = reader.readLine();

        }

    }

    // public static void printTable(Hashtable<String, Node> h){

    // Set<String> ks = h.keySet();
    // for (String key: ks){
    // System.out.println(key + ": " + h.get(key).getData());
    // }

    // }
}

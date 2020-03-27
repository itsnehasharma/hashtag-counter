import java.util.*;
import java.io.*;

public class hashtagCounter {
    

    public static void main(String[] args) throws IOException {

        // set up for reading and writing files
        File inputFile = new File("input.txt");
        File outputFile = new File("output.txt");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        // BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        // BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        // Node n = new Node();

        Hashtable<String, Node> hashtags = new Hashtable<String, Node>();
        Hashtable<String, Integer> hashtagsWithInt = new Hashtable<String, Integer>();
        String s = "";

        FibbTree tree = new FibbTree();

        // array to hold the string just read into the word and its frequency
        String split[] = new String[2];
        int currentValue = 0;
        // int newCount = 0;

        s = reader.readLine(); // read first line of file into s
        while (s != null) { // while the file has a next input

            if (s.charAt(0) == '#') { // hashtag in the list

                String hashtag = s.substring(1); // the word starts at char 1
                split = hashtag.split(" "); // split the string into word and frequency
                String newTag = split[0];
                // System.out.println(newTag);
                int newValue = Integer.parseInt(split[1]);

                // if the hashmap already contains the hashtag, need to increaseKey
                // hashtagsWithInt.containsKey(newTag)
                if (hashtags.containsKey(newTag)) {
                    // System.out.println("duplicate");
                    // perform increaseKey
                    // currentValue = hashtagsWithInt.get(newTag); // get the number of tags existing in map
                    // Node temp = hashtags.get(newTag);
                    // currentValue = temp.getData();
                    // newValue += currentValue;
                    // temp.setData(newValue);
                    // pretend this is increase key
                    // hashtagsWithInt.replace(newTag, newValue);
                    // hashtags.replace(newTag, temp);

                    currentValue = hashtags.get(newTag).getData();
                    hashtags.get(newTag).setData(newValue += currentValue); //needs to change to increase key
                }
                // if the hashtag foes not exist in the map
                else {
                    hashtagsWithInt.put(newTag, newValue);

                    Node n = new Node(newValue);
                    tree.insert(n);
                    hashtags.put(newTag, n);

                }

                writer.append(split[0]); // test to append word to output
            } else if (s.equalsIgnoreCase("stop")) {
                // end the program
                System.out.println("done");
            }
            s = reader.readLine();

            /*
             * output test if (s != null){ // writer.append(","); }
             * 
             */
        }

        System.out.println("HASHMAP WITH INTEGER");
        System.out.println(hashtagsWithInt.toString());

        System.out.println("HASHMAP WITH NODES");
        System.out.println(hashtags.toString());

        System.out.println(tree.toString());

        reader.close();
        writer.close();

        // FibbTree ft = new FibbTree();

        // ft.insert(2);
        // // System.out.println(ft.toString());
        // ft.insert(3);
        // // System.out.println(ft.toString());
        // ft.insert(4);
        // // System.out.println(ft.toString());
        // ft.insert(5);
        // // System.out.println(ft.toString());
        // ft.insert(6);
        // System.out.println(ft.toString());

        // ft.pairwiseCombine();
        // System.out.println(ft.toString());

    }
}

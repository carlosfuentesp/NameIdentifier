package standford;

import edu.stanford.nlp.simple.Sentence;
import util.Csv;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
        Queue<String> unrecognizedQueue = new LinkedList<String>();
        List<List<String>> listOfNames = Csv.readCsv();
        for (List<String> row : listOfNames) {
            String name = row.get(0);
            Sentence sentence = new Sentence(name);
            String result = sentence.nerTags().get(0);
            if (!result.equals("PERSON")) {
                //System.out.println(name + ": No");
                unrecognizedQueue.add(name);
            }
//            else {
//                System.out.println(name + ": Yes");
//            }

        }
        System.out.println("Unrecognized names: " + unrecognizedQueue);
        System.out.println("Total: " + unrecognizedQueue.size());
        System.out.println("Total: " +  listOfNames.size());
    }
}

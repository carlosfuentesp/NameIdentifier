package standford;

import edu.stanford.nlp.simple.Sentence;
import util.Csv;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        for (List<String> names : Csv.readCsv()) {
            String name = names.get(0);
            Sentence sentence = new Sentence(name);
            String result = sentence.nerTags().get(0);
            if (!result.equals("PERSON")) {
                System.out.println(name + ": No");
            } else {
                System.out.println(name + ": Yes");
            }
        }
    }
}

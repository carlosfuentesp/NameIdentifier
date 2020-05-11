import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.Span;

import java.io.FileInputStream;

public class Main {
    public static void main(String[] args) {

        try {
            String word = "William";

            NameFinderME finder = new NameFinderME(new TokenNameFinderModel(new FileInputStream("en-ner-person.bin")));
            Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
            String[] tokens = tokenizer.tokenize(word);
            Span[] names = finder.find(tokens);

            displayResult(finder.probs(names), tokens[0], names);

            finder.clearAdaptiveData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void displayResult(double[] probs, String token, Span[] names) {
        System.out.println("Word: " + token);
        if (names.length != 0) {
            System.out.println("It is a person's first or last name");
            System.out.println("Probability: " + probs[0]);
        } else {
            System.out.println("It is not a person's first or last name");
        }
    }
}

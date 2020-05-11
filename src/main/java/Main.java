import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.Span;

import java.io.FileInputStream;

public class Main {
    public static void main(String[] args) {

        try {
            String word = "Carlos";

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
        if (names.length != 0) {
            System.out.println(token + ": Yes, " + probs[0]);
        } else {
            System.out.println(token + ": No");
        }
    }
}

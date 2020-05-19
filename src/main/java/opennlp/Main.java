package opennlp;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.Span;
import util.Csv;

import java.io.FileInputStream;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        try {
            NameFinderME finder = new NameFinderME(new TokenNameFinderModel(new FileInputStream("src/main/java/opennlp/en-ner-person.bin")));
            Tokenizer tokenizer = SimpleTokenizer.INSTANCE;

            for (List<String> names : Csv.readCsv()) {
                findName(names.get(0), finder, tokenizer);
            }

            finder.clearAdaptiveData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void findName(String word, NameFinderME finder, Tokenizer tokenizer) {
        String[] tokens = tokenizer.tokenize(word);
        Span[] names = finder.find(tokens);
        displayResult(finder.probs(names), tokens[0], names);
    }

    private static void displayResult(double[] probs, String token, Span[] names) {
        if (names.length != 0) {
            System.out.println(token + ": Yes, " + probs[0]);
        } else {
            System.out.println(token + ": No");
        }
    }
}

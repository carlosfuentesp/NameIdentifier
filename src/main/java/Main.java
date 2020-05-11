import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.Span;

import java.io.FileInputStream;

public class Main {
    public static void main(String[] args) {

        try {
            String word = "Mike";
            NameFinderME finder = new NameFinderME(new TokenNameFinderModel(new FileInputStream("en-ner-person.bin")));
            Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
            String[] tokens = tokenizer.tokenize(word);

            Span[] names = finder.find(tokens);
            double[] probs = finder.probs(names);
            displayNames(names, probs, tokens);

            finder.clearAdaptiveData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void displayNames(Span[] names, double[] probs, String[] tokens) {
        for (Span name : names) {
            StringBuilder sb = new StringBuilder();
            for (int ti = name.getStart(); ti < name.getEnd(); ti++) {
                sb.append(tokens[ti]).append(" ");
            }
            System.out.println(sb.substring(0, sb.length() - 1));

            System.out.println("ttype: " + name.getType());

        }

        for (int i = 0; i < probs.length; i++) {
            System.out.println("probs: " + probs[i]);
        }
    }
}

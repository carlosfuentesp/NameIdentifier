import com.opencsv.CSVReader;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.Span;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        try {
            for (List<String> name : readCsv()) {
                findName(name.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void findName(String word) throws IOException {
        NameFinderME finder = new NameFinderME(new TokenNameFinderModel(new FileInputStream("en-ner-person.bin")));
        Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize(word);
        Span[] names = finder.find(tokens);

        displayResult(finder.probs(names), tokens[0], names);

        finder.clearAdaptiveData();
    }

    private static void displayResult(double[] probs, String token, Span[] names) {
        if (names.length != 0) {
            System.out.println(token + ": Yes, " + probs[0]);
        } else {
            System.out.println(token + ": No");
        }
    }

    private static List<List<String>> readCsv() {
        List<List<String>> records = new ArrayList<List<String>>();
        try {
            CSVReader csvReader = new CSVReader(new FileReader("names.csv"));
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
            return records;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}

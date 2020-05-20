package standford;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.sequences.SeqClassifierFlags;
import edu.stanford.nlp.util.StringUtils;

import java.util.Properties;

public class Train {

    public static void main(String[] args) {
        String model = "ner-model.ser.gz";
        //trainAndWrite(model,"properties.txt","labeledData.txt");
        CRFClassifier classifier = getModel(model);
        doTagging(classifier, "oreoluwa");



    }

    private static void doTagging(CRFClassifier model, String input) {
        input = input.trim();
        System.out.println(input + "=>"  +  model.classifyToString(input));
    }

    private static CRFClassifier getModel(String modelPath) {
        return CRFClassifier.getClassifierNoExceptions(modelPath);
    }

    private static void trainAndWrite(String modelOutPath, String prop, String trainingFilePath) {
        Properties props = StringUtils.propFileToProperties(prop);
        props.setProperty("serializeTo", modelOutPath);
        if (trainingFilePath != null) {
            props.setProperty("trainFile", trainingFilePath);
        }
        SeqClassifierFlags flags = new SeqClassifierFlags(props);
        CRFClassifier<CoreLabel> crf = new CRFClassifier<CoreLabel>(flags);
        crf.train();
        crf.serializeClassifier(modelOutPath);
    }
}

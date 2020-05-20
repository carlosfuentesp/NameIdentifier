package lingpipe;

import com.aliasi.chunk.Chunk;
//import com.aliasi.chunk.Chunker;
import com.aliasi.chunk.Chunking;

import com.aliasi.dict.DictionaryEntry;
import com.aliasi.dict.ExactDictionaryChunker;
import com.aliasi.dict.MapDictionary;

import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import java.util.ArrayList;
import java.util.List;
import util.Csv;

// http://www.alias-i.com/lingpipe/index.html

public class Main {
  static final double CHUNK_SCORE = 1.0;

  public static void main(String[] args) {
    List<List<String>> listCandiateNames   = Csv.readCsv("names.csv");

    List<String> firstNames = new ArrayList<String>();
    List<String> lastNames = new ArrayList<String>();
    List<String> nonDefined = new ArrayList<String>();

    System.out.println("Init");

    MapDictionary<String> dictionary = trainedDictionary();
    ExactDictionaryChunker dictionaryChunkerFF = exactDictionaryChunker(dictionary);

    for (List<String> row : listCandiateNames) {
      String text = row.get(0);
      String type = chunk(dictionaryChunkerFF,text);

      if (type.equals("FN")) {
        firstNames.add(text);
      }
      else if (type.equals("LN")) {
        lastNames.add(text);
      }
      else {
        nonDefined.add(text);
      }
    }

    System.out.println("FIRST NAMES");
    System.out.println(firstNames);

    System.out.println("LAST NAMES");
    System.out.println(lastNames);

    System.out.println("NON DEFINED NAMES");
    System.out.println(nonDefined);

  }

  static String chunk(ExactDictionaryChunker chunker, String text) {
    String type = new String();

    Chunking chunking = chunker.chunk(text);
    for (Chunk chunk : chunking.chunkSet()) {
      //int start = chunk.start();
      //int end = chunk.end();
      type = chunk.type();
      //double score = chunk.score();
      //String phrase = text.substring(start,end);
    }
    return type;
  }

  static MapDictionary<String> trainedDictionary() {
    MapDictionary<String> dictionary = new MapDictionary<String>();

    List<List<String>> listOfNames = Csv.readCsv("names2.csv");
    System.out.println("list of names size: " + listOfNames.size());
    for (List<String> row : listOfNames) {
      String firstName = row.get(0).toUpperCase();
      String lastName = row.get(1).toUpperCase();

      dictionary.addEntry(new DictionaryEntry<String>(firstName,"FN",CHUNK_SCORE));
      dictionary.addEntry(new DictionaryEntry<String>(lastName,"LN",CHUNK_SCORE));

    }

    // training model

    //dictionary.addEntry(new DictionaryEntry<String>("Milber","FN",CHUNK_SCORE));
    //dictionary.addEntry(new DictionaryEntry<String>("Champutiz","LN",CHUNK_SCORE));
    return dictionary;

  }

  static  ExactDictionaryChunker exactDictionaryChunker(MapDictionary<String> dictionary) {
    ExactDictionaryChunker dictionaryChunker
        = new ExactDictionaryChunker(dictionary,
        IndoEuropeanTokenizerFactory.INSTANCE,
        false,false);
    return dictionaryChunker;

  }

}

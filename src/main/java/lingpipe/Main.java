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

// http://www.alias-i.com/lingpipe/index.html

public class Main {
  static final double CHUNK_SCORE = 1.0;

  public static void main(String[] args) {
    String myData[] = new String[]{"Milber", "Champutiz", "milber  ", "milbe"};
    List<String> firstNames = new ArrayList<String>();
    List<String> lastNames = new ArrayList<String>();
    List<String> nonDefined = new ArrayList<String>();

    System.out.println("Init");

    MapDictionary<String> dictionary = trainedDictionary();
    ExactDictionaryChunker dictionaryChunkerFF = exactDictionaryChunker(dictionary);

    for (int i = 0; i < myData.length; ++i) {
      String text = myData[i];
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


    System.out.println(firstNames);

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

    // training model

    dictionary.addEntry(new DictionaryEntry<String>("Milber","FN",CHUNK_SCORE));
    dictionary.addEntry(new DictionaryEntry<String>("Champutiz","LN",CHUNK_SCORE));
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

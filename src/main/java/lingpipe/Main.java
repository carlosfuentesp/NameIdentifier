package lingpipe;

import com.aliasi.chunk.Chunk;
//import com.aliasi.chunk.Chunker;
import com.aliasi.chunk.Chunking;

import com.aliasi.dict.DictionaryEntry;
import com.aliasi.dict.ExactDictionaryChunker;
import com.aliasi.dict.MapDictionary;

import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;

// http://www.alias-i.com/lingpipe/index.html

public class Main {
  static final double CHUNK_SCORE = 1.0;

  public static void main(String[] args) {
    String myData[] = new String[]{"Milber", "Champutiz", "milber  ", " cxcv "};

    System.out.println("Init");
    System.out.println(CHUNK_SCORE);

    MapDictionary<String> dictionary = trainedDictionary();
    ExactDictionaryChunker dictionaryChunkerFF = exactDictionaryChunker(dictionary);

    for (int i = 0; i < myData.length; ++i) {
      String text = myData[i];
      String type = chunk(dictionaryChunkerFF,text);
      System.out.println("Value: " + text + "  -- " + type);
    }


    System.out.println("finish");

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

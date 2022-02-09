package com.openfeign.demo.service;

import com.openfeign.demo.adapter.DictionaryAdapter;
import com.openfeign.demo.client.DictionaryClient;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictionaryService {

  private static String SYNONYM = "synonyms";
  private static String ANTONYM = "antonyms";

  @Autowired
  private DictionaryClient openDictionaryClient;

  public List<Map<String, Object>> getAnswer(String word){
    return Optional.ofNullable(openDictionaryClient.getAnswer(word)).orElseThrow();
  }

  public List<String> getSynonymousWord(String word) {
    return DictionaryAdapter.getListAnswersToListWordsByClassificationWord(getAnswer(word), SYNONYM);
  }

  public List<String> getAntonymsWord(String word) {
    return DictionaryAdapter.getListAnswersToListWordsByClassificationWord(getAnswer(word), ANTONYM);
  }
}

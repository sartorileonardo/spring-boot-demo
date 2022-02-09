package com.openfeign.demo.adapter;

import java.util.List;
import java.util.Map;

public class DictionaryAdapter {

  public static List<String> getListAnswersToListWordsByClassificationWord(List<Map<String, Object>> listMap, String classification){
    List<Map<String, Object>> meanings = (List<Map<String, Object>>) listMap.stream()
        .findFirst()
        .get()
        .get("meanings");
    List<Map<String, Object>> definitions = (List<Map<String, Object>>) meanings.stream().findFirst().get().get("definitions");
    List<String> words = (List<String>) definitions.stream().findFirst().get().get(classification);
    return words;
  }

}

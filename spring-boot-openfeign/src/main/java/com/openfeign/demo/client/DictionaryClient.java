package com.openfeign.demo.client;

import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "opendictionary", url = "https://api.dictionaryapi.dev/api/v2/")
public interface DictionaryClient {
  @RequestMapping(method = RequestMethod.GET, value = "entries/en/{word}", produces = "application/json")
  List<Map<String, Object>> getAnswer(@PathVariable("word") String word);
}

package org.example.service;

import org.example.config.TranslateConfig;
import org.example.controller.dto.TranslateDTOIn;
import org.example.controller.dto.TranslateDTOOut;
import org.example.model.TranslateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TranslateService {

    @Autowired
    private TranslateConfig config;

    @Cacheable(value = "text")
    public TranslateDTOOut getTranslate(TranslateDTOIn dtoIn){
        TranslateModel translator = new TranslateModel(config.getKey());
        String text = translator.translate(dtoIn.getText(), dtoIn.getFromLanguage(), dtoIn.getToLanguage());
        return new TranslateDTOOut(text);
    }
}

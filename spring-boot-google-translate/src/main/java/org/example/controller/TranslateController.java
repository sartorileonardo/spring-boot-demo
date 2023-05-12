package org.example.controller;

import org.example.controller.dto.TranslateDTOIn;
import org.example.controller.dto.TranslateDTOOut;
import org.example.service.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("translate")
public class TranslateController {

    @Autowired
    private TranslateService service;

    @PostMapping
    public TranslateDTOOut getTranslate(@RequestBody TranslateDTOIn dtoIn){
        return service.getTranslate(dtoIn);
    }
}

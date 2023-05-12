package org.example.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TranslateDTOIn {
    private String fromLanguage;
    private String toLanguage;
    private String text;
}

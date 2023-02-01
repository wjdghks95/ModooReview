package com.io.rol.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryName {
    FOOD("food"), BEAUTY("beauty"), COSMETIC("cosmetic"), FASHION("fashion"), NURSING("nursing"), DIGITAL("digital"), SPORTS("sports"),
    PET("pet"), RESTAURANT("restaurant"), TRAVEL("travel"), CULTURE("culture"), INTERIOR("interior"), ETC("etc");

    private final String category;
}

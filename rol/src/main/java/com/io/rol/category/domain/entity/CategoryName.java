package com.io.rol.category.domain.entity;

import lombok.Getter;

// 카테고리 이름
@Getter
public enum CategoryName {
    FOOD("food"), BEAUTY("beauty"), COSMETIC("cosmetic"), FASHION("fashion"), NURSING("nursing"), DIGITAL("digital"), SPORTS("sports"),
    PET("pet"), RESTAURANT("restaurant"), TRAVEL("travel"), CULTURE("culture"), INTERIOR("interior"), ETC("etc");

    private String category;

    CategoryName(String category) {
        this.category = category;
    }
}

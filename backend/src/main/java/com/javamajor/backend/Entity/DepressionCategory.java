package com.javamajor.backend.Entity;


public enum DepressionCategory {
    MINIMAL_DEPRESSSION("1-5 index score"),
    MILD_DEPRESSSION("5-10 index score"),
    MODERATE_DEPRESSSION("10-15 index score"),
    MODERATELY_SEVERE_DEPRESSSION("15-20 index score"),
    SEVERE_DEPRESSSION("20-24 index score");

    private final String description;

    // The constructor name should be the same as the enum name (Category)
    DepressionCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

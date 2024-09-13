package com.javamajor.backend.Entity;

public enum Category {
    EARLY_CHILDHOOD("2-6 Years"),
    MIDDLE_CHILDHOOD("7-11 Years"),
    ADOLESCENCE_CHILDHOOD("12-18 Years"),
    YOUNG_ADULTHOOD("19-29 Years"),
    MIDDLE_ADULTHOOD("30-49 Years"),
    OLDER_ADULTHOOD("50+ Years");

    private final String description;

    // The constructor name should be the same as the enum name (Category)
    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

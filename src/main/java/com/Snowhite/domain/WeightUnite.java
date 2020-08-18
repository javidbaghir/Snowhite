package com.Snowhite.domain;

public enum WeightUnite {

    GRAM(1);

    private int value;

    WeightUnite(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static WeightUnite fromValue(int value) {

        WeightUnite weightUnite = null;

        if (value == 1) {
            weightUnite = GRAM;
        }

        throw new RuntimeException("Invalid weight unite value = " + value);
    }
}

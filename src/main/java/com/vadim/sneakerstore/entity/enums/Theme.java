package com.vadim.sneakerstore.entity.enums;

public enum Theme {
    DARK("dark"),
    LIGHT("light");

    private String theme;

    Theme(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
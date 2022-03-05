package com.vadim.sneakerstore.entity.enums;

public enum Currency {
    USD("$"),
    GBP("£"),
    EUR("€"),
    JPY("¥"),
    RUB("₽");

    private final String symbol;

    Currency(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}

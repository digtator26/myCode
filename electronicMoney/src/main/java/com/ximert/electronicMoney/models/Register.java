package com.ximert.electronicMoney.models;

import java.util.Set;

public class Register {

    private Set<SpentElectronicCoin> coins;

    public Register(Set<SpentElectronicCoin> coins) {
        this.coins = coins;
    }

    public Set<SpentElectronicCoin> getCoins() {
        return coins;
    }

}

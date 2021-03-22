package ru.otus.spring.integration.domain;

public class ButterflyEgg {

    private final int serialNumber;

    public ButterflyEgg(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getSerialNumber() {
        return serialNumber;
    }
}

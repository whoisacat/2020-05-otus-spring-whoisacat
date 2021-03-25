package ru.otus.spring.integration.domain;

public class Caterpillar {

    private final int serialNumber;
    private int size;

    public Caterpillar(int serialNumber) {
        this.serialNumber = serialNumber;
        this.size = 1;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

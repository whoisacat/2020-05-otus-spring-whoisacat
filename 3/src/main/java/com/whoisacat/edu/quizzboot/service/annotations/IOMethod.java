package com.whoisacat.edu.quizzboot.service.annotations;

public enum IOMethod{
    IN("Ввод"),
    OUT("Вывод)");

    private final String methodName;

    IOMethod(String method){
        this.methodName = method;
    }

    @Override public String toString(){
        return methodName;
    }
}

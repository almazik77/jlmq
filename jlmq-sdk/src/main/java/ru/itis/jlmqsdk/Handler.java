package ru.itis.jlmqsdk;

public interface Handler<T> {

    void handle(T body) throws Exception;
}

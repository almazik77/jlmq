package ru.itis.jlmqsdk;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;

public class JLMQ {

    public static JlmqConnector connector(String uri) {
        return new JlmqConnector(URI.create(uri));
    }

}

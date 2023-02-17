package br.org.learn.leandro.beans;

import br.org.learn.leandro.config.HeaderProcessorProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Interceptador {

    private final HeaderProcessorProperties properties;


    public String getFirstHeader() {
        return properties.getFirstHeader();
    }

    public String getSecondHeader() {
        return properties.getSecondHeader();
    }

    public String getThirdHeader() {
        return properties.getThirdHeader();
    }

}

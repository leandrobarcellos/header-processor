package br.org.learn.leandro.beans;

import br.org.learn.leandro.config.DefaultHeaderProcessorProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Interceptador {

    private final DefaultHeaderProcessorProperties properties;

    public String getFirstHeader() {
        return properties.getFirst();
    }

    public String getSecondHeader() {
        return properties.getSecond();
    }

    public String getThirdHeader() {
        return properties.getThird();
    }

    public String getFourth() {
        return properties.getFourthHeader();
    }

}

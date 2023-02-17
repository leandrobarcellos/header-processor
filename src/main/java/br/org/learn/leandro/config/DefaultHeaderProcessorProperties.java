package br.org.learn.leandro.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter(AccessLevel.PACKAGE)
@ConfigurationProperties(prefix = "header-processor.headers")
@Component
public class DefaultHeaderProcessorProperties {
    private String first;
    private String second;
    private String third;
    private String fourthHeader;

}

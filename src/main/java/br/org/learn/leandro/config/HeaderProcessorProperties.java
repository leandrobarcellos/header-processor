package br.org.learn.leandro.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter(AccessLevel.PACKAGE)
@Component
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "header-processor.headers")
public class HeaderProcessorProperties {
    @Value("${first:null}")
    private String firstHeader;
    @Value("${second:null}")
    private String secondHeader;
    @Value("${third:null}")
    private String thirdHeader;

}

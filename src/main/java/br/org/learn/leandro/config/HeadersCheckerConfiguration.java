package br.org.learn.leandro.config;

import br.org.learn.leandro.beans.HeadersChecker;
import br.org.learn.leandro.beans.InterceptorHandlers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Slf4j
@RequiredArgsConstructor
@Configuration
public class HeadersCheckerConfiguration {

    private final InterceptorHandlers handlers;
    private final HeadersChecker checker;

    @PostConstruct
    void init() {
        handlers.addPreHandler(checker::preHandle);
    }
}

package br.org.learn.leandro.beans;

import br.org.learn.leandro.config.DefaultHeaderProcessorProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.function.UnaryOperator;


@Slf4j
@RequiredArgsConstructor
@Configuration
public class HeadersChecker {

    private final DefaultHeaderProcessorProperties properties;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UnaryOperator<String> h = request::getHeader;
        String apply = h.apply(properties.getFirst());
        if (apply == null) {
            throw new IllegalArgumentException("Faltou um header ae!");
        }
        log.debug("Primeiro header customizado {}: {}", properties.getFirst(), h.apply(properties.getFirst()));
        log.debug("Segundo header customizado {}: {}", properties.getSecond(), h.apply(properties.getSecond()));
        log.debug("Terceiro header customizado {}: {}", properties.getThird(), h.apply(properties.getThird()));
        log.debug("Quarto header customizado {}: {}", properties.getFourthHeader(), h.apply(properties.getFourthHeader()));
        return true;
    }

}

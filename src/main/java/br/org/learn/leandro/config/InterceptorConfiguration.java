package br.org.learn.leandro.config;

import br.org.learn.leandro.beans.InterceptorHandlers;
import br.org.learn.leandro.beans.Interceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@ComponentScan("br.org.learn.leandro")
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final DefaultHeaderProcessorProperties properties;
    private final InterceptorHandlers processors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor(processors, properties.isEnableTimer()));
    }
}

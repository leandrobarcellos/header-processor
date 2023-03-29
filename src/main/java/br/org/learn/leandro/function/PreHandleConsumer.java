package br.org.learn.leandro.function;

import br.org.learn.leandro.contexts.PreHandleContext;

import java.lang.annotation.Annotation;

@FunctionalInterface
public interface PreHandleConsumer<A extends Annotation>{
    void accept(PreHandleContext<A> context)  throws Exception;
}

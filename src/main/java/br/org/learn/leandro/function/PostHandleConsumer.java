package br.org.learn.leandro.function;

import br.org.learn.leandro.contexts.PostHandleContext;

import java.lang.annotation.Annotation;

@FunctionalInterface
public interface PostHandleConsumer<A extends Annotation>{
    void accept(PostHandleContext<A> context) throws Exception;
}

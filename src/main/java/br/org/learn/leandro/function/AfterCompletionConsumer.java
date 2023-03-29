package br.org.learn.leandro.function;

import br.org.learn.leandro.contexts.AfterCompletionContext;

import java.lang.annotation.Annotation;

@FunctionalInterface
public interface AfterCompletionConsumer<A extends Annotation>{
    void accept(AfterCompletionContext<A> context) throws Exception;
}

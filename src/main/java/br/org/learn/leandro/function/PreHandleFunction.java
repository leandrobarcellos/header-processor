package br.org.learn.leandro.function;

import br.org.learn.leandro.contexts.PreHandleContext;

import java.lang.annotation.Annotation;

@FunctionalInterface
public interface PreHandleFunction<A extends Annotation> {
    boolean apply(PreHandleContext<A> context) throws Exception;
}

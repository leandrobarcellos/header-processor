package br.org.learn.leandro.contexts;

import java.lang.annotation.Annotation;


public interface AfterCompletionContext<A extends Annotation> extends HandlingContext {

    Exception getException();

    A getAnnotation();
}

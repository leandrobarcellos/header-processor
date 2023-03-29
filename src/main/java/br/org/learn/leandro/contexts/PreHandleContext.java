package br.org.learn.leandro.contexts;

import java.lang.annotation.Annotation;


public interface PreHandleContext<A extends Annotation> extends HandlingContext {

    A getAnnotation();
}

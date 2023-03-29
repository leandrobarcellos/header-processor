package br.org.learn.leandro.contexts;

import org.springframework.web.servlet.ModelAndView;

import java.lang.annotation.Annotation;


public interface PostHandleContext<A extends Annotation> extends HandlingContext {

    ModelAndView getModelAndView();

    A getAnnotation();
}

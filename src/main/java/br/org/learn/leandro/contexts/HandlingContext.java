package br.org.learn.leandro.contexts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.time.LocalDateTime;


public interface HandlingContext {
    LocalDateTime startedAt();

    HttpServletRequest getRequest();

    HttpServletResponse getResponse();

    Object getHandler();
}

package br.org.learn.leandro.function;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface AfterCompletionAny {
    void accept(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception;
}

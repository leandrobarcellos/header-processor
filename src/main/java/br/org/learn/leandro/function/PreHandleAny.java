package br.org.learn.leandro.function;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface PreHandleAny {
    boolean apply(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}

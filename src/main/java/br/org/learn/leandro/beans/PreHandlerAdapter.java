package br.org.learn.leandro.beans;

import br.org.learn.leandro.contexts.PreHandleContext;
import br.org.learn.leandro.function.PreHandleAny;
import br.org.learn.leandro.function.PreHandleConsumer;
import br.org.learn.leandro.function.PreHandleFunction;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Objects;

class PreHandlerAdapter<A extends Annotation> {
    private final PreHandleConsumer<A> consumer;
    private final PreHandleFunction<A> function;
    private final PreHandleAny anyRequest;

    public PreHandlerAdapter(
            PreHandleConsumer<A> consumer,
            PreHandleFunction<A> function,
            PreHandleAny anyRequest
    ) {
        this.consumer = consumer;
        this.function = function;
        this.anyRequest = anyRequest;
    }

    public PreHandlerAdapter(PreHandleConsumer<A> consumer) {
        this(consumer, null, null);
    }

    public PreHandlerAdapter(PreHandleFunction<A> function) {
        this(null, function, null);
    }

    public PreHandlerAdapter(PreHandleAny anyRequest) {
        this(null, null, anyRequest);
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flg = true;
        if (Objects.nonNull(anyRequest)) {
            flg = anyRequest.apply(request, response, handler);
        }
        return flg;
    }

    public boolean preHandle(PreHandleContext<A> context) throws Exception {
        boolean flg = true;
        if (Objects.nonNull(consumer)) {
            flg = doConsume(consumer, context);
        } else if (Objects.nonNull(function)) {
            flg = function.apply(context);
        }
        return flg;
    }

    @SneakyThrows
    private boolean doConsume(PreHandleConsumer<A> c, PreHandleContext<A> context) {
        c.accept(context);
        return true;
    }
}

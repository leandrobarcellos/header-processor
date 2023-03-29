package br.org.learn.leandro.beans;

import br.org.learn.leandro.contexts.PreHandleContext;
import br.org.learn.leandro.function.PreHandleAny;
import br.org.learn.leandro.function.PreHandleConsumer;
import br.org.learn.leandro.function.PreHandleFunction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class ManagedPreHandler<A extends Annotation> extends ManagedHandler {
    private final ManagedPreHandler<?> parent;
    private final PreHandlerAdapter<A> preHandlerAdapter;

    public ManagedPreHandler(ManagedPreHandler<?> parent, PreHandlerAdapter<A> preHandlerAdapter) {
        this.parent = parent;
        this.preHandlerAdapter = preHandlerAdapter;
    }

    public ManagedPreHandler(ManagedPreHandler<?> parent, PreHandleAny preHandle) {
        this(parent, new PreHandlerAdapter<>(preHandle));
    }

    public ManagedPreHandler(PreHandleConsumer<A> consumer) {
        this(null, new PreHandlerAdapter<>(consumer));
    }

    public ManagedPreHandler(PreHandleFunction<A> function) {
        this(null, new PreHandlerAdapter<>(function));
    }

    public ManagedPreHandler(PreHandlerAdapter<A> preHandlerAdapter) {
        this(null, preHandlerAdapter);
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;
        if (Objects.nonNull(preHandlerAdapter)) {
            flag = preHandlerAdapter.preHandle(request, response, handler);
            if (flag && Objects.nonNull(parent)) {
                flag = parent.preHandle(request, response, handler);
            }
        }
        return flag;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler, A a) throws Exception {
        boolean flag = true;
        request.setAttribute(STARTED_AT_PARAM, LocalDateTime.now());
        if (Objects.nonNull(a)) {
            flag = preHandlerAdapter.preHandle(new PreContext<>(request, response, handler, a));
        }
        return flag;
    }

    @Getter
    @RequiredArgsConstructor
    static class PreContext<A extends Annotation> implements PreHandleContext<A> {
        private final HttpServletRequest request;
        private final HttpServletResponse response;
        private final Object handler;
        private final A annotation;

        @Override
        public LocalDateTime startedAt() {
            return STARTED_AT.apply(this.request);
        }
    }
}

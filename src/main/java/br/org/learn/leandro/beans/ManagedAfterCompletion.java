package br.org.learn.leandro.beans;

import br.org.learn.leandro.contexts.AfterCompletionContext;
import br.org.learn.leandro.function.AfterCompletionAny;
import br.org.learn.leandro.function.AfterCompletionConsumer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.time.LocalDateTime;
import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ManagedAfterCompletion<A extends Annotation> extends ManagedHandler {
    private final ManagedAfterCompletion<?> parent;
    private final AfterCompletionAny anyRequest;
    private final AfterCompletionConsumer<A> afterCompletionConsumer;

    public ManagedAfterCompletion(ManagedAfterCompletion<?> parent, AfterCompletionAny anyRequest) {
        this(parent, anyRequest, null);
    }

    public ManagedAfterCompletion(AfterCompletionConsumer<A> afterCompletionConsumer) {
        this(null, null, afterCompletionConsumer);
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (Objects.nonNull(anyRequest)) {
            anyRequest.accept(request, response, handler, ex);
            if (Objects.nonNull(parent)) {
                parent.afterCompletion(request, response, handler, ex);
            }
        }
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex, A a) throws Exception {
        if (Objects.nonNull(afterCompletionConsumer)) {
            afterCompletionConsumer.accept(new AfterContext<>(request, response, handler, ex, a));
        }
    }

    @Getter
    @RequiredArgsConstructor
    static class AfterContext<A extends Annotation> implements AfterCompletionContext<A> {
        private final HttpServletRequest request;
        private final HttpServletResponse response;
        private final Object handler;
        private final Exception exception;
        private final A annotation;

        @Override
        public LocalDateTime startedAt() {
            return STARTED_AT.apply(this.request);
        }
    }
}
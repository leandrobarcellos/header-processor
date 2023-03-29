package br.org.learn.leandro.beans;

import br.org.learn.leandro.contexts.PostHandleContext;
import br.org.learn.leandro.function.PostHandleAny;
import br.org.learn.leandro.function.PostHandleConsumer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ManagedPostHandler<A extends Annotation> extends ManagedHandler {
    private final ManagedPostHandler<?> parent;
    private final PostHandleAny postHandleAny;
    private final PostHandleConsumer<A> postHandleConsumer;

    public ManagedPostHandler(ManagedPostHandler<?> parent, PostHandleAny postHandleAny) {
        this(parent, postHandleAny, null);
    }

    public ManagedPostHandler(PostHandleConsumer<A> postHandleConsumer) {
        this(null, null, postHandleConsumer);
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (Objects.nonNull(postHandleAny)) {
            postHandleAny.accept(request, response, handler, modelAndView);
            if (Objects.nonNull(parent)) {
                parent.postHandle(request, response, handler, modelAndView);
            }
        }
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView, A a) throws Exception {
        if (Objects.nonNull(postHandleConsumer)) {
            postHandleConsumer.accept(new PostContext<>(request, response, handler, modelAndView, a));
        }
    }

    @Getter
    @RequiredArgsConstructor
    static class PostContext<A extends Annotation> implements PostHandleContext<A> {
        private final HttpServletRequest request;
        private final HttpServletResponse response;
        private final Object handler;
        private final ModelAndView modelAndView;
        private final A annotation;

        @Override
        public LocalDateTime startedAt() {
            return STARTED_AT.apply(this.request);
        }
    }
}

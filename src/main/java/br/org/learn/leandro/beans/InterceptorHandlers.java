package br.org.learn.leandro.beans;


import br.org.learn.leandro.function.*;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class InterceptorHandlers {
    private static final String HANDLE_ANY_REQUEST = "ANY_REQUEST";
    private final Map<String, ManagedPreHandler<?>> preHandlers;
    private final Map<String, ManagedPostHandler<?>> postHandlers;
    private final Map<String, ManagedAfterCompletion<?>> afterCompletion;

    public InterceptorHandlers() {
        this.preHandlers = new HashMap<>();
        this.preHandlers.put(null, new ManagedPreHandler<>((PreHandlerAdapter<?>) null));
        this.postHandlers = new HashMap<>();
        this.postHandlers.put(null, new ManagedPostHandler<>(null));
        this.afterCompletion = new HashMap<>();
        this.afterCompletion.put(null, new ManagedAfterCompletion<>(null));
    }

    @SuppressWarnings("unchecked")
    <A extends Annotation> ManagedPreHandler<A> getPreHandler(String name) {
        return (ManagedPreHandler<A>) this.preHandlers.get(name);
    }

    @SuppressWarnings("unchecked")
    <A extends Annotation> ManagedPreHandler<A> getPreHandler() {
        return (ManagedPreHandler<A>) this.preHandlers.get(HANDLE_ANY_REQUEST);
    }

    @SuppressWarnings("unchecked")
    <A extends Annotation> ManagedPostHandler<A> getPostHandler(String name) {
        return (ManagedPostHandler<A>) this.postHandlers.get(name);
    }

    @SuppressWarnings("unchecked")
    <A extends Annotation> ManagedPostHandler<A> getPostHandler() {
        return (ManagedPostHandler<A>) this.postHandlers.get(HANDLE_ANY_REQUEST);
    }

    @SuppressWarnings("unchecked")
    <A extends Annotation> ManagedAfterCompletion<A> getAfterCompletion(String name) {
        return (ManagedAfterCompletion<A>) this.afterCompletion.get(name);
    }

    @SuppressWarnings("unchecked")
    <A extends Annotation> ManagedAfterCompletion<A> getAfterCompletion() {
        return (ManagedAfterCompletion<A>) this.afterCompletion.get(HANDLE_ANY_REQUEST);
    }

    public final void addPreHandler(PreHandleAny preHandle) {
        ManagedPreHandler<?> anyRequest = this.preHandlers.get(HANDLE_ANY_REQUEST);
        if (Objects.isNull(anyRequest)) {
            this.preHandlers.put(HANDLE_ANY_REQUEST, new ManagedPreHandler<>(null, preHandle));
        } else {
            this.preHandlers.put(HANDLE_ANY_REQUEST, new ManagedPreHandler<>(anyRequest, preHandle));
        }
    }

    public final <A extends Annotation> void addPreHandler(String name, PreHandleConsumer<A> preHandle) {
        this.preHandlers.put(name, new ManagedPreHandler<>(preHandle));
    }

    public final <A extends Annotation> void addPreHandler(String name, PreHandleFunction<A> preHandle) {
        this.preHandlers.put(name, new ManagedPreHandler<>(preHandle));
    }

    public final <A extends Annotation> void addPostHandler(String name, PostHandleConsumer<A> postHandle) {
        this.postHandlers.put(name, new ManagedPostHandler<>(postHandle));
    }

    public final void addPostHandler(PostHandleAny postHandle) {
        ManagedPostHandler<?> anyRequest = this.postHandlers.get(HANDLE_ANY_REQUEST);
        if (Objects.isNull(anyRequest)) {
            this.postHandlers.put(HANDLE_ANY_REQUEST, new ManagedPostHandler<>(null, postHandle));
        } else {
            this.postHandlers.put(HANDLE_ANY_REQUEST, new ManagedPostHandler<>(anyRequest, postHandle));
        }
    }

    public final <A extends Annotation> void addAfterCompletion(String name, AfterCompletionConsumer<A> afterCompletion) {
        this.afterCompletion.put(name, new ManagedAfterCompletion<>(afterCompletion));
    }

    public final void addAfterCompletion(AfterCompletionAny afterCompletion) {
        ManagedAfterCompletion<?> anyRequest = this.afterCompletion.get(HANDLE_ANY_REQUEST);
        if (Objects.isNull(anyRequest)) {
            this.afterCompletion.put(HANDLE_ANY_REQUEST, new ManagedAfterCompletion<>(null, afterCompletion));
        } else {
            this.afterCompletion.put(HANDLE_ANY_REQUEST, new ManagedAfterCompletion<>(anyRequest, afterCompletion));
        }
    }
}

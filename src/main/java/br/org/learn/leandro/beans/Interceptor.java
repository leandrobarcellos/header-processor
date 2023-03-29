package br.org.learn.leandro.beans;

import br.org.learn.leandro.InterceptWhen;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class Interceptor implements HandlerInterceptor {

    private final InterceptorHandlers processors;
    private final boolean timer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;
        ManagedPreHandler<?> anyRequest = processors.getPreHandler();
        if (Objects.nonNull(anyRequest)) {
            flag = anyRequest.preHandle(request, response, handler);
        }
        if (flag && handler instanceof HandlerMethod checkedHandler) {
            Annotation annotation = extractAnnotation(checkedHandler);
            String handlerName = extractHandlerName(annotation);
            ManagedPreHandler<Annotation> preHandler = processors.getPreHandler(handlerName);
            flag = preHandler.preHandle(request, response, handler, annotation);
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        ManagedPostHandler<?> anyRequest = processors.getPostHandler();
        if (Objects.nonNull(anyRequest)) {
            anyRequest.postHandle(request, response, handler, modelAndView);
        }
        if (handler instanceof HandlerMethod checkedHandler) {
            Annotation a = extractAnnotation(checkedHandler);
            String handlerName = extractHandlerName(a);
            Optional.ofNullable(processors.getPostHandler(handlerName))
                    .orElse(processors.getPostHandler(null))
                    .postHandle(request, response, handler, modelAndView, a);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ManagedAfterCompletion<?> anyRequest = processors.getAfterCompletion();
        if (Objects.nonNull(anyRequest)) {
            anyRequest.afterCompletion(request, response, handler, ex);
        }
        if (handler instanceof HandlerMethod checkedHandler) {
            Annotation a = extractAnnotation(checkedHandler);
            String handlerName = extractHandlerName(a);
            Optional.ofNullable(processors.getAfterCompletion(handlerName))
                    .orElse(processors.getAfterCompletion(null))
                    .afterCompletion(request, response, handler, ex, a);
        }
        executionTimer(request, handler);
    }

    private String extractHandlerName(Annotation annotation) {
        return Optional.ofNullable(annotation).map(Annotation::annotationType)
                .map(at -> at.getAnnotation(InterceptWhen.class))
                .map(InterceptWhen::value)
                .orElse(null);
    }

    protected Annotation extractAnnotation(HandlerMethod handler) {
        return Optional.of(handler)
                .map(HandlerMethod.class::cast)
                .map(HandlerMethod::getMethod)
                .map(AccessibleObject::getAnnotations)
                .flatMap(as -> Arrays.stream(as).filter(a -> a.annotationType().isAnnotationPresent(InterceptWhen.class)).findFirst())
                .orElse(null);
    }

    private void executionTimer(HttpServletRequest request, Object handler) {
        if (timer && handler instanceof HandlerMethod handlerMethod) {
            String msg = "";
            LocalDateTime startedAt = ManagedHandler.STARTED_AT.apply(request);
            long between = ChronoUnit.MILLIS.between(startedAt, LocalDateTime.now());
            List<String> paramsNames = Arrays.stream(handlerMethod.getMethodParameters())
                    .map(MethodParameter::getParameter).map(Parameter::getType).map(Class::getSimpleName)
                    .toList();
            msg += "[" + handlerMethod.getBeanType().getSimpleName() + ":";
            msg += handlerMethod.getMethod().getName() + "(";
            msg += String.join(", ", paramsNames) + ")] Executado em {} millis";
            log.info(msg, between);
        }
    }
}

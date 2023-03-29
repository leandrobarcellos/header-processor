package br.org.learn.leandro.beans;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.function.Function;

public class ManagedHandler {
    protected static final String STARTED_AT_PARAM = "m_-.-_m:startedAt";
    public static final Function<HttpServletRequest, LocalDateTime> STARTED_AT =
            req -> (LocalDateTime) req.getAttribute(STARTED_AT_PARAM);

    ManagedHandler() {
    }
}

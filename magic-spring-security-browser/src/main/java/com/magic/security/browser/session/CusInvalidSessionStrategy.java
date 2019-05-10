package com.magic.security.browser.session;

import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义session失效策略.
 */
public class CusInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

    public CusInvalidSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        onSessionInvalid(request, response);
    }
}

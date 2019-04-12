package com.magic.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TimeFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(TimeFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        filterChain.doFilter(request, response);
        logger.info(((HttpServletRequest) request).getRequestURL()
                + "->TimeFilter 耗时：" + (System.currentTimeMillis() - startTime) + "millis");
    }

    @Override
    public void destroy() {

    }
}

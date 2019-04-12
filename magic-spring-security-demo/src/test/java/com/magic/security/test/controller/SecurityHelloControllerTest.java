package com.magic.security.test.controller;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class SecurityHelloControllerTest extends BaseControllerTest {

    @Test
    public void testSayHello() throws Exception {
        ResultActions action = requestAction(get("/hello"));
        MockHttpServletResponse response = resultInfo(action);
        System.out.println(response.getContentAsString());
    }

}

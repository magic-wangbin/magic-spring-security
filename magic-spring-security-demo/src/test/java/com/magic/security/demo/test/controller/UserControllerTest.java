package com.magic.security.demo.test.controller;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends BaseControllerTest {

    @Test
    public void userListTest() throws Exception {
        ResultActions resultActions = requestAction(
                get("/user")
                        .param("userName", "zhangsan")
                        .param("age", "10")
                        .param("ageTo", "21")
        );
        MockHttpServletResponse response = resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andReturn()
                .getResponse();
        System.out.println(response.getContentAsString());
    }

    @Test
    public void getById() throws Exception {
        ResultActions resultActions = requestAction(get("/user/100"));
        System.out.println(resultInfo(resultActions).getContentAsString());
    }

    @Test
    public void validateUserId() throws Exception {
        ResultActions resultActions = requestAction(get("/user/validate/10").param("id","12"));
        System.out.println(resultInfo(resultActions).getContentAsString());
    }

    @Test
    public void getByIdFailed() throws Exception {
        ResultActions resultActions = requestAction(get("/user/xxx"));
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    public void createUser() throws Exception {

        String content = "{\"userId\":\"10\",\"userName\":null,\"password\":\"123345\",\"birthday\":" + new Date().getTime() + "}";

        ResultActions resultActions = requestAction(post("/user").content(content));
        String result = resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(100))
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println(result);
    }

    @Test
    public void upateUser() throws Exception {
        //时间为毫秒
        Date date = new Date(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(date);
        String content = "{\"userId\":\"100\",\"userName\":\"tom\",\"birthday\":" + date.getTime() + "}";
        ResultActions resultActions = requestAction(put("/user/100").content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("tom"));
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void deleteUser() throws Exception{
        ResultActions resultActions = requestAction(delete("/user/100"));
        resultActions.andExpect(status().isOk());
    }
}

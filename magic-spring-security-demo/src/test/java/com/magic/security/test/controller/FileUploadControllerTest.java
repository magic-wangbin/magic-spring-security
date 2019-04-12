package com.magic.security.test.controller;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FileUploadControllerTest extends BaseControllerTest {
    @Test
    public void uploadFileTest() throws Exception {
        //
        String response = mockMvc.perform(multipart("/file")
                .file(new MockMultipartFile(
                        "multipartFile",
                        "tmp.txt",
                        "multipart/form-data",
                        "hello upload".getBytes("UTF-8")
                ))).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(response);
    }

    @Test
    public void uploadFileTest2() throws Exception {
        String path = "C:\\Users\\Administrator\\Desktop\\temp.txt";
        InputStream inputStream = new FileInputStream(new File(path));
        String response = mockMvc.perform(
                multipart("/file")
                        .file(new MockMultipartFile(
                                "multipartFile", null,
                                "text/pain", inputStream
                        ))
        ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(response);
    }
}

package com.bfchengnuo.security;

import com.bfchengnuo.secunity.demo.SecurityDemoApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Created by 冰封承諾Andy on 2019/7/6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecurityDemoApplication.class)
public class HelloTest {
    @Autowired
    private WebApplicationContext ac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ac).build();
    }

    @Test
    public void testQueryUser() throws Exception {
        String content = mockMvc.perform(
                MockMvcRequestBuilders.get("/user")
                        .param("userName", "skye")
                        .param("page", "2")
                        .param("size", "20")
                        .param("sort", "name,desc")
                        .param("sort", "age,asc")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 将结果进行 json 解析，如果是集合并且长度为 3 则通过
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();

        System.out.println(content);
    }

    @Test
    public void testCreate() throws Exception {
        String userJson = "{\"id\": \"3\", \"userName\":\"skye\",\"pwd\":\"password\"}";
        String content = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3))
                .andReturn().getResponse().getContentAsString();

        System.out.println(content);
    }
}

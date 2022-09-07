package com.hawng.hawng.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawng.hawng.Api.KaKaoController;
import com.hawng.hawng.Api.KaKaoService;
import com.hawng.hawng.domain.Post;
import com.hawng.hawng.domain.PostCategory;
import com.hawng.hawng.request.CommentCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class KaKaoControllerTest {


    @Autowired
    MockMvc mockMvc;
    @Autowired
    KaKaoService kaKaoService;

    @Autowired
    KaKaoController kaKaoController;
    @Autowired
    ObjectMapper objectMapper;
//w3hdLMYh5pB_CKkzdCDnkmvUT08sIuwbpGTOUdugJA7ZnjkJ7XDPxBskbv0QvT9GHXQdoAopcFAAAAGDGGbPLA
    @Test
    @DisplayName("카카오 aip")
    public void test() throws Exception {

        String kakaoAccessToken = kaKaoService.getKakaoAccessToken("w3hdLMYh5pB_CKkzdCDnkmvUT08sIuwbpGTOUdugJA7ZnjkJ7XDPxBskbv0QvT9GHXQdoAopcFAAAAGDGGbPLA");
        kaKaoService.createKakaoUser(kakaoAccessToken);


//
//        mockMvc.perform(MockMvcRequestBuilders.get("/kakao")
//                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString("임시")))
//                .andExpect(status().isOk())
//                .andDo(print());






    }
}

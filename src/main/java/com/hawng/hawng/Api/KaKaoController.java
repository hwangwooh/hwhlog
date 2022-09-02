package com.hawng.hawng.Api;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class KaKaoController {

    @Autowired
    private final KaKaoService kaKaoService;

//    @ResponseBody
//    @GetMapping("/kakao")
//    public void kakaoCallback(@RequestParam String code) {
//        System.out.println("kakaoCallback =  " +code);
//        String kakaoAccessToken = kaKaoService.getKakaoAccessToken(code);
//        System.out.printf("kakaoAccessToken   =  " + kakaoAccessToken);
//        kaKaoService.createKakaoUser(kakaoAccessToken);
//    }

    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) {
        System.out.println("kakaoCallback =  " +code);
    }
}

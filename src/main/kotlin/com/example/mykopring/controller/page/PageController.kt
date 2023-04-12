package com.example.mykopring.controller.page

import com.example.mykopring.model.http.UserRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

// 그냥 컨트롤러 어노테이션을 사용하면 static 디렉토리 하위에 있는 main.html을 찾게된다
@Controller
class PageController {

    @GetMapping("/main")
    fun main(): String {
        println("init main")
        return "main.html"
    }

    // static 페이지를 보여줘야 하는 @Controller에서 응답을 JSON으로 보내고 싶다면
    // 하단과 같이 @ResponseBody를 붙여준다
    @ResponseBody
    @GetMapping("/test")
    fun response(): UserRequest {
        return UserRequest().apply {
            this.name = "steve"
            this.age = 12
            this.email = "yyy@abc"
        }
    }
}

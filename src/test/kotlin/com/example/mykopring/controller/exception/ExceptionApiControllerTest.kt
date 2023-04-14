package com.example.mykopring.controller.exception

import com.example.mykopring.model.http.UserRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.util.LinkedMultiValueMap

@WebMvcTest
@AutoConfigureMockMvc
class ExceptionApiControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun helloTest() {
        mockMvc.perform(
            get("/api/exception/hello"),
        ).andExpect(
            status().isOk,
        ).andExpect(
            content().string("hello"),
        ).andDo(print())
    }

    @Test
    fun getTest() {
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "steve")
        queryParams.add("age", "5")

        mockMvc.perform(
            get("/api/exception").queryParams(queryParams),
        ).andExpect(
            status().isBadRequest,
        ).andExpect(
            content().contentType("application/json"),
        ).andExpect(
            jsonPath("result_code").value("Fail"),
        ).andDo(
            print(),
        )
    }

    @Test
    fun postTest() {
        val userRequest = UserRequest().apply {
            this.name = "steve"
            this.age = 11
            this.phoneNumber = "010-1111-2222"
        }
        // userRequest를 json 형태로 바꿔줘야함 . 테스트 코드 시에 accept를 application/json 형태로 받기 때문
        // 하단에서 object를 json 형태로 바꿔준다.
        val json = jacksonObjectMapper().writeValueAsString(userRequest)

        mockMvc.perform(
            post("/api/exception")
                .content(json)
                .contentType("application/json")
                .accept("application/json"),
        )
    }
}

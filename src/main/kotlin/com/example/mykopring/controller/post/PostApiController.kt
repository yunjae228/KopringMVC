package com.example.mykopring.controller.post

import com.example.mykopring.model.http.Result
import com.example.mykopring.model.http.UserRequest
import com.example.mykopring.model.http.UserResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class PostApiController {

    @PostMapping("/post-mapping")
    fun postMapping(@RequestBody userRequest: UserRequest): UserRequest {
        return userRequest
    }

    @PostMapping("/object-mapping")
    fun postMappingObjectMapper(@RequestBody userRequest: UserRequest): UserResponse {
        // 복잡한 Response가 필요할 때 사용해보자
        return UserResponse().apply {
            this.result = Result().apply {
                this.resultCode = "OK"
                this.resultMessage = "Success"
            }
        }.apply {
            this.description = "~~~~~"
        }.apply {
            val userList = mutableListOf<UserRequest>()
            userList.add(userRequest)
            userList.add(
                UserRequest().apply {
                    this.name = "a"
                    this.age = 10
                    this.email = "~~@~~"
                },
            )
            this.userRequest = userList
        }
    }
}

package com.example.mykopring.controller.get

import com.example.mykopring.model.http.UserRequest
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import kotlin.math.min

@RestController
@RequestMapping("/api")
@Validated
class GetApiController {

    @GetMapping("/hello")
    fun hello(): String {
        return "hello Kotlin"
    }

    @GetMapping("/get-mapping/path-variable")
    fun pathVariable(
        @Size(min = 2, max = 6)
        @RequestParam(value = "name") _name: String,

        @NotNull(message = "age값 누락")
        @Min(value = 20, message = "20보다 커야함")
        @RequestParam
        age: Int,
    ): String {
        val name: String = "kotlin"

        println("$name, $age, $_name")
        return name + age + _name
    }

    @GetMapping("/get-mapping/query-param")
    fun queryParam(@RequestParam name: String, @RequestParam age: Int): String {
        println("$name, $age")

        return name + age
    }

    @GetMapping("/get-mapping/query-paramObject")
    fun queryParmObject(userRequest: UserRequest): UserRequest {
        println("user name : ${userRequest.name}, user age : ${userRequest.age}")

        return userRequest
    }

    @GetMapping("/get-mapping/query-paramMap")
    fun queryParmMap(@RequestParam userRequest: Map<String, Any>): Map<String, Any> {
        println("user name : ${userRequest["name"]}, user age : ${userRequest["age"]}")

        return userRequest
    }
}

package com.example.mvc.controller.put

import com.example.mykopring.model.http.UserRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class PutApiController {

    @PutMapping("/put-mapping")
    fun putMapping(): String {
        return "put-mapping"
    }

    @RequestMapping(method = [RequestMethod.PUT], path = ["/request-mapping"])
    fun requestMapping(): String {
        return "request-mapping - put method"
    }

    @PutMapping(path = ["/put-mapping/object"])
    fun putMappingObject(
        // Valid 어노테이션이 UserRequest data class에 있는 validation을 진행한다
        @Valid @RequestBody
        userRequest: UserRequest,
        // 유효성 검사가 끝난 결과를 bindingResult에 저장한다.
        // 함수에서 불러와 쓸 수 있다.
        bindingResult: BindingResult,
    ): ResponseEntity<String> {
        if (bindingResult.hasErrors()) {
            // 500 error
            val msg = StringBuilder()
            bindingResult.allErrors.forEach {
                /**
                 * 코틀린에서 as는 타입스크립트에서의 타입 단언인 as와는 조금 다르다
                 * 타입에 대해서 동작하는 건 맞지만 아래 같은 경우에는 it을 as뒤에 오는
                 * 지정한 타입으로 캐스트(변환)하게 된다.
                 * 만약, 해당 타입으로 바꿀 수없다면, ClassCatException이 발생된다.
                 *
                 * 이럴 경우 is로 타입을 체크하고, 타입을 바꿀 수 있는 지 검사해야 하지만,
                 * 코틀린에서는 간결하게 as? 연산자를 제공하게된다.
                 * as? 연산자는 대상 타입으로 캐스트 할 수 없으면 null을 반환하게 된다.
                 * */
                val field = it as FieldError
                val message = it.defaultMessage
                msg.append(field.field + " : " + message + "\n")
            }
            return ResponseEntity.badRequest().body(msg.toString())
        }

        return ResponseEntity.ok("")
        // 0. Response
        /* return UserResponse().apply {
             // 1. result
             this.result = Result().apply {
                 this.resultCode = "OK"
                 this.resultMessage = "성공"
             }
         }.apply {
             // 2. description
             this.description = "~~~~~~~~~"
         }.apply {
             // 3. user mutable list
             val userList = mutableListOf<UserRequest>()
             userList.add(userRequest)
             userList.add(UserRequest().apply {
                 this.name = "a"
                 this.age = 10
                 this.email = "a@gmail.com"
                 this.address = "a address"
                 this.phoneNumber = "010-1111-aaaa"
             })
             userList.add(UserRequest().apply {
                 this.name = "b"
                 this.age = 20
                 this.email = "b@gmail.com"
                 this.address = "b address"
                 this.phoneNumber = "010-1111-bbbb"
             })
             this.userRequest = userList
         }*/
    }
}

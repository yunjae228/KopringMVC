package com.example.mykopring.advice

import com.example.mykopring.controller.exception.ExceptionApiController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

// 해당 어노테이션을 붙이게 되면 , RestController에서 발생하는 Exception들이 현재 클래스를 지나치게 된다
@RestControllerAdvice(basePackageClasses = [ExceptionApiController::class])
class GlobalControllerAdvice {
    // value에다가 어떤 Exception 잡을지 명시 가능 , 현재는 Runtime Exception을 모두 잡겠다는 뜻
    @ExceptionHandler(value = [RuntimeException::class])
    fun exception(e: RuntimeException): String {
        return "$e"
    }

    @ExceptionHandler(value = [IndexOutOfBoundsException::class])
    fun indexOutOfBoundsException(): ResponseEntity<String> {
        return ResponseEntity.internalServerError().body("Index Error")
    }
}

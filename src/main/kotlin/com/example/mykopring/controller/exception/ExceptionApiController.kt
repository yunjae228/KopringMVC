package com.example.mykopring.controller.exception

import com.example.mykopring.model.http.Error
import com.example.mykopring.model.http.ErrorResponse
import com.example.mykopring.model.http.UserRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@RestController
@RequestMapping("/api/exception")
@Validated
class ExceptionApiController {

    @GetMapping("")
    fun get(
        @NotBlank
        @Size(min = 2, max = 6)
        @RequestParam
        name: String,

        @Min(10)
        @RequestParam
        age: Int,
    ): String {
        return "$name $age"
    }

    @PostMapping("")
    fun post(
        @Valid @RequestBody
        userRequest: UserRequest,
    ) {
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun methodArgumentNotValidException(
        e: MethodArgumentNotValidException,
        request: HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        val errors: MutableList<Error> = mutableListOf<Error>()

        e.bindingResult.allErrors.forEach { errorObject ->
            val error = Error().apply {
                this.field = (errorObject as FieldError).field
                this.message = errorObject.defaultMessage
                this.value = errorObject.rejectedValue
            }

            errors.add(error)
        }

        val errorResponse = ErrorResponse().apply {
            this.resultCode = "Fail"
            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            this.httpMethod = request.method.toString()
            this.message = "요청 에러"
            this.path = request.requestURI.toString()
            this.timestamp = LocalDateTime.now()
            this.errors = errors
        }

        return ResponseEntity.badRequest().body(errorResponse)
    }

    // 이 클래스에서 ConstraintViolationException이 발생하면 여기로 들어오게 된다
    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun constraintViolationException(
        e: ConstraintViolationException,
        request: HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        val errors: MutableList<Error> = mutableListOf<Error>()

        e.constraintViolations.forEach {
            val error = Error().apply {
                // propertyPath의 마지막이 Error 정보들임
                this.field = it.propertyPath.last().name
                this.message = it.message
                this.value = it.invalidValue
            }
            errors.add(error)
        }

        val errorResponse = ErrorResponse().apply {
            this.resultCode = "Fail"
            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            this.httpMethod = request.method.toString()
            this.message = "요청 에러"
            this.path = request.requestURI.toString()
            this.timestamp = LocalDateTime.now()
            this.errors = errors
        }

        return ResponseEntity.badRequest().body(errorResponse)
    }

    @GetMapping("/hello")
    fun hello() {
        if (true) {
            throw RuntimeException("강제 exception")
        }
    }

    // 클래스 내부에서 예외 처리를 해줘도 되고, 만약 전역으로 관리할 예외 처리가 필요하다면
    // advice/exception 클래스를 따로 만드는 게 편하다
}

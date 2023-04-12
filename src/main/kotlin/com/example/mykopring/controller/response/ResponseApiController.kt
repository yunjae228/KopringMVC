package com.example.mykopring.controller.response

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/response")
class ResponseApiController {

    @GetMapping("")
    fun getMapping(@RequestParam age: Int?): ResponseEntity<String> {
        return age?.let {
            if (it < 20) {
                return ResponseEntity.badRequest().body("$age 값은 20보다 커야 합니다")
            }

            ResponseEntity.ok("OK")
        } ?: kotlin.run {
            return ResponseEntity.badRequest().body("age 값이 누락 되었습니다.")
        }
    }
}

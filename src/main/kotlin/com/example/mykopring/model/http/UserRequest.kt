package com.example.mykopring.model.http

import com.example.mykopring.annotation.StringFormatDateTime
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.constraints.AssertTrue
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

@JsonNaming(PropertyNamingStrategy::class)
data class UserRequest(
    @field:NotNull
    @field:Size(min = 2, max = 8)
    var name: String? = null,

    @field:Positive
    var age: Int? = null,
    var email: String? = null,

    // 정규식 검증, 각 휴대폰 번호 자릿수의 조건을 의미함
    @field:Pattern(regexp = "^\\d{2,3}=\\d{3,4}-\\d{4}\$")
    var phoneNumber: String? = null,

    // 날짜 유효성 검증, 지정된 형식에 맞게 들어오게 validation
    // 요런식으로 까다로운 유효성 검증이 필요할 때, 따로 함수를 만들어 검증이 가능하다.
    // Spring에서 제공하는 Validation 외에 필요한 유효성 검사가 있을때 아래와 같이 사용하면 될 것 같다.

    @field: StringFormatDateTime(pattern = "yyyy-MM-dd HH:mm:ss", message = "패턴이 올바르지 않습니다")
    var createdAt: String? = null,
) {

    // 그러나 만약 모든 유효성 검사가 까다로울때 마다, 이렇게 data class에 함수를 만들어 내는 것은 좋지 않다고 한다.
    // 이럴 때를 대비해 커스터 마이징한 어노테이션을 활용할 수도 있다고 한다.

    // 검증하는 과정에서 , 아래의 메소드가 실행될 수 있도록 @AssertTrue 어노테이션을 붙여준다.
    // validation이 일어날때 @AssertTrue를 참고해서 해당 메소드가 실행되게 된다
    //    @AssertTrue(message = "생성일자의 패턴은 yyyy-MM-dd HH:mm:ss 여야 합니다")
    //    private fun isValidCreatedAt(): Boolean {
    //        return try {
    //            LocalDateTime.parse(this.createdAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    //            true
    //        } catch (e: Exception) {
    //            false
    //        }
    //    }

    // 그러나 만약 모든 유효성 검사가 까다로울때 마다, 이렇게 data class에 함수를 만들어 내는 것은 좋지 않다고 한다.
    // 이럴 때를 대비해 커스터 마이징한 어노테이션을 활용할 수도 있다고 한다.
}

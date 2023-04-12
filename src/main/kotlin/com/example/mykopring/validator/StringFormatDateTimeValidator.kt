package com.example.mykopring.validator

import com.example.mykopring.annotation.StringFormatDateTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class StringFormatDateTimeValidator : ConstraintValidator<StringFormatDateTime, String> {

    private var pattern: String? = null

    // 우리가 지정한 validation pattern을 적용 시키기 위해
    // StringFormatDateTime에 지정되어 있는 pattern을 가져와야 한다.
    // initialize라는 fun을 오버라이딩 해, 타입으로 우리가 만든 StringFormatDateTime 어노테이션 클래스를 가져온다.
    // 그리고 initialize 오버라이드 함수에서 pattern을 초기화 시켜준다. (가져온 어노테이션 클래스로.)
    override fun initialize(constraintAnnotation: StringFormatDateTime?) {
        // 만든 pattern을 nullabe로 지정했기 때문에 엘비스 연산자로 nullable한 값입을 알려준다.
        // 커스텀 어노테이션에 지정된 패턴을 현재 클래스의 패턴에다가 지정해준다.
        this.pattern = constraintAnnotation?.pattern
        super.initialize(constraintAnnotation)
    }

    // ConstraintValidator라는 추상 클래스를 상속 받았음
    // 추상 클래스에선 반드시 생성해야할 메소드가 있기 때문에 , isValid라는 메소드를 오버라이딩 시켜준다.
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        return try {
            LocalDateTime.parse(value, DateTimeFormatter.ofPattern(pattern))
            true
        } catch (e: Exception) {
            false
        }
    }
}

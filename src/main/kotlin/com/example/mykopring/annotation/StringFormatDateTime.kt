package com.example.mykopring.annotation

import com.example.mykopring.validator.StringFormatDateTimeValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

// 1. 어노테이션으로 동작하기 위해 @Target를 붙인다.
// 2. 매개변수로는 field, getter, setter에 지정해준다
@Constraint(validatedBy = [StringFormatDateTimeValidator::class]) // 어떠한 validate를 사용하겠음을 명시
@Target(
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
)
// 런타임에만 실행될 수 있게 설정
// 또한 이 어노테이션이 붙었을 때 검증해줄 함수를 만들어야 한다
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class StringFormatDateTime(
    val pattern: String = "yyyy-MM-dd HH:mm:ss",
    val message: String = "시간 형식이 유효하지 않습니다",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)
// StringFormatDateTime 어노테이션이 붙은 변수에 대해서는 FIELD, GETTER, SETTER에 대해서
// StringFormatDateTimeValidator를 통해 검증을 하겠다는 뜻

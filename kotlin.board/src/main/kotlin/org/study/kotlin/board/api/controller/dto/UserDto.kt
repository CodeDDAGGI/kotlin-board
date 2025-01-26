package org.study.kotlin.board.api.controller.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.study.kotlin.board.global.annotaion.Password
import java.time.LocalDateTime

// 껍데기
object UserDto {
    data class SignupRequest(
        @field:Email
        @field:NotBlank
        val email: String,
        @field:Password // 커스텀으로 만들꺼
        val password: String,
        @field:NotBlank
        val name: String,
    ){
        fun toCommend() =  // 표현식 형태로 작성하는게 좋음
            UserCreateCommand(
                email = email,
                password = password,
                name = name
            )
    }

    data class Response(
        val id: Long,
        val email: String,
        val name : String,
        val createdAt : LocalDateTime,
        val updateAt : LocalDateTime?,
        // 컨트롤 + 알트 + o를 하면 자동 임포트
    )

    data class LoginRequest(
        @field:Email
        val email: String,
        @field:NotBlank // 커스텀으로 만들꺼
        val password: String,
    )

    data class LoginResponse(
        val token : String,
    )

    data class UpdateRequest(
        @field:Email
        val email : String,
        @field:NotBlank
        val name : String,
        val password : String? = null
    ){
        fun toCommand() =
            UserUpdateCommand(
                email = email,
                name = name ,
                password = password
            )
    }
}


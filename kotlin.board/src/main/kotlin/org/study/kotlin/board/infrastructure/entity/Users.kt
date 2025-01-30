package org.study.kotlin.board.infrastructure.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.crypto.password.PasswordEncoder
import org.study.kotlin.board.api.controller.dto.UserCreateCommand
import org.study.kotlin.board.api.controller.dto.UserDto
import org.study.kotlin.board.application.service.UsersService
import org.study.kotlin.board.global.config.entity.BaseEntity

@Entity
@Table(name = "users")
class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 전략적으로 어떻게 가져가는건가
    val id: Long? = null,
    @Column(nullable = false) // nullable false를 줘서 무조건 값이 있어야댐
    var email: String,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    @JsonIgnore val passwordHash: String,
    // 보안성 : 직렬화된 제이슨 객체를 외부로 나가는걸 방지하기위해서
    // 데이터 보호
    // dto로 변환시 object객체에서 제외시켜줌 (비밀번호 공개를 막아줌)

) : BaseEntity() {

    fun validatePassword(rawPassword: String , passwordEncoder: PasswordEncoder):Boolean{
        return passwordEncoder.matches(rawPassword, passwordHash)
    }

    fun changeEmail(newEmail : String , usersService: UsersService){
        if(usersService.existsByEmail(newEmail)){
            throw IllegalArgumentException("이미 존재하는 이메일입니다.")
        }
        this.email = newEmail
    }

    fun toResponse() =
        UserDto.Response(
            id = id?: 0,
            email = email,
            name = name,
            createdAt = creatiedAt,
            updateAt = updateAt
        )

    companion object {
        fun create(
            command: UserCreateCommand,
            passwordEncoder: PasswordEncoder,
        ) = Users(
            email = command.email,
            name = command.name,
            passwordHash = passwordEncoder.encode(command.password),
        )
    }
}

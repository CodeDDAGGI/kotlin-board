package org.study.kotlin.board.application.service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.crypto.password.PasswordEncoder
import org.study.kotlin.board.api.controller.dto.UserCreateCommand
import org.study.kotlin.board.infrastructure.entity.Users

// 알트엔터 생성
class UsersServiceTest : BehaviorSpec({
    val passwordEncoder = mockk<PasswordEncoder>()
    val usersService = mockk<UsersService>();

    given("User 엔터티 생성 시") {
        val command = UserCreateCommand(
            email = "test@test.comm",
            password = "password123123",
            name = "test"
        )


        `when`("올바른 정보로 생성을 하면") {
            every { passwordEncoder.encode(command.password) } returns "encodedPassword"

            val user = Users.create(command, passwordEncoder)

            then("User 객체가 정상적으로 생성한다") {
                user.email shouldBe command.email
                user.name shouldBe command.name
                user.passwordHash shouldBe "encodePassword"
            }
        }
    }
    given("비밀번호 검증 시"){
        var user = Users(
            email = "test@test.com",
            name = "test",
            passwordHash = "encodePassword"
        )

        `when`("올바른 비밀번호를 입력하면") {
            every { passwordEncoder.matches("password123", "encodePassword") } returns true

            then("검증에 성공한다") {
                user.validatePassword("password123", passwordEncoder) shouldBe true
            }
        }

        `when`("잘못된 비밀번호를 입력하면"){
            every{ passwordEncoder.matches("password123", "encodePassword")} returns false
            then("검증에 실패한다"){
                user.validatePassword("password123" , passwordEncoder) shouldBe false
            }
        }
    }
    given("이메일 변경 시"){
        val user = Users(
            email = "test@test12.com",
            name = "test",
            passwordHash = "encodedPassword"
        )
        `when`("중복되지 않은 이메일"){
            then("변경에 성공한다"){

            }
        }

        `when`("중복되는 이메일로 변경을 하면"){
            then("변경에 실패한다")
        }
    }

}) // 기능 중심으로 테스트
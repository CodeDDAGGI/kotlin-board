package org.study.kotlin.board.application.service

import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerProperties.Token
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.study.kotlin.board.api.controller.dto.UserCreateCommand
import org.study.kotlin.board.api.controller.dto.UserDto
import org.study.kotlin.board.api.controller.dto.UserUpdateCommand
import org.study.kotlin.board.global.exception.CustomExcetion
import org.study.kotlin.board.infrastructure.entity.Users
import org.study.kotlin.board.infrastructure.repository.UsersRepository

@Service
@Transactional(readOnly = true) // 트랜잭션을 읽기 작업에 검
class UsersService(
    private val usersRepository: UsersRepository,
    private val passwordEncoder : PasswordEncoder,
    )
{
    @Transactional
    fun signUp(command: UserCreateCommand): UserDto.Response{
        validateDuplicateEmail(command.email)

        return usersRepository.save(
            Users.create(command, passwordEncoder)
        ).toResponse() // toResponse 라는 확장함수를 커스텀 예정
    }


    fun login(request: UserDto.LoginRequest):Users{
        val user = usersRepository.findByEmail(request.email)
            .orElseThrow{CustomExcetion.UserNotFoundExcetion("사용자를 찾을수가 없습니다")}
        if(user.validatePassword(request.password, passwordEncoder)){
            throw CustomExcetion.UnauthorizedAccessException("비밀번호가 일치하지 않습니다")
        }
        return user
    }

    fun updateUser(id:Long , command: UserUpdateCommand): UserDto.Response{
        val user = usersRepository.findById(id)
            .orElseThrow{ CustomExcetion.UserNotFoundExcetion("사용자를 찾을 수 없습니다.")}

        // 이메일 변경 시 중복 체크
        if(command.email != user.email){
            validateDuplicateEmail(command.email)
        }

        return usersRepository.save(
            Users(
                id = user.id,
                email = command.email,
                name = command.name,
                passwordHash = if(command.password !=null){
                    passwordEncoder.encode(command.password)
                }else{
                    user.passwordHash
                }
            )
        ).toResponse()
    }

    private fun validateDuplicateEmail(email:String){
        if(usersRepository.existsByEmail(email)){
            throw CustomExcetion.DuplicateEmailExcetion("이미 사용중인 이메일입니다.")
        }
    }
}

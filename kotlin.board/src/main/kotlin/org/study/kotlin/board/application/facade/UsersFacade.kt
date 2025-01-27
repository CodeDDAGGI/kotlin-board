package org.study.kotlin.board.application.facade


import org.springframework.stereotype.Component
import org.study.kotlin.board.api.controller.dto.UserDto
import org.study.kotlin.board.application.service.JwtService
import org.study.kotlin.board.application.service.UsersService

@Component
class UsersFacade (
    private val usersService: UsersService,
    private val jwtService: JwtService,
){
    fun signUp(request: UserDto.SignupRequest):UserDto.Response{
        return usersService.signUp(request.toCommend())
    }

    fun login(request: UserDto.LoginRequest): UserDto.LoginResponse{
        val generateToken = jwtService.generateToken(usersService.login(request))
        return UserDto.LoginResponse(generateToken)
    }

    fun updateUser (id: Long , request:UserDto.UpdateRequest): UserDto.Response{
        return usersService.updateUser(id, request.toCommand())
    }

    fun deleteUser(id:Long) {
        return usersService.deleteUser(id)
    }


    
}
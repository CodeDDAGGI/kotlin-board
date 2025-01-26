package org.study.kotlin.board.api.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.study.kotlin.board.api.controller.dto.UserDto
import org.study.kotlin.board.application.facade.UsersFacade

@RestController
@RequestMapping("/api/v1/users")
@Validated
class UsersController (
    private val usersFacade: UsersFacade
){

    @PostMapping("/signup")
    fun signUp(
        @Valid @RequestBody request: UserDto.SignupRequest
    ): ResponseEntity<UserDto.Response> = ResponseEntity.ok(usersFacade.signUp(request))

    @PostMapping("/signin")
    fun signIn (
        @Valid @RequestBody request: UserDto.LoginRequest
    ): ResponseEntity<UserDto.LoginResponse> = ResponseEntity.ok(usersFacade.login(request))

    @PutMapping("/{id}")
    fun updateUser (
        @PathVariable id: Long,
        @Valid @RequestBody request: UserDto.UpdateRequest
    ): ResponseEntity<UserDto.Response> = ResponseEntity.ok(usersFacade.updateUser(request))
}
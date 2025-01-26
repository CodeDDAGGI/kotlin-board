package org.study.kotlin.board.api.controller.dto

data class UserCreateCommand(
    val email: String,
    val password: String,
    val name: String,
)

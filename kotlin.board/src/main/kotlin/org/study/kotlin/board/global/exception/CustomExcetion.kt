package org.study.kotlin.board.global.exception

import org.springframework.http.HttpStatus

// enum class 랑 비슷함 - 타입이 정해져있는걸
sealed class CustomExcetion {

    class DuplicateEmailExcetion(message: String):BusinessException(
        message = message,
        status = HttpStatus.CONFLICT
    )

    class UnauthorizedAccessException(message : String) : BusinessException (
        message = message,
        status = HttpStatus.UNAUTHORIZED
    )
    class UserNotFoundExcetion(message : String) : BusinessException (
        message = message,
        status = HttpStatus.NOT_FOUND
    )


}
package org.study.kotlin.board.global.exception

import org.springframework.http.HttpStatus

abstract class BusinessException (
    message : String,
    val status : HttpStatus =  HttpStatus.BAD_REQUEST
): RuntimeException(message)

class DuplicateEmailExcetion(message: String):BusinessException(
    message = message,
    status = HttpStatus.CONFLICT
)
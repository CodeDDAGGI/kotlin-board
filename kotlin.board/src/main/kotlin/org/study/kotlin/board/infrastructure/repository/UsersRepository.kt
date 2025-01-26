package org.study.kotlin.board.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.study.kotlin.board.infrastructure.entity.Users
import java.util.Optional

// 앞쪽은 객체 (테이블의 타입) ,뒤쪽은 프라이머리 키의 타입
interface UsersRepository : JpaRepository<Users, Long> {
    fun findByEmail(email: String): Optional<Users> // optional<Users>

    fun existsByEmail(email: String): Boolean
}

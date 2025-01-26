package org.study.kotlin.board.application.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.study.kotlin.board.infrastructure.entity.Users
import java.nio.charset.StandardCharsets
import java.util.Date

@Service
class JwtService(
    private val passwordEncoder: PasswordEncoder, // 주입
) {
    companion object {
        // Value 쓸때 $만쓰면 값을 불러오는 것만함
        @Value("\${jwt.secret}")
        // lateinit 서비스가 동작을 할때 값을 가져오게
        private lateinit var secret: String
    }

    fun generateToken(users: Users): String {
        val currentTimeMillis = System.currentTimeMillis()
        return Jwts.builder()
            .subject(users.email)
            .claim("role", "USER")
            .issuedAt(Date(currentTimeMillis))
            .expiration(Date(currentTimeMillis * 1000 * 60 * 60)) // 1시간
            .signWith(Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8)))
            .compact() // 아스키코드로 바꿔줌 암호화진행
    }

//    fun test(token: String){
//        val parseJwtClaims = parseJwtClaims(token)
//        parseJwtClaims.
//    }

    fun parseJwtClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8)))
            .build()
            .parseSignedClaims(token)
            .payload // 보안이된 키를 풀것이냐
    }
}

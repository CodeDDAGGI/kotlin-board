package org.study.kotlin.board.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun filterChain(http:HttpSecurity):SecurityFilterChain{
        http.
                csrf{it.disable()}
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        "/api/v1/users/signup",
                        "/api/v1/users/signin",
                        "/h2-console"
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement({
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            })

        return http.build()
    }
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

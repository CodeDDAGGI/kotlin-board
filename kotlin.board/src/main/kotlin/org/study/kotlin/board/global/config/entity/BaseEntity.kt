package org.study.kotlin.board.global.config.entity

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass // jpa에서 사용하는 어노테이션
@EntityListeners(AuditingEntityListener::class) // 베이스엔티티가 모든 엔티티를 읽음
open class BaseEntity {
    var creatiedAt: LocalDateTime = LocalDateTime.now()
    var updateAt: LocalDateTime? = null

        protected set

    fun updateAt() {
        this.updateAt = LocalDateTime.now()
    }
}

package com.hayley.spring_batch.repository

import com.hayley.spring_batch.entity.UserNew
import org.springframework.data.jpa.repository.JpaRepository

interface UserNewRepository: JpaRepository<UserNew, Long> {
}
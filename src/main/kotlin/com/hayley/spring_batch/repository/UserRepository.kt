package com.hayley.spring_batch.repository

import com.hayley.spring_batch.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
}
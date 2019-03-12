package com.example.springoauth2client.repositories

import com.example.springoauth2client.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>

package com.example.springoauth2client.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class User (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        val name: String
) {
    override fun toString(): String = "User [name=$name, id=$id]"
}
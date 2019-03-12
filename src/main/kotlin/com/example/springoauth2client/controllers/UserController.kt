package com.example.springoauth2client.controllers

import com.example.springoauth2client.exception.UserDataIsEmptyException
import com.example.springoauth2client.model.User
import com.example.springoauth2client.repositories.UserRepository
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class UserController(private val userRepository: UserRepository) {

    @RequestMapping(value = ["/user"])
    fun user(principal: Principal): Principal  {
        val input = (principal as OAuth2Authentication).userAuthentication.details as HashMap<String, String>
        val id = input["id"].toString()
        val login = input["login"]
        if (id.isEmpty() || login == null) throw UserDataIsEmptyException("User ID or LOGIN IS EMPTY")
        val user = User(id.toLong(), login)
        userRepository.save(user)
        return principal
    }
}
package com.example.springoauth2client.controllers

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class UserController {

    @RequestMapping(value = ["/"])
    fun user(user: Principal) = "Hello ${user.name}"
}
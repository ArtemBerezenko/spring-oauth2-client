package com.example.springoauth2client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringOauth2ClientApplication

fun main(args: Array<String>) {
    runApplication<SpringOauth2ClientApplication>(*args)
}

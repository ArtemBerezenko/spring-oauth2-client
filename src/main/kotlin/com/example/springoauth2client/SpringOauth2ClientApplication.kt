package com.example.springoauth2client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
@EnableOAuth2Sso
class SpringOauth2ClientApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(SpringOauth2ClientApplication::class.java)
            .properties("spring.config.name=client").run(*args)
}

package com.example.springoauth2client

import com.example.springoauth2client.model.User
import com.example.springoauth2client.repositories.UserRepository
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class SpringOauth2ClientApplicationTests {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun contextLoads() {
    }

    @Test
    fun userRepositoryTest() {
        val user = User(1, "TEST")
        userRepository.save(user)
        val userFromBD = userRepository.findById(1)
        assertNotNull(userFromBD)
    }

}

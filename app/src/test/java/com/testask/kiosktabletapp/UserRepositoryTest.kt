package com.testask.kiosktabletapp

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.HttpException
import retrofit2.Response

// Define your test classes here
//class UserRepositoryTest {
//
//    private lateinit var apiService: ApiService
//    private lateinit var userRepository: UserRepository
//
//    @Before
//    fun setUp() {
//        apiService = mock(ApiService::class.java)
//        userRepository = UserRepository(apiService)
//    }
//
//    @Test
//    fun `loginUser returns valid response when successful`() = runTest {
//        val user = User1("username@example.com", "Password123")
//        val data = Data("John Doe", "token123")
//        val responseAuth = ResponseAuth(data, "Success", true)
//        `when`(apiService.loginUser(user)).thenReturn(responseAuth)
//
//        val result = userRepository.loginUser("username@example.com", "Password123")
//
//        assertEquals(responseAuth, result)
//    }
//}

package com.example.chattingapp.service

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.chattingapp.dto.User
import com.example.chattingapp.service.util.rest.RestApiService
import com.example.chattingapp.service.util.rest.RestServiceCallback
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomApiTest {

    @Test
    fun testCreate(){
//        ServiceTestUtils.loginAndDo(restApiService, user){
//            restApiService.createRoom("모두 모여!").enqueue(RestServiceCallback<String>{
//                assertEquals(it.code(), 200)
//                assertEquals(it.body() as String, "true")
//            })
//        }
    }

    companion object {
        @JvmStatic
        var restApiService: RestApiService = RestApiService.instance
        val user = User("ma", "jasin", "itna", "123", "1234")
    }
}
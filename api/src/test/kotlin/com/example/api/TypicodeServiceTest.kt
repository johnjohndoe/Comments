package com.example.api

import com.example.api.models.Comment
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

private const val BASE_URL = "https://jsonplaceholder.typicode.com"

@RunWith(JUnit4::class)
class TypicodeServiceTest {

    @Test
    fun `Validates a comments response`() {
        val call = service.getComments()
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                val comments = response.body()
                assertThat(comments).isNotNull
                comments?.forEach { assertComments(it) }
            } else {
                fail("getComments() response is not successful.")
            }
        } catch (e: IOException) {
            fail("Should not throw {$e}")
        }
    }

    private fun assertComments(comment: Comment) = with(comment) {
        assertThat(postId).isNotNull()
        assertThat(remoteId).isNotNull()
        assertThat(message).isNotNull()
        assertThat(emailAddress).isNotNull()
        assertThat(authorName).isNotNull()
    }

    private val service: TypicodeService by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .build()
        ApiModule.provideTypicodeService(BASE_URL, okHttpClient)
    }

}
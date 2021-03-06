package example.micronaut.bookinventory

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class BooksControllerTest {
    @Inject
    @field:Client("/")
    lateinit var rxHttpClient: RxHttpClient

    @Test
    fun testBooksController() {
        val rsp = rxHttpClient.toBlocking().exchange(HttpRequest.GET<Any>("/books/stock/1491950358"), Boolean::class.java)
        Assertions.assertEquals(rsp.status(), HttpStatus.OK)
        Assertions.assertTrue(rsp.body()!!)
    }

    @Test
    fun testBooksControllerWithNonExistingIsbn() {
        val thrown = Assertions.assertThrows(HttpClientResponseException::class.java) { rxHttpClient.toBlocking().exchange(HttpRequest.GET<Any>("/books/stock/XXXXX"), Boolean::class.java) }
        Assertions.assertEquals(
                HttpStatus.NOT_FOUND,
                thrown.response.status
        )
    }
}
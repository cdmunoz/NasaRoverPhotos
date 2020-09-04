package co.cdmunoz.nasaroverphotos.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.cdmunoz.nasaroverphotos.data.repository.BaseRepository.Companion.SOMETHING_WRONG
import co.cdmunoz.nasaroverphotos.di.configureTestAppModules
import co.cdmunoz.nasaroverphotos.utils.MockWebServerBaseTest
import co.cdmunoz.nasaroverphotos.utils.Result
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.ArgumentMatchers.anyInt
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class PhotosRepositoryTest : MockWebServerBaseTest(), KoinTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val photosRepository: PhotosRepository by inject()
    private val sol = anyInt()
    private val page = anyInt()

    override fun isMockServerEnabled() = true

    @Before
    fun start() {
        super.setUp()
        startKoin {
            modules(configureTestAppModules(getMockWebServerUrl()))
        }
    }

    @After
    fun after() {
        stopKoin()
        super.tearDown()
    }

    @Test
    fun `given response ok when fetching results then return a list with elements`() {
        runBlocking {
            mockHttpResponse("json/photos_response_one_item.json", HttpURLConnection.HTTP_OK)
            val apiResponse = photosRepository.getPhotosFromApi(sol, page)

            assertNotNull(apiResponse)
            assertEquals(apiResponse.extractData?.size, 1)
        }
    }

    @Test
    fun `given response ok when fetching empty results then return an empty list`() {
        runBlocking {
            mockHttpResponse("json/photos_response_empty_list.json", HttpURLConnection.HTTP_OK)
            val apiResponse = photosRepository.getPhotosFromApi(sol, page)

            assertNotNull(apiResponse)
            assertEquals(apiResponse.extractData?.size, 0)
        }
    }

    @Test
    fun `given response failure when fetching results then return exception`() {
        runBlocking {
            mockHttpResponse(PhotosRepository.GENERAL_ERROR_CODE)
            val apiResponse = photosRepository.getPhotosFromApi(sol, page)

            assertNotNull(apiResponse)
            val expectedValue = Result.Error(Exception(SOMETHING_WRONG))
            assertEquals(expectedValue.exception.message,
                (apiResponse as Result.Error).exception.message)
        }
    }
}
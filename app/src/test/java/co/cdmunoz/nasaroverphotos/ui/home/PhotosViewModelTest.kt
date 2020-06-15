package co.cdmunoz.nasaroverphotos.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import co.cdmunoz.nasaroverphotos.data.model.Photo
import co.cdmunoz.nasaroverphotos.data.repository.PhotosRepository
import co.cdmunoz.nasaroverphotos.utils.Result
import co.cdmunoz.nasaroverphotos.utils.TestCoroutineRule
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import org.mockito.Mockito.`when` as whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PhotosViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    private lateinit var viewModel: PhotosViewModel
    @Mock
    private lateinit var photosRepository: PhotosRepository
    @Mock
    private lateinit var photosResponseObserver: Observer<Result<ArrayList<Photo>>>

    @Before
    fun setUp() {
        viewModel = PhotosViewModel(photosRepository)
    }

    @Test
    fun `when fetching results ok then return a list successfully`() {
        val emptyList = arrayListOf<Photo>()
        testCoroutineRule.runBlockingTest {
            viewModel.getPhotos().observeForever(photosResponseObserver)
            whenever(photosRepository.getPhotosFromApi(anyInt(), anyInt())).thenAnswer {
                Result.Success(emptyList)
            }
            viewModel.loadData()
            assertNotNull(viewModel.getPhotos().value)
            assertEquals(Result.Success(emptyList), viewModel.getPhotos().value)
        }
    }

    @Test
    fun `when calling for results then return loading`() {
        testCoroutineRule.runBlockingTest {
            viewModel.getPhotos().observeForever(photosResponseObserver)
            viewModel.loadData()
            verify(photosResponseObserver).onChanged(Result.InProgress)
        }
    }

    @Test
    fun `when fetching results fails then return an error`() {
        val exception = mock(HttpException::class.java)
        testCoroutineRule.runBlockingTest {
            viewModel.getPhotos().observeForever(photosResponseObserver)
            whenever(photosRepository.getPhotosFromApi(anyInt(), anyInt())).thenAnswer {
                Result.Error(exception)
            }
            viewModel.loadData()
            assertNotNull(viewModel.getPhotos().value)
            assertEquals(Result.Error(exception), viewModel.getPhotos().value)
        }
    }

    @After
    fun tearDown() {
        viewModel.getPhotos().removeObserver(photosResponseObserver)
    }
}
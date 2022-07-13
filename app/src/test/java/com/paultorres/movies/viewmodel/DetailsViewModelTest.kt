package com.paultorres.movies.viewmodel

import androidx.paging.ExperimentalPagingApi
import com.paultorres.movies.data.MoviesRepository
import com.paultorres.movies.model.local.MoviesEntity
import com.paultorres.movies.myFakesMovie
import com.paultorres.movies.util.MOVIE_TYPE
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class DetailsViewModelTest{
    @RelaxedMockK
    private lateinit var vm: DetailsViewModel
    @RelaxedMockK
    private lateinit var repository: MoviesRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        vm = DetailsViewModel(repository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when detailsviewmodel return a single movie`() = runTest {

        coEvery { repository.getMovie(MOVIE_TYPE,1) } returns myFakesMovie
        // When

        val movie = repository.getMovie(MOVIE_TYPE,1)

        //Then
        assert(myFakesMovie == movie)
    }
}


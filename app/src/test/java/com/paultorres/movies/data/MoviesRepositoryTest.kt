package com.paultorres.movies.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import com.paultorres.movies.data.api.MoviesAPIInterface
import com.paultorres.movies.data.local.MoviesDatabase
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
class MoviesRepositoryTest {
    @RelaxedMockK
    private lateinit var repository: MoviesRepository

    @RelaxedMockK
    private lateinit var db: MoviesDatabase

    @RelaxedMockK
    private lateinit var api: MoviesAPIInterface


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = MoviesRepository(api, db)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when Repository return a single movie`() = runTest {

        coEvery { repository.getMovie(MOVIE_TYPE, 1) } returns myFakesMovie
        // When

        val movie = db.moviesDBInterface().getSearchMovie(MOVIE_TYPE, 1)

        //Then
        assert(myFakesMovie == movie)

    }

}


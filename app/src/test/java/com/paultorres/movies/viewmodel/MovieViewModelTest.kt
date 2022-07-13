package com.paultorres.movies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.paging.map
import com.paultorres.movies.data.MoviesRepository
import com.paultorres.movies.model.local.MoviesEntity
import com.paultorres.movies.myFakesMoviesList
import com.paultorres.movies.util.MOVIE_TYPE
import com.paultorres.movies.util.POPULAR_TYPE
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class MovieViewModelTest {
    @RelaxedMockK
    private lateinit var repository: MoviesRepository
    private lateinit var vm: MovieViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        vm = MovieViewModel(repository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when movieviewmodel return movies`() = runTest {

        var list: List<MoviesEntity> = emptyList()

        vm.pop

        repository.getMovies(MOVIE_TYPE, POPULAR_TYPE).collect {
            it.map {
                list.indexOf(it)
                Assert.assertEquals(myFakesMoviesList, list)
            }
        }
    }
}



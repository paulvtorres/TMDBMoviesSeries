package com.paultorres.movies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.paging.map
import com.paultorres.movies.data.MoviesRepository
import com.paultorres.movies.model.local.MoviesEntity
import com.paultorres.movies.myFakesSeriesList
import com.paultorres.movies.util.POPULAR_TYPE
import com.paultorres.movies.util.SERIE_TYPE
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class SerieViewModelTest {
    @RelaxedMockK
    private lateinit var repository: MoviesRepository
    private lateinit var vm: SerieViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        vm = SerieViewModel(repository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when serieviewmodel return series list`() = runTest {
        var list: List<MoviesEntity> = emptyList()

        vm.pop

        repository.getMovies(SERIE_TYPE, POPULAR_TYPE).collect {
            it.map {
                list.indexOf(it)
                Assert.assertEquals(myFakesSeriesList, list)
            }
        }
    }
}



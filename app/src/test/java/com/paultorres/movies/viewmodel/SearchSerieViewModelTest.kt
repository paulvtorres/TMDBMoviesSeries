package com.paultorres.movies.viewmodel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.paging.map
import com.paultorres.movies.data.MoviesRepository
import com.paultorres.movies.model.local.MoviesEntity
import com.paultorres.movies.myFakesSeriesList
import com.paultorres.movies.util.SERIE_TYPE
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test



@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class SearchSerieViewModelTest{
    @RelaxedMockK
    private lateinit var repository: MoviesRepository
    private lateinit var vm: SearchMovieViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        vm = SearchMovieViewModel(repository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when searchserieviewmodel return series search`() = runTest {
        var list: List<MoviesEntity> = emptyList()

        vm.searchMovies("1")

        repository.getSearch(SERIE_TYPE, "1").collect {
            it.map {
                list.indexOf(it)
                assertEquals(myFakesSeriesList, list)
            }
        }
    }
}
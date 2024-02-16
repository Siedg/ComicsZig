package br.com.comicszig

import br.com.comicszig.core.components.Status
import br.com.comicszig.core.components.ViewState
import br.com.comicszig.home.domain.HomeRepository
import br.com.comicszig.home.model.ComicItemModel
import br.com.comicszig.home.model.DataModel
import br.com.comicszig.home.presentation.viewmodel.HomeViewModel
import io.mockk.called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ViewModelTest {

    @get:Rule
    val rule = instantLiveDataAndCoroutineRules

    private lateinit var viewModel: HomeViewModel
    private val repository: HomeRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun whenGetComics_CheckIfSuccessAndData() {
        coEvery { repository.getComics() } returns ComicItemModel(data = DataModel(results = listOf()))

        viewModel.getComics()

        assertEquals(ComicItemModel(data = DataModel(results = listOf())), viewModel.comics.value.data)
        assertEquals(Status.SUCCESS, viewModel.comics.value.status)
    }

    @Test
    fun whenGetComicsIsCalled_CheckIfRepositoryIsCalled() {
        coEvery { repository.getComics() } returns ComicItemModel(data = DataModel(results = listOf()))

        viewModel.getComics()
        coVerify(exactly = 1) { repository.getComics() }
    }

    @Test
    fun whenGetComicsIsCalled_CheckIfError() {
        coEvery { repository.getComics() } throws Exception(MOCK_EXCEPTION)
        viewModel.getComics()

        coVerify(exactly = 1) { repository.getComics() }
        assertEquals(Status.ERROR, viewModel.comics.value.status)
    }

    private companion object  {
        const val MOCK_EXCEPTION = "Error mockk"
    }
}
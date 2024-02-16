package br.com.comicszig.home.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.pullRefreshIndicatorTransform
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.comicszig.R
import br.com.comicszig.core.components.Status
import br.com.comicszig.home.model.ResultModel
import br.com.comicszig.home.presentation.components.ComicItem
import br.com.comicszig.home.presentation.components.LoadingList
import br.com.comicszig.home.presentation.viewmodel.HomeViewModel
import br.com.comicszig.ui.theme.Dimensions.dimen_16dp
import br.com.comicszig.ui.theme.Dimensions.dimen_1dp
import br.com.comicszig.ui.theme.Dimensions.dimen_24dp
import br.com.comicszig.ui.theme.Dimensions.dimen_8dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent() {
    val viewModel: HomeViewModel = koinViewModel()
    val comics by remember { viewModel.comics }

    val refreshScope = rememberCoroutineScope()
    val refreshing by remember { viewModel.refreshing }

    fun refresh() = refreshScope.launch {
        viewModel.changeRefreshingState(true)
        delay(1000)
        viewModel.getComics()
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)

    LaunchedEffect(viewModel) {
        viewModel.getComics()
    }

    when(comics.status) {
        Status.LOADING -> {
            LoadingList()
        }

        Status.SUCCESS -> {
            val comicsList = mutableListOf<ResultModel>()
            comics.data?.data?.results?.let { comicsList.addAll(it) }
            LazyColumn(
                modifier = Modifier
                    .pullRefresh(state)
                    .fillMaxSize(),
                contentPadding = PaddingValues(dimen_16dp),
                content = {
                    if (!refreshing) {
                        comics.data?.let {
                            items(comicsList) { comic ->
                                ComicItem(
                                    modifier = Modifier.padding(top = dimen_8dp),
                                    title = comic.title,
                                    description = comic.description,
                                    imagePath = comic.thumbnail.path,
                                    extension = comic.thumbnail.extension
                                )

                                HorizontalDivider(
                                    modifier = Modifier.padding(top = dimen_8dp),
                                    thickness = dimen_1dp
                                )
                            }
                        }
                    }
                }
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(dimen_24dp)
                        .pullRefreshIndicatorTransform(state)
                        .align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 3.dp
                )
            }
        }

        Status.ERROR -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.home_error_label)
                )
                Button(
                    shape = RoundedCornerShape(dimen_8dp),
                    onClick = { viewModel.getComics() }
                ) {
                    Text(
                        text = stringResource(id = R.string.home_button_retry)
                    )
                }
            }
        }

        else -> {}
    }
}

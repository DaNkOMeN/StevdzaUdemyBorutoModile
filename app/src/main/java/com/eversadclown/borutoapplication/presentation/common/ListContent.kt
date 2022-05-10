package com.eversadclown.borutoapplication.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.eversadclown.borutoapplication.domain.model.Hero
import com.eversadclown.borutoapplication.presentation.components.HeroItem
import com.eversadclown.borutoapplication.presentation.components.ShimmerEffect
import com.eversadclown.borutoapplication.ui.theme.SMALL_PADDING

@Composable
fun ListContent(
    heroes: LazyPagingItems<Hero>,
    navController: NavHostController
) {
    val result = handlePagingResult(heroes = heroes)
    if (result) {
        LazyColumn(
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(
                SMALL_PADDING
            )
        ) {
            items(items = heroes, key = { hero -> hero.id }) { hero ->
                hero?.let { HeroItem(hero = hero, navController = navController) }
            }
        }
    }
}

@Composable
fun handlePagingResult(heroes: LazyPagingItems<Hero>): Boolean {
    heroes.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }

        return when {
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
                return false
            }
            error != null -> {
                false
            }
            else -> true
        }
    }
}

package com.eversadclown.borutoapplication.presentation.screen.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.eversadclown.borutoapplication.presentation.common.ListContent

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val allHeroes =
        homeViewModel.getAllHeroes.collectAsLazyPagingItems() //Было Flow<PagingData<Hero>>

    Scaffold(topBar = {
        HomeTopBar(onSearchClicker = {

        })
    }, content = {
        ListContent(heroes = allHeroes, navController = navController)
    })

}





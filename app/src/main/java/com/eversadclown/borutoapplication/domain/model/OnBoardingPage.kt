package com.eversadclown.borutoapplication.domain.model

import androidx.annotation.DrawableRes
import com.eversadclown.borutoapplication.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.drawable.welcome,
        title = "Greeding",
        description = "Are you Boruto fan? Because if you are then we have great news for you"
    )
    object Second : OnBoardingPage(
        image = R.drawable.explore,
        title = "Explore",
        description = "Find your favorite heroes and learn some of the things that you didn't know about"
    )
    object Third : OnBoardingPage(
        image = R.drawable.power,
        title = "Power",
        description = "Check out your hero's power and see how much are they strong compatring to others"
    )
}

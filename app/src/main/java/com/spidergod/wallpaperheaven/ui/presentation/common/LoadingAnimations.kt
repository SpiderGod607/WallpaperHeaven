package com.spidergod.wallpaperheaven.ui.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.spidergod.wallpaperheaven.R

@Composable
fun PaperPlaneLoading(modifier: Modifier) {
    val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.paperplane) }
    val animationState =
        rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)
    LottieAnimation(
        spec = animationSpec,
        animationState = animationState,
        modifier = modifier.fillMaxWidth(),

        )
}

@Composable
fun NoInternetAnimation(modifier: Modifier) {
    val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.no_internet) }
    val animationState =
        rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)
    LottieAnimation(
        spec = animationSpec,
        animationState = animationState,
        modifier = modifier.fillMaxWidth()
    )
}


@Composable
fun WaveLoading(modifier: Modifier) {
    val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.wave_loading) }
    val animationState =
        rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)
    LottieAnimation(
        spec = animationSpec,
        animationState = animationState,
        modifier = modifier
    )
}
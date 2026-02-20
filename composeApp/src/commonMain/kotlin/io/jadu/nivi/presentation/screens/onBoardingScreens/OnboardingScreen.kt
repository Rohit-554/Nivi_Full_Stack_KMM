package io.jadu.nivi.presentation.screens.onBoardingScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.jadu.nivi.presentation.components.ButtonUI
import io.jadu.nivi.presentation.components.GlassContainer
import io.jadu.nivi.presentation.components.RiveAnimationComponent
import io.jadu.nivi.presentation.components.lottie.LottieAnimation
import io.jadu.nivi.presentation.screens.OnboardingPage
import io.jadu.nivi.presentation.screens.OnboardingViewModel
import io.jadu.nivi.presentation.theme.MajorColors
import io.jadu.nivi.presentation.theme.Spacing
import io.jadu.nivi.presentation.theme.bodyLarge
import io.jadu.nivi.presentation.theme.h1TextStyle
import io.jadu.nivi.presentation.utils.VSpacer
import io.jadu.nivi.presentation.utils.extensions.bounceClickable
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit,
    viewModel: OnboardingViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val pagerState = rememberPagerState(pageCount = { uiState.pages.size })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.updateCurrentPage(page)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            OnboardingPageContent(
                page = uiState.pages[page],
                pageNumber = page
            )
        }

        VSpacer(Spacing.s8)

        if (!uiState.isLastPage) {
            NormalOnBoardingScreens(
                onSkipClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(uiState.totalPages - 1)
                    }
                },
                onNextClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(uiState.currentPage + 1)
                    }
                }
            )
        } else {
            LastOnBoardingPage {
                if (uiState.isLastPage) {
                    viewModel.nextPage(onFinishOnboarding)
                }
            }
        }
    }
}

@Composable
private fun OnboardingPageContent(
    page: OnboardingPage,
    pageNumber: Int,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        RiveAnimationComponent(
            source = page.riveAnimationUrl,
            modifier = Modifier
                .fillMaxSize()
                .blur(Spacing.s64)
                .alpha(if (pageNumber >= 1) 0.4f else 1f)
                .scale(2f),
            autoPlay = true,
            loop = true
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .padding(horizontal = Spacing.s24),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = page.title,
                style = h1TextStyle().copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 48.sp
                ),
                textAlign = TextAlign.Start
            )
            VSpacer(Spacing.s32)
            Text(
                text = page.description,
                style = bodyLarge(),
                textAlign = TextAlign.Start
            )
            VSpacer(Spacing.s64)
        }
    }

}


@Composable
private fun BoxScope.NormalOnBoardingScreens(
    onSkipClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().align(Alignment.BottomEnd),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .padding(start = Spacing.s16)
                .bounceClickable { onSkipClick() },
            text = "Skip",
            style = bodyLarge()
        )
        GlassContainer(
            modifier = Modifier
                .padding(Spacing.s16),
            shape = CircleShape
        ) {
            IconButton(
                modifier = Modifier,
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(Spacing.s36)
                    )
                },
                onClick = onNextClick
            )
        }
    }

}

@Composable
private fun BoxScope.LastOnBoardingPage(
    onClick: () -> Unit
) {
    LottieAnimation(
        path = "files/lottie/chick_bird_1.json",
        modifier = Modifier
            .size(152.dp)
    )

    GlassContainer(
        modifier = Modifier
            .padding(Spacing.s16)
            .align(Alignment.BottomCenter),
        backgroundAlpha = 1f
    ) {
        ButtonUI(
            modifier = Modifier
                .fillMaxWidth()
                .height(Spacing.s48),
            containerColor = MajorColors.TrustBlue.color,
            onClick = onClick,
            text = "Let's Begin",
            enabled = true,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewOnboardingSong() {
    OnboardingScreen(onFinishOnboarding = {})
}

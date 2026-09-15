package com.example.lampstandmobile.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lampstandmobile.data.convex.ConvexClientProvider
import com.example.lampstandmobile.ui.auth.AuthNavigation
import com.example.lampstandmobile.ui.auth.AuthScreen
import com.example.lampstandmobile.ui.auth.AuthState as UiAuthState
import com.example.lampstandmobile.ui.auth.AuthStep
import com.example.lampstandmobile.ui.components.shared.BottomNav
import com.example.lampstandmobile.ui.components.shared.BottomNavAction
import com.example.lampstandmobile.ui.components.shared.BottomNavState
import com.example.lampstandmobile.ui.components.shared.BottomNavTab
import com.example.lampstandmobile.ui.dashboard.DashboardContent
import com.example.lampstandmobile.ui.dashboard.DashboardScreen
import com.example.lampstandmobile.ui.dashboard.DashboardState
import com.example.lampstandmobile.ui.dashboard.DashboardViewModel
import com.example.lampstandmobile.ui.journey.completed.MyJourneyCompletedScreen
import com.example.lampstandmobile.ui.journey.detail.JourneyDetailScreen
import com.example.lampstandmobile.ui.journey.feedback.FeedbackOption
import com.example.lampstandmobile.ui.journey.feedback.MyJourneyFeedbackContent
import com.example.lampstandmobile.ui.journey.feedback.MyJourneyFeedbackScreen
import com.example.lampstandmobile.ui.journey.list.JourneyPathUi
import com.example.lampstandmobile.ui.journey.list.MyJourneyListContent
import com.example.lampstandmobile.ui.journey.list.MyJourneyListScreen
import com.example.lampstandmobile.ui.journey.list.MyJourneyListState
import com.example.lampstandmobile.ui.journey.list.MyJourneyListViewModel
import com.example.lampstandmobile.ui.journey.lookingforward.MyJourneyLookingForwardScreen
import com.example.lampstandmobile.ui.journey.session.MyJourneySessionScreen
import com.example.lampstandmobile.ui.journey.support.MyJourneySupportScreen
import com.example.lampstandmobile.ui.onboarding.OnboardingPreferencesScreen
import com.example.lampstandmobile.ui.onboarding.OnboardingProfileScreen
import com.example.lampstandmobile.ui.paths.CategoryPathsScreen
import com.example.lampstandmobile.ui.paths.PathsScreen
import com.example.lampstandmobile.ui.profile.PersonalInformationScreen
import com.example.lampstandmobile.ui.profile.ProfileScreen
import com.example.lampstandmobile.ui.theme.LampStandMobileTheme
import com.example.lampstandmobile.R
import dev.convex.android.AuthState as ConvexAuthState

@Composable
fun AppNavigation(
    initialDestination: AppDestination = AppDestination.AUTH,
    enableAuthSync: Boolean = !LocalInspectionMode.current
) {
    var state by remember(initialDestination) {
        mutableStateOf(AppNavigationState(currentDestination = initialDestination))
    }

    val onNavigate: (AppDestination) -> Unit = { destination ->
        state = state.copy(currentDestination = destination)
    }

    if (enableAuthSync) {
        val isConvexReady = try {
            ConvexClientProvider.client
            true
        } catch (_: Exception) {
            false
        }
        val convexAuthState by if (isConvexReady) {
            ConvexClientProvider.client.authState.collectAsState()
        } else {
            remember { mutableStateOf<ConvexAuthState<String>>(ConvexAuthState.AuthLoading()) }
        }

        LaunchedEffect(convexAuthState) {
            when (convexAuthState) {
                is ConvexAuthState.Authenticated<*> -> {
                    if (state.currentDestination == AppDestination.AUTH) {
                        onNavigate(AppDestination.HOME)
                    }
                }
                is ConvexAuthState.Unauthenticated<*> -> {
                    if (state.currentDestination != AppDestination.AUTH) {
                        onNavigate(AppDestination.AUTH)
                    }
                }
                is ConvexAuthState.AuthLoading<*> -> {
                    // Keep current state or show splash
                }
            }
        }
    }

    val isPreview = LocalInspectionMode.current || !enableAuthSync

    // Map AppDestination to BottomNavTab - mirrors web activeTab prop
    // WEB: BottomNav activeTab="home"|"paths"|"journey"|"profile" per page
    val bottomNavTab: BottomNavTab? = when (state.currentDestination) {
        AppDestination.HOME -> BottomNavTab.HOME
        AppDestination.PATHS, AppDestination.PATH_DETAIL -> BottomNavTab.PATHS
        AppDestination.JOURNEY,
        AppDestination.JOURNEY_LIST,
        AppDestination.JOURNEY_DETAIL -> BottomNavTab.JOURNEY
        // SESSION + FEEDBACK + COMPLETED + LOOKING_FORWARD + SUPPORT + ONBOARDING + PROFILE_PERSONAL_INFO hide BottomNav like web layout.tsx:12 endsWith /session /feedback
        AppDestination.JOURNEY_SESSION,
        AppDestination.JOURNEY_FEEDBACK,
        AppDestination.JOURNEY_COMPLETED,
        AppDestination.JOURNEY_LOOKING_FORWARD,
        AppDestination.JOURNEY_SUPPORT,
        AppDestination.ONBOARDING_PROFILE,
        AppDestination.ONBOARDING,
        AppDestination.PROFILE_PERSONAL_INFO -> null
        AppDestination.PROFILE -> BottomNavTab.PROFILE
        AppDestination.AUTH -> null
    }

    if (bottomNavTab != null) {
        Scaffold(
            bottomBar = {
                BottomNav(
                    state = BottomNavState(activeTab = bottomNavTab),
                    onAction = { action ->
                        if (action is BottomNavAction.SelectTab) {
                            when (action.tab) {
                                BottomNavTab.HOME -> onNavigate(AppDestination.HOME)
                                BottomNavTab.PATHS -> onNavigate(AppDestination.PATHS)
                                BottomNavTab.JOURNEY -> onNavigate(AppDestination.JOURNEY_LIST)
                                BottomNavTab.PROFILE -> onNavigate(AppDestination.PROFILE)
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (state.currentDestination) {
                    AppDestination.AUTH -> {
                        if (isPreview) {
                            // Interactive preview: stateful AUTH so EMAIL -> OTP switches like web app/auth/page.tsx currentStep
                            var previewAuthState by remember { mutableStateOf(UiAuthState()) }
                            AuthScreen(
                                state = previewAuthState,
                                onAction = { action ->
                                    when (action) {
                                        is com.example.lampstandmobile.ui.auth.AuthAction.EmailChanged -> previewAuthState = previewAuthState.copy(email = action.email)
                                        com.example.lampstandmobile.ui.auth.AuthAction.SendOtp -> previewAuthState = previewAuthState.copy(currentStep = AuthStep.OTP)
                                        is com.example.lampstandmobile.ui.auth.AuthAction.CodeChanged -> {
                                            val newCode = previewAuthState.code.toMutableList().also { it[action.index] = action.value }
                                            previewAuthState = previewAuthState.copy(code = newCode)
                                        }
                                        com.example.lampstandmobile.ui.auth.AuthAction.VerifyOtp -> previewAuthState = previewAuthState.copy(isLoading = true)
                                        else -> Unit
                                    }
                                }
                            )
                        } else {
                            AuthNavigation()
                        }
                    }
                    AppDestination.HOME -> {
                        if (isPreview) {
                            DashboardContent(
                                state = DashboardState(
                                    homeState = mapOf(
                                        "state" to "active",
                                        "pathTitle" to "Trust in Uncertainty",
                                        "currentDay" to 3,
                                        "totalDays" to 7,
                                        "todayDone" to false
                                    )
                                ),
                                onAction = {},
                                onNavigate = onNavigate,
                                contentPadding = innerPadding
                            )
                        } else {
                            val dashboardViewModel: DashboardViewModel = viewModel()
                            DashboardScreen(
                                viewModel = dashboardViewModel,
                                onNavigate = onNavigate,
                                contentPadding = innerPadding
                            )
                        }
                    }
                    AppDestination.PATHS -> {
                        PathsScreen(onNavigate = onNavigate, contentPadding = innerPadding)
                    }
                    AppDestination.PATH_DETAIL -> {
                        CategoryPathsScreen(onNavigate = onNavigate, contentPadding = innerPadding)
                    }
                    AppDestination.JOURNEY,
                    AppDestination.JOURNEY_LIST -> {
                        val vm: MyJourneyListViewModel = viewModel()
                        MyJourneyListScreen(
                            viewModel = vm,
                            onNavigate = onNavigate,
                            contentPadding = innerPadding
                        )
                    }
                    AppDestination.JOURNEY_DETAIL -> {
                        JourneyDetailScreen(
                            onNavigate = onNavigate,
                            onBack = { onNavigate(AppDestination.JOURNEY_LIST) },
                            onContinueReading = { onNavigate(AppDestination.JOURNEY_SESSION) },
                            onSessionClick = { onNavigate(AppDestination.JOURNEY_SESSION) },
                            contentPadding = innerPadding
                        )
                    }
                    AppDestination.JOURNEY_SESSION -> {
                        MyJourneySessionScreen(
                            onBack = { onNavigate(AppDestination.JOURNEY_DETAIL) },
                            onComplete = { onNavigate(AppDestination.JOURNEY_FEEDBACK) },
                            contentPadding = innerPadding
                        )
                    }
                    AppDestination.JOURNEY_FEEDBACK -> {
                        MyJourneyFeedbackScreen(
                            onBack = { onNavigate(AppDestination.JOURNEY_SESSION) },
                            onNavigate = { dest -> if (dest == AppDestination.JOURNEY_DETAIL) onNavigate(AppDestination.JOURNEY_COMPLETED) else onNavigate(dest) },
                            contentPadding = innerPadding
                        )
                    }
                    AppDestination.JOURNEY_COMPLETED -> {
                        MyJourneyCompletedScreen(
                            onBack = { onNavigate(AppDestination.JOURNEY_FEEDBACK) },
                            onNavigate = { onNavigate(AppDestination.JOURNEY_LOOKING_FORWARD) },
                            contentPadding = innerPadding
                        )
                    }
                    AppDestination.JOURNEY_LOOKING_FORWARD -> {
                        MyJourneyLookingForwardScreen(
                            onBack = { onNavigate(AppDestination.JOURNEY_COMPLETED) },
                            onNavigate = { onNavigate(AppDestination.JOURNEY_SUPPORT) },
                            contentPadding = innerPadding
                        )
                    }
                    AppDestination.JOURNEY_SUPPORT -> {
                        MyJourneySupportScreen(
                            onBack = { onNavigate(AppDestination.JOURNEY_LOOKING_FORWARD) },
                            onNavigate = onNavigate,
                            contentPadding = innerPadding
                        )
                    }
                    AppDestination.ONBOARDING_PROFILE -> {
                        OnboardingProfileScreen(
                            onContinue = { _, _ -> onNavigate(AppDestination.ONBOARDING) },
                            onNavigateProfile = { onNavigate(AppDestination.ONBOARDING_PROFILE) },
                            onNavigateOnboarding = { onNavigate(AppDestination.ONBOARDING) }
                        )
                    }
                    AppDestination.ONBOARDING -> {
                        OnboardingPreferencesScreen(
                            onBackToProfile = { onNavigate(AppDestination.ONBOARDING_PROFILE) },
                            onStartJourney = { _, _, _, _ -> onNavigate(AppDestination.HOME) },
                            onNavigateProfile = { onNavigate(AppDestination.ONBOARDING_PROFILE) },
                            onNavigateOnboarding = { onNavigate(AppDestination.ONBOARDING) }
                        )
                    }
                    AppDestination.PROFILE -> {
                        ProfileScreen(onNavigate = onNavigate, contentPadding = innerPadding)
                    }
                    AppDestination.PROFILE_PERSONAL_INFO -> {
                        com.example.lampstandmobile.ui.profile.PersonalInformationScreen(onNavigate = onNavigate, contentPadding = innerPadding)
                    }
                }
            }
        }
    } else {
        // AUTH + ONBOARDING + SESSION etc - no bottom nav
        when (state.currentDestination) {
            AppDestination.AUTH -> {
                if (isPreview) {
                    var previewAuthState2 by remember { mutableStateOf(UiAuthState()) }
                    AuthScreen(
                        state = previewAuthState2,
                        onAction = { action ->
                            when (action) {
                                is com.example.lampstandmobile.ui.auth.AuthAction.EmailChanged -> previewAuthState2 = previewAuthState2.copy(email = action.email)
                                com.example.lampstandmobile.ui.auth.AuthAction.SendOtp -> previewAuthState2 = previewAuthState2.copy(currentStep = AuthStep.OTP)
                                is com.example.lampstandmobile.ui.auth.AuthAction.CodeChanged -> {
                                    val newCode = previewAuthState2.code.toMutableList().also { it[action.index] = action.value }
                                    previewAuthState2 = previewAuthState2.copy(code = newCode)
                                }
                                else -> Unit
                            }
                        }
                    )
                } else {
                    AuthNavigation()
                }
            }
            AppDestination.ONBOARDING_PROFILE -> OnboardingProfileScreen(onContinue = { _, _ -> onNavigate(AppDestination.ONBOARDING) }, onNavigateProfile = { onNavigate(AppDestination.ONBOARDING_PROFILE) }, onNavigateOnboarding = { onNavigate(AppDestination.ONBOARDING) })
            AppDestination.ONBOARDING -> OnboardingPreferencesScreen(onBackToProfile = { onNavigate(AppDestination.ONBOARDING_PROFILE) }, onStartJourney = { _, _, _, _ -> onNavigate(AppDestination.HOME) }, onNavigateProfile = { onNavigate(AppDestination.ONBOARDING_PROFILE) }, onNavigateOnboarding = { onNavigate(AppDestination.ONBOARDING) })
            AppDestination.JOURNEY_SESSION -> MyJourneySessionScreen(onBack = { onNavigate(AppDestination.JOURNEY_DETAIL) }, onComplete = { onNavigate(AppDestination.JOURNEY_FEEDBACK) }, contentPadding = PaddingValues(0.dp))
            AppDestination.JOURNEY_FEEDBACK -> MyJourneyFeedbackScreen(onBack = { onNavigate(AppDestination.JOURNEY_SESSION) }, onNavigate = { dest -> if (dest == AppDestination.JOURNEY_DETAIL) onNavigate(AppDestination.JOURNEY_COMPLETED) else onNavigate(dest) }, contentPadding = PaddingValues(0.dp))
            AppDestination.JOURNEY_COMPLETED -> MyJourneyCompletedScreen(onBack = { onNavigate(AppDestination.JOURNEY_FEEDBACK) }, onNavigate = onNavigate, contentPadding = PaddingValues(0.dp))
            AppDestination.JOURNEY_LOOKING_FORWARD -> MyJourneyLookingForwardScreen(onBack = { onNavigate(AppDestination.JOURNEY_COMPLETED) }, onNavigate = { onNavigate(AppDestination.JOURNEY_SUPPORT) }, contentPadding = PaddingValues(0.dp))
            AppDestination.JOURNEY_SUPPORT -> MyJourneySupportScreen(onBack = { onNavigate(AppDestination.JOURNEY_LOOKING_FORWARD) }, onNavigate = onNavigate, contentPadding = PaddingValues(0.dp))
            else -> {}
        }
    }
}

@Preview(showBackground = true, name = "AppNavigation – ALL SCREENS (scroll) – like app")
@Composable
fun AppNavigationAllScreensPreview() {
    LampStandMobileTheme {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp)
        ) {
            PreviewSection("AUTH – Via Navigation (AppNavigation AUTH)") {
                Box(modifier = Modifier.fillMaxWidth().height(600.dp)) {
                    AppNavigation(initialDestination = AppDestination.AUTH, enableAuthSync = false)
                }
            }
            PreviewSection("AUTH – EMAIL") {
                AuthScreen(state = UiAuthState(email = "", currentStep = AuthStep.EMAIL), onAction = {})
            }
            PreviewSection("AUTH – EMAIL Filled") {
                AuthScreen(state = UiAuthState(email = "demo@lampstand.test", currentStep = AuthStep.EMAIL), onAction = {})
            }
            PreviewSection("AUTH – OTP") {
                AuthScreen(state = UiAuthState(email = "test@example.com", currentStep = AuthStep.OTP, code = listOf("1", "2", "", "")), onAction = {})
            }
            PreviewSection("AUTH – OTP Filled") {
                AuthScreen(state = UiAuthState(email = "demo@lampstand.test", currentStep = AuthStep.OTP, code = listOf("1", "2", "3", "4")), onAction = {})
            }
            PreviewSection("AUTH – Loading") {
                AuthScreen(state = UiAuthState(isLoading = true), onAction = {})
            }
            PreviewSection("AUTH – Error") {
                AuthScreen(state = UiAuthState(email = "demo@lampstand.test", currentStep = AuthStep.EMAIL, errorMessage = "Failed to send code. Please try again."), onAction = {})
            }
            PreviewSection("ONBOARDING – Profile") { OnboardingProfileScreen(onContinue = { _, _ -> }) }
            PreviewSection("ONBOARDING – Preferences") { OnboardingPreferencesScreen() }
            PreviewSection("HOME – Dashboard (active 3/7)") {
                DashboardContent(
                    state = DashboardState(homeState = mapOf("state" to "active", "pathTitle" to "Trust in Uncertainty", "currentDay" to 3, "totalDays" to 7, "todayDone" to false)),
                    onAction = {}, onNavigate = {}
                )
            }
            PreviewSection("PATHS") { PathsScreen(onNavigate = {}) }
            PreviewSection("PATH_DETAIL – CategoryPaths") { CategoryPathsScreen(onNavigate = {}) }
            PreviewSection("JOURNEY LIST – With Path") {
                MyJourneyListContent(
                    state = MyJourneyListState(paths = listOf(JourneyPathUi("1", "Trust in Uncertainty", "Grow in understanding God's character through these curated paths", 7, R.drawable.sailboat))),
                    onAction = {}, onNavigate = {}
                )
            }
            PreviewSection("JOURNEY LIST – Empty") {
                MyJourneyListContent(state = MyJourneyListState(paths = emptyList()), onAction = {}, onNavigate = {})
            }
            PreviewSection("JOURNEY DETAIL") { JourneyDetailScreen(onNavigate = {}) }
            PreviewSection("JOURNEY SESSION – 5 swipe") { MyJourneySessionScreen() }
            PreviewSection("JOURNEY FEEDBACK") {
                MyJourneyFeedbackContent(selected = FeedbackOption.HELPFUL, thoughts = "", onSelected = {}, onThoughts = {}, onBack = {}, onContinue = {})
            }
            PreviewSection("JOURNEY COMPLETED") {
                com.example.lampstandmobile.ui.journey.completed.MyJourneyCompletedContent(q1 = "I'm more grounded", q2 = null, q3 = null, thoughts = "", onQ1 = {}, onQ2 = {}, onQ3 = {}, onThoughts = {}, onBack = {}, onFinish = {})
            }
            PreviewSection("JOURNEY LOOKING FORWARD") { MyJourneyLookingForwardScreen(onBack = {}) }
            PreviewSection("JOURNEY SUPPORT") { MyJourneySupportScreen() }
            PreviewSection("PROFILE") { ProfileScreen(onNavigate = {}) }
            PreviewSection("PROFILE – Personal Information") { PersonalInformationScreen(onNavigate = {}) }
        }
    }
}

@Composable
private fun PreviewSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)) {
        Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        content()
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
    }
}

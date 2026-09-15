package com.example.lampstandmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lampstandmobile.data.convex.ConvexClientProvider
import com.example.lampstandmobile.navigation.AppDestination
import com.example.lampstandmobile.navigation.AppNavigation
import com.example.lampstandmobile.ui.auth.AuthScreen
import com.example.lampstandmobile.ui.auth.AuthState
import com.example.lampstandmobile.ui.auth.AuthStep
import com.example.lampstandmobile.ui.dashboard.DashboardContent
import com.example.lampstandmobile.ui.dashboard.DashboardState
import com.example.lampstandmobile.ui.journey.JourneyScreen
import com.example.lampstandmobile.ui.journey.completed.MyJourneyCompletedScreen
import com.example.lampstandmobile.ui.journey.detail.JourneyDetailScreen
import com.example.lampstandmobile.ui.journey.feedback.MyJourneyFeedbackScreen
import com.example.lampstandmobile.ui.journey.list.MyJourneyListContent
import com.example.lampstandmobile.ui.journey.list.MyJourneyListState
import com.example.lampstandmobile.ui.journey.list.JourneyPathUi
import com.example.lampstandmobile.ui.journey.lookingforward.MyJourneyLookingForwardScreen
import com.example.lampstandmobile.ui.journey.session.MyJourneySessionScreen
import com.example.lampstandmobile.ui.journey.support.MyJourneySupportScreen
import com.example.lampstandmobile.ui.onboarding.OnboardingPreferencesScreen
import com.example.lampstandmobile.ui.onboarding.OnboardingProfileScreen
import com.example.lampstandmobile.ui.paths.CategoryPathsScreen
import com.example.lampstandmobile.ui.paths.PathSupportScreen
import com.example.lampstandmobile.ui.paths.PathsScreen
import com.example.lampstandmobile.ui.profile.PersonalInformationScreen
import com.example.lampstandmobile.ui.profile.ProfileScreen
import com.example.lampstandmobile.ui.theme.LampStandMobileTheme
import com.example.lampstandmobile.R

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge()
        
        ConvexClientProvider.initialize(applicationContext)

        setContent {
            LampStandMobileTheme {
                AppNavigation()
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────
// Preview — ONE scrollable preview like the app - all screens from MainActivity
// Open MainActivity.kt → Split → Preview → Interactive to swipe through
// ─────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "MainActivity – ALL SCREENS (scroll)")
@Composable
private fun MainActivityAllScreensPreview() {
    LampStandMobileTheme {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp)
        ) {
            PreviewSection("AUTH – EMAIL") {
                AuthScreen(state = AuthState(email = "demo@lampstand.test", currentStep = AuthStep.EMAIL), onAction = {})
            }
            PreviewSection("AUTH – OTP Filled") {
                AuthScreen(state = AuthState(email = "demo@lampstand.test", currentStep = AuthStep.OTP, code = listOf("1", "2", "3", "4")), onAction = {})
            }
            PreviewSection("ONBOARDING – Profile") { OnboardingProfileScreen(onContinue = { _, _ -> }) }
            PreviewSection("ONBOARDING – Preferences") { OnboardingPreferencesScreen() }
            PreviewSection("HOME – Dashboard (active 3/7)") {
                DashboardContent(
                    state = DashboardState(homeState = mapOf("state" to "active", "pathTitle" to "Trust in Uncertainty", "currentDay" to 3, "totalDays" to 7, "todayDone" to false)),
                    onAction = {}, onNavigate = {}
                )
            }
            PreviewSection("HOME – Dashboard (no_path)") {
                DashboardContent(state = DashboardState(homeState = null), onAction = {}, onNavigate = {})
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
                com.example.lampstandmobile.ui.journey.feedback.MyJourneyFeedbackContent(selected = com.example.lampstandmobile.ui.journey.feedback.FeedbackOption.HELPFUL, thoughts = "", onSelected = {}, onThoughts = {}, onBack = {}, onContinue = {})
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

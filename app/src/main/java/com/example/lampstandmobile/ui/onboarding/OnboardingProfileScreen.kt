package com.example.lampstandmobile.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lampstandmobile.R
import com.example.lampstandmobile.ui.components.ui.Input
import com.example.lampstandmobile.ui.theme.LampStandMobileTheme

@Composable
fun OnboardingProfileScreen(
    onContinue: (String, String) -> Unit = { _, _ -> },
    onNavigateProfile: () -> Unit = {},
    onNavigateOnboarding: () -> Unit = {}
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    OnboardingProfileContent(
        firstName = firstName, lastName = lastName, isLoading = isLoading, error = error,
        onFirstName = { firstName = it }, onLastName = { lastName = it },
        onContinue = {
            if (firstName.isBlank() || lastName.isBlank()) { error = "Please fill in both names"; return@OnboardingProfileContent }
            isLoading = true
            onContinue(firstName.trim(), lastName.trim())
        },
        onNavigateProfile = onNavigateProfile, onNavigateOnboarding = onNavigateOnboarding
    )
}

@Composable
fun OnboardingProfileContent(
    firstName: String, lastName: String, isLoading: Boolean, error: String?,
    onFirstName: (String) -> Unit, onLastName: (String) -> Unit, onContinue: () -> Unit,
    onNavigateProfile: () -> Unit, onNavigateOnboarding: () -> Unit
) {
    val isPreview = LocalInspectionMode.current
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFDFDFD)).verticalScroll(rememberScrollState()).padding(horizontal = 28.dp).padding(top = 28.dp, bottom = 48.dp)) {
        // Dev Preview Banner like web onboarding/page.tsx:42
        if (isPreview) {
            RowDevPreview(onNavigateProfile, onNavigateOnboarding, active = 0)
            Spacer(modifier = Modifier.height(12.dp))
        }
        Image(painter = painterResource(R.drawable.logo), contentDescription = "Lampstand", modifier = Modifier.size(60.dp).padding(start = 0.dp))
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Complete Your Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF335E78))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Please fill in the information below to create your account", fontSize = 16.sp, color = Color(0xFF535353), lineHeight = 22.sp, modifier = Modifier.padding(bottom = 32.dp))
        Input(label = "First Name", placeholder = "Enter First Name", value = firstName, onValueChange = onFirstName, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text))
        Spacer(modifier = Modifier.height(16.dp))
        Input(label = "Last Name", placeholder = "Enter Last Name", value = lastName, onValueChange = onLastName, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text))
        error?.let { Text(text = it, color = Color.Red, fontSize = 14.sp, modifier = Modifier.padding(top = 12.dp)) }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onContinue, modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF335E78), contentColor = Color.White, disabledContainerColor = Color(0xFFE2E8F0)),
            enabled = firstName.isNotBlank() && lastName.isNotBlank() && !isLoading
        ) { Text(text = if (isLoading) "Saving…" else "Continue", fontSize = 16.sp, fontWeight = FontWeight.SemiBold) }
    }
}

@Composable
private fun RowDevPreview(onProfile: () -> Unit, onOnboarding: () -> Unit, active: Int) {
    Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(Color(0xFFFFF8E1)).border(width = 1.dp, color = Color(0xFFFFE082), shape = RoundedCornerShape(8.dp)).padding(12.dp)) {
        Text(text = "🛠️ Dev Preview Mode", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF335E78))
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.foundation.layout.Row {
            Text(text = "Step 1: Profile", fontSize = 12.sp, fontWeight = if (active == 0) FontWeight.Bold else FontWeight.Normal, color = if (active == 0) Color(0xFF335E78) else Color(0xFF535353), modifier = Modifier.padding(end = 16.dp).clickableNoRipple(onProfile))
            Text(text = "Step 2: Onboarding", fontSize = 12.sp, fontWeight = if (active == 1) FontWeight.Bold else FontWeight.Normal, color = if (active == 1) Color(0xFF335E78) else Color(0xFF535353), modifier = Modifier.clickableNoRipple(onOnboarding))
        }
    }
}

@Composable
private fun Modifier.clickableNoRipple(onClick: () -> Unit): Modifier = this.then(
    clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClick)
)

@Preview(showBackground = true, name = "Onboarding Profile")
@Composable
fun OnboardingProfilePreview() {
    LampStandMobileTheme { OnboardingProfileContent(firstName = "", lastName = "", isLoading = false, error = null, onFirstName = {}, onLastName = {}, onContinue = {}, onNavigateProfile = {}, onNavigateOnboarding = {}) }
}

@Preview(showBackground = true, name = "Onboarding Profile - Filled")
@Composable
fun OnboardingProfileFilledPreview() {
    LampStandMobileTheme { OnboardingProfileContent(firstName = "John", lastName = "Doe", isLoading = false, error = null, onFirstName = {}, onLastName = {}, onContinue = {}, onNavigateProfile = {}, onNavigateOnboarding = {}) }
}

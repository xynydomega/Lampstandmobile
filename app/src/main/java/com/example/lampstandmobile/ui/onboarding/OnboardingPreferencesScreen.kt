package com.example.lampstandmobile.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lampstandmobile.R
import com.example.lampstandmobile.ui.theme.LampStandMobileTheme

private val Primary = Color(0xFF335E78)
private val Bg = Color(0xFFFDFDFD)

@Composable
fun OnboardingPreferencesScreen(
    onBackToProfile: () -> Unit = {},
    onStartJourney: (String, String, String, String?) -> Unit = { _, _, _, _ -> },
    onNavigateProfile: () -> Unit = {},
    onNavigateOnboarding: () -> Unit = {}
) {
    var selectedSeason by remember { mutableStateOf("struggling_to_trust") }
    var dailyTime by remember { mutableStateOf("early_morning") }
    var contact by remember { mutableStateOf("email") }
    var phone by remember { mutableStateOf("") }

    OnboardingPreferencesContent(
        selectedSeason = selectedSeason, dailyTime = dailyTime, contact = contact, phone = phone,
        onSeason = { selectedSeason = it }, onDaily = { dailyTime = it }, onContact = { contact = it }, onPhone = { phone = it.filter { c -> c.isDigit() } },
        onStart = { onStartJourney(selectedSeason, dailyTime, contact, if (contact != "email") "+234$phone" else null) },
        onNavigateProfile = onNavigateProfile, onNavigateOnboarding = onNavigateOnboarding
    )
}

@Composable
fun OnboardingPreferencesContent(
    selectedSeason: String, dailyTime: String, contact: String, phone: String,
    onSeason: (String) -> Unit, onDaily: (String) -> Unit, onContact: (String) -> Unit, onPhone: (String) -> Unit,
    onStart: () -> Unit, onNavigateProfile: () -> Unit, onNavigateOnboarding: () -> Unit
) {
    val isPreview = LocalInspectionMode.current
    Column(modifier = Modifier.fillMaxSize().background(Bg).verticalScroll(rememberScrollState()).padding(horizontal = 28.dp).padding(top = 28.dp, bottom = 48.dp)) {
        if (isPreview) {
            RowDevBanner(onNavigateProfile, onNavigateOnboarding, 1)
            Spacer(modifier = Modifier.height(12.dp))
        }
        Image(painter = painterResource(R.drawable.logo), contentDescription = null, modifier = Modifier.size(60.dp))
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Where are you right now?", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, lineHeight = 24.sp, color = Color(0xFF335E78))
        Text(text = "Choose the season that feels most true for you today.", fontSize = 12.sp, color = Color(0xFF535353), modifier = Modifier.padding(bottom = 16.dp))
        SeasonCard(selected = selectedSeason == "struggling_to_trust", onClick = { onSeason("struggling_to_trust") }, label = "Struggling to trust God", icon = "🌊")
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
            Box(modifier = Modifier.weight(1f).height(1.dp).background(Color(0xFFE0E0E0)))
            Text(text = "COMING SOON", fontSize = 11.sp, letterSpacing = 1.sp, color = Color(0xFF92ADBE), modifier = Modifier.padding(horizontal = 12.dp))
            Box(modifier = Modifier.weight(1f).height(1.dp).background(Color(0xFFE0E0E0)))
        }
        listOf("uncertain_future" to "Uncertain about the future 🌫️", "pressure_stress" to "Feeling pressure & stress ⚖️", "waiting" to "In a season of waiting ⏳", "distant_from_god" to "Feeling distant from God 🌑").forEach { (id, label) ->
            Box(modifier = Modifier.fillMaxWidth().height(40.dp).clip(RoundedCornerShape(4.dp)).background(Color(0xFFF5F5F5)).border(width = 0.5.dp, color = Color(0xFFE0E0E0), shape = RoundedCornerShape(4.dp)).padding(horizontal = 20.dp), contentAlignment = Alignment.CenterStart) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text(text = label, fontSize = 12.sp, color = Color(0xFFB3B3B3))
                    Text(text = "soon", fontSize = 10.sp, color = Color(0xFFBBBBBB))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "When would you like to do your daily session?", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, lineHeight = 24.sp, color = Color(0xFF335E78))
        Text(text = "We’ll send you a gentle reminder at this time each day.", fontSize = 12.sp, color = Color(0xFF535353), modifier = Modifier.padding(bottom = 16.dp))
        listOf("early_morning" to Pair("Before the day begins", "sunrise"), "afternoon" to Pair("A midday pause", "sun"), "evening" to Pair("When things slow down", "moon"), "before_bed" to Pair("A quiet close to the day", "crescent")).forEach { (id, pair) ->
            val isSelected = dailyTime == id
            Row(modifier = Modifier.fillMaxWidth().height(54.dp).clip(RoundedCornerShape(4.dp)).background(if (isSelected) Color(0xFFEEF4F8) else Color(0xFFFCFCFC)).border(width = 0.5.dp, color = if (isSelected) Primary else Color(0xFFB3B3B3), shape = RoundedCornerShape(4.dp)).clickable { onDaily(id) }.padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(modifier = Modifier.size(24.dp).background(Color(0xFFE8E8E8), CircleShape), contentAlignment = Alignment.Center) { Text(text = "◐", fontSize = 12.sp) }
                    Column { Text(text = pair.first, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF335E78)); Text(text = pair.second, fontSize = 11.sp, color = Color(0xFF535353)) }
                }
                Icon(imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked, contentDescription = null, tint = if (isSelected) Primary else Color(0xFFB3B3B3), modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "How would you like us to keep in touch?", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, lineHeight = 24.sp, color = Color(0xFF335E78))
        Text(text = "We only reach out when it matters — never spam.", fontSize = 12.sp, color = Color(0xFF535353), modifier = Modifier.padding(bottom = 16.dp))
        listOf("email" to "Email", "whatsapp" to "WhatsApp", "both" to "Both").forEach { (id, label) ->
            val isSelected = contact == id
            Row(modifier = Modifier.fillMaxWidth().height(40.dp).clip(RoundedCornerShape(4.dp)).background(if (isSelected) Color(0xFFEEF4F8) else Color(0xFFFCFCFC)).border(width = 0.5.dp, color = if (isSelected) Primary else Color(0xFFB3B3B3), shape = RoundedCornerShape(4.dp)).clickable { onContact(id) }.padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = label, fontSize = 12.sp, color = Color(0xFF335E78))
                Icon(imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked, contentDescription = null, tint = if (isSelected) Primary else Color(0xFFB3B3B3), modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (contact == "whatsapp" || contact == "both") {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Phone Number", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Primary, modifier = Modifier.padding(bottom = 8.dp))
            Row(modifier = Modifier.fillMaxWidth().height(52.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFFFCFCFC)).border(width = 1.dp, color = Color(0xFFE5E7EB), shape = RoundedCornerShape(8.dp)).padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "🇳🇬 +234", fontSize = 14.sp, color = Color(0xFF335E78), modifier = Modifier.padding(end = 8.dp))
                Box(modifier = Modifier.width(1.dp).height(24.dp).background(Color(0xFFE5E7EB)))
                OutlinedTextField(value = phone, onValueChange = onPhone, placeholder = { Text(text = "+234", fontSize = 14.sp, color = Color(0xFFB3B3B3)) }, modifier = Modifier.weight(1f).padding(start = 12.dp), singleLine = true, colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedBorderColor = Color.Transparent, unfocusedBorderColor = Color.Transparent))
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onStart, modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(containerColor = Primary, contentColor = Color.White), enabled = selectedSeason.isNotBlank() && dailyTime.isNotBlank() && contact.isNotBlank() && (contact == "email" || phone.isNotBlank())) {
            Text(text = "Start My Journey", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun RowDevBanner(onProfile: () -> Unit, onOnboarding: () -> Unit, active: Int) {
    Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(Color(0xFFFFF8E1)).border(width = 1.dp, color = Color(0xFFFFE082), shape = RoundedCornerShape(8.dp)).padding(12.dp)) {
        Text(text = "🛠️ Dev Preview Mode", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Primary)
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(text = "Step 1: Profile", fontSize = 12.sp, fontWeight = if (active == 0) FontWeight.Bold else FontWeight.Normal, color = if (active == 0) Primary else Color(0xFF535353), modifier = Modifier.padding(end = 16.dp).clickableNoRipple(onProfile))
            Text(text = "Step 2: Onboarding", fontSize = 12.sp, fontWeight = if (active == 1) FontWeight.Bold else FontWeight.Normal, color = if (active == 1) Primary else Color(0xFF535353), modifier = Modifier.clickableNoRipple(onOnboarding))
        }
    }
}

@Composable
private fun SeasonCard(selected: Boolean, onClick: () -> Unit, label: String, icon: String) {
    Row(modifier = Modifier.fillMaxWidth().height(40.dp).clip(RoundedCornerShape(4.dp)).background(if (selected) Color(0xFFEEF4F8) else Color(0xFFFCFCFC)).border(width = 0.5.dp, color = if (selected) Primary else Color(0xFFB3B3B3), shape = RoundedCornerShape(4.dp)).clickable { onClick() }.padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = icon, fontSize = 16.sp)
            Text(text = label, fontSize = 12.sp, color = Color(0xFF335E78))
        }
        Icon(imageVector = if (selected) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked, contentDescription = null, tint = if (selected) Primary else Color(0xFFB3B3B3), modifier = Modifier.size(18.dp))
    }
}

@Composable
private fun Modifier.clickableNoRipple(onClick: () -> Unit): Modifier = this.then(clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClick))

@Preview(showBackground = true, name = "Onboarding Preferences")
@Composable
fun OnboardingPreferencesPreview() {
    LampStandMobileTheme { OnboardingPreferencesContent(selectedSeason = "struggling_to_trust", dailyTime = "early_morning", contact = "email", phone = "", onSeason = {}, onDaily = {}, onContact = {}, onPhone = {}, onStart = {}, onNavigateProfile = {}, onNavigateOnboarding = {}) }
}

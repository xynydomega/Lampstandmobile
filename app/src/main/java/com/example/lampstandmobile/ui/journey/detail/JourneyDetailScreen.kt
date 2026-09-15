package com.example.lampstandmobile.ui.journey.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lampstandmobile.R
import com.example.lampstandmobile.navigation.AppDestination
import com.example.lampstandmobile.ui.theme.LampStandMobileTheme

private val Primary = Color(0xFF335E78)
private val TitleColor = Color(0xFF184159)
private val TextColor = Color(0xFF535353)
private val LockedColor = Color(0xFFB3B3B3)
private val DividerColor = Color(0xFFE8E8E8)
private val HeroBackground = Color(0x26335E78)
private val DoneGreen = Color(0xFF2A7A5A)

@Composable
fun JourneyDetailScreen(
    onNavigate: (AppDestination) -> Unit = {},
    onBack: () -> Unit = { onNavigate(AppDestination.JOURNEY_LIST) },
    onContinueReading: () -> Unit = {},
    onSessionClick: (Int) -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val sessions = listOf(
        JourneySession(1, "Trusting God's Character", SessionStatus.AVAILABLE),
        JourneySession(2, "When Things Feel Unclear", SessionStatus.LOCKED),
        JourneySession(3, "Learning to Rest", SessionStatus.COMPLETED),
        JourneySession(4, "When Fear Takes Over", SessionStatus.LOCKED),
        JourneySession(5, "Trusting Through Uncertainty", SessionStatus.LOCKED),
        JourneySession(6, "God's Steady Presence", SessionStatus.LOCKED),
        JourneySession(7, "Resting in His Character", SessionStatus.LOCKED)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(contentPadding)
            .verticalScroll(rememberScrollState())
    ) {
        // Hero banner — web app/my-journey/[id]/page.tsx hero
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                .background(HeroBackground)
                .padding(top = 20.dp, bottom = 32.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { onBack() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Primary,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(R.drawable.sailboat),
                contentDescription = "Trust in Uncertainty",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 186.dp, height = 206.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White)
            )
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Trust in Uncertainty", fontSize = 20.sp, lineHeight = 24.sp, fontWeight = FontWeight.SemiBold, color = TitleColor)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Learn to rest in God's unchanging character even when the path ahead is unclear or unsettling.", fontSize = 11.sp, lineHeight = 15.sp, color = TextColor, modifier = Modifier.width(205.dp))
            }
        }

        // Continue Reading — web Continue Reading 275x42
        Box(modifier = Modifier.fillMaxWidth().padding(top = 0.dp), contentAlignment = Alignment.Center) {
            Row(
                modifier = Modifier.width(275.dp).height(42.dp).clip(RoundedCornerShape(8.dp)).background(Primary).clickable { onContinueReading() },
                horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Continue Reading", fontSize = 14.sp, lineHeight = 22.sp, fontWeight = FontWeight.Medium, color = Color.White)
                Spacer(modifier = Modifier.width(12.dp))
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
        }

        // Reading Schedule
        Column(modifier = Modifier.fillMaxWidth().padding(start = 19.dp, top = 24.dp)) {
            Text(text = "Reading Schedule", fontSize = 14.sp, lineHeight = 15.sp, fontWeight = FontWeight.SemiBold, color = TextColor)
            Spacer(modifier = Modifier.height(19.dp))
            Row(modifier = Modifier.horizontalScroll(rememberScrollState()).padding(end = 19.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val days = listOf("Mon" to "14", "Tue" to "15", "Wed" to "16", "Thu" to "17", "Fri" to "18", "Sat" to "19", "Sun" to "20")
                days.forEachIndexed { index, day ->
                    val isActive = index == 0
                    Column(
                        modifier = Modifier.size(width = 53.dp, height = 59.dp).clip(RoundedCornerShape(8.dp)).background(if (isActive) Color(0xFFD9D9D9) else Primary),
                        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = day.first, fontSize = 10.sp, color = if (isActive) Color(0xFF888888) else Color.White)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = day.second, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = if (isActive) Color(0xFF888888) else Color.White)
                    }
                }
            }
        }

        // Overview
        Column(modifier = Modifier.fillMaxWidth().padding(start = 19.dp, end = 19.dp, top = 24.dp)) {
            Text(text = "Overview", fontSize = 14.sp, lineHeight = 15.sp, fontWeight = FontWeight.SemiBold, color = TextColor)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "This 7-day path is designed to help you rely on God's steady presence and character when life feels unclear or unpredictable.", fontSize = 11.sp, lineHeight = 15.sp, color = TextColor)
        }

        // Sessions
        Column(modifier = Modifier.fillMaxWidth().padding(start = 19.dp, end = 19.dp, top = 24.dp, bottom = 16.dp)) {
            Text(text = "Sessions", fontSize = 14.sp, lineHeight = 15.sp, fontWeight = FontWeight.SemiBold, color = TextColor)
            Spacer(modifier = Modifier.height(16.dp))
            sessions.forEachIndexed { index, session ->
                val isLocked = session.status == SessionStatus.LOCKED
                Row(
                    modifier = Modifier.fillMaxWidth().padding(end = 24.dp, top = 4.dp, bottom = 4.dp).clickable(enabled = !isLocked) { onSessionClick(session.day) },
                    horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(11.dp), verticalAlignment = Alignment.CenterVertically) {
                        SessionStatusIcon(session.status)
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(text = "Session ${session.day}", fontSize = 11.sp, lineHeight = 15.sp, color = TextColor)
                            Text(text = session.title, fontSize = 12.sp, lineHeight = 15.sp, fontWeight = FontWeight.SemiBold, color = if (isLocked) LockedColor else Color(0xFF1E1E1E))
                            if (session.status == SessionStatus.COMPLETED) Text(text = "Completed", fontSize = 10.sp, color = DoneGreen)
                        }
                    }
                    when (session.status) {
                        SessionStatus.COMPLETED -> Box(modifier = Modifier.size(18.dp).clip(CircleShape).background(Color(0xFFE8F4F0)), contentAlignment = Alignment.Center) { Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = DoneGreen, modifier = Modifier.size(13.dp)) }
                        SessionStatus.AVAILABLE -> Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp).clip(CircleShape).background(Primary).padding(2.dp))
                        SessionStatus.LOCKED -> Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = LockedColor, modifier = Modifier.size(18.dp).clip(CircleShape).background(Color(0xFFD9D9D9)).padding(2.dp))
                    }
                }
                if (index < sessions.lastIndex) {
                    Spacer(modifier = Modifier.height(15.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(0.5.dp).background(DividerColor))
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

private enum class SessionStatus { COMPLETED, AVAILABLE, LOCKED }
private data class JourneySession(val day: Int, val title: String, val status: SessionStatus)

@Composable
private fun SessionStatusIcon(status: SessionStatus) {
    when (status) {
        SessionStatus.COMPLETED -> Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Primary, modifier = Modifier.size(12.dp))
        SessionStatus.AVAILABLE -> Text(text = "✳", fontSize = 12.sp, color = Primary)
        SessionStatus.LOCKED -> Text(text = "✳", fontSize = 12.sp, color = LockedColor)
    }
}

@Preview(showBackground = true, name = "Journey Detail")
@Composable
fun JourneyDetailPreview() {
    LampStandMobileTheme { JourneyDetailScreen() }
}

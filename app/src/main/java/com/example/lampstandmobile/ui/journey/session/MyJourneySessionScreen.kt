package com.example.lampstandmobile.ui.journey.session

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lampstandmobile.R
import com.example.lampstandmobile.ui.theme.LampStandMobileTheme

private val Primary = Color(0xFF335E78)
private val Bg = Color(0xFFFDFDFD)
private val TextBody = Color(0xFF535353)
private val Heading = Color(0xFF335E78)
private val DotActive = Color(0xFF335E78)
private val DotInactive = Color(0xFFB3B3B3)
private val LabelBlue = Color(0xFF7BA1B6)

// Single file — one screen — 5 horizontal sections UI-only 1:1 of session/page.tsx
@Composable
fun MyJourneySessionScreen(
    onBack: () -> Unit = {},
    onComplete: () -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var activeIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .padding(contentPadding)
    ) {
        // Horizontal pager — simplified snap via Row horizontalScroll + activeIndex
        // Web uses snap-x overflow-x-hidden; Compose uses Row + manual dots navigation
        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            when (activeIndex) {
                0 -> TodaysJourneySection(onBack = onBack, onNext = { activeIndex = 1 })
                1 -> ContextSection(onBack = { activeIndex = 0 }, onNext = { activeIndex = 2 })
                2 -> InsightSection(onBack = { activeIndex = 1 }, onNext = { activeIndex = 3 })
                3 -> ApplicationSection(onBack = { activeIndex = 2 }, onNext = { activeIndex = 4 })
                4 -> GuidedPrayerSection(onBack = { activeIndex = 3 }, onComplete = onComplete)
            }
        }
        // Dots + arrows footer is inside each section per web; no extra footer here
    }
}

@Composable
private fun TodaysJourneySection(onBack: () -> Unit, onNext: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Bg)) {
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 21.dp).padding(top = 24.dp, bottom = 100.dp)) {
            Box(modifier = Modifier.size(30.dp).clip(CircleShape).background(Color.White).clickable { onBack() }, contentAlignment = Alignment.Center) {
                Icon(painter = painterResource(R.drawable.logo), contentDescription = "Back", tint = Primary, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(modifier = Modifier.fillMaxWidth().height(166.dp).clip(RoundedCornerShape(10.dp)).background(Color(0xFFE8E8E8)), contentAlignment = Alignment.Center) {
                Image(painter = painterResource(R.drawable.sailboat), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            }
            Spacer(modifier = Modifier.height(18.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Box(modifier = Modifier.size(12.dp).background(LabelBlue, CircleShape))
                Text(text = "TODAY'S JOURNEY", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp, color = LabelBlue)
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "Finding Stillness", fontSize = 24.sp, fontWeight = FontWeight.SemiBold, lineHeight = 33.sp, color = Heading, modifier = Modifier.padding(bottom = 12.dp))
            Text(text = "Finding Stillness invites you to quiet your heart, slow your mind, and become aware of God's steady presence in the midst of life's noise.", fontSize = 14.sp, lineHeight = 22.sp, color = TextBody, modifier = Modifier.padding(bottom = 24.dp))
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(6.dp)).background(Color(0xFFF3F7FA)).border(width = 0.dp, color = LabelBlue, shape = RoundedCornerShape(6.dp)).padding(start = 4.dp).background(Color.Transparent).padding(start = 12.dp, top = 15.dp, bottom = 15.dp, end = 16.dp)) {
                Column {
                    Text(text = "CORE SCRIPTURE", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp, color = Heading, modifier = Modifier.padding(bottom = 9.dp))
                    Text(text = "\"Be still, and know that I am God; I will be exalted among the nations, I will be exalted in the earth.\"", fontSize = 12.sp, lineHeight = 18.sp, color = TextBody, modifier = Modifier.padding(bottom = 6.dp))
                    Text(text = "Psalm 46:10 (NIV)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Primary)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "When God says, \"Be still,\" He is inviting you to release your grip on outcomes, on worries, on the need to figure everything out. Stillness is an act of trust.", fontSize = 14.sp, lineHeight = 22.sp, color = TextBody)
        }
        SessionFooter(activeIndex = 0, onDot = { }, onLeft = {}, onRight = onNext)
    }
}

@Composable
private fun ContextSection(onBack: () -> Unit, onNext: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Bg)) {
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 21.dp).padding(top = 24.dp, bottom = 100.dp)) {
            Text(text = "CONTEXT", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp, color = Color(0xFF92ADBE))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Historical & Biblical Background", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF184159), modifier = Modifier.padding(bottom = 18.dp))
            Text(text = "Psalm 46 was written in a time when God's people were familiar with national turmoil and instability of life in ancient Near East. This psalm belongs to sons of Korah...", fontSize = 12.sp, lineHeight = 18.sp, color = TextBody, modifier = Modifier.padding(bottom = 18.dp))
            Text(text = "The command \"Be still\" in Hebrew (raphah) means to loosen, to let go, to relax your grip. God speaks into fear calling His people not to panic...", fontSize = 12.sp, lineHeight = 18.sp, color = TextBody)
        }
        SessionFooter(activeIndex = 1, onDot = { if (it < 1) onBack() else onNext() }, onLeft = onBack, onRight = onNext)
    }
}

@Composable
private fun InsightSection(onBack: () -> Unit, onNext: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Bg)) {
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 21.dp).padding(top = 24.dp, bottom = 100.dp)) {
            Text(text = "CHARACTER OF GOD INSIGHT", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp, color = Color(0xFF92ADBE))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "What this Reveals About God", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF184159))
            Spacer(modifier = Modifier.height(18.dp))
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(4.dp)).background(Color(0x1A335E78)).padding(start = 3.dp).background(Color.Transparent).padding(16.dp)) {
                Column {
                    Text(text = "Core Insight", fontSize = 11.sp, fontStyle = FontStyle.Italic, color = Primary, modifier = Modifier.padding(bottom = 6.dp))
                    Text(text = "God is Compassionate & Gracious.", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF184159))
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            Text(text = "The Hebrew word 'rachum' signifies deep parental-like womb-compassion. God's primary posture is tender care...", fontSize = 12.sp, lineHeight = 18.sp, color = TextBody)
        }
        SessionFooter(activeIndex = 2, onDot = { }, onLeft = onBack, onRight = onNext)
    }
}

@Composable
private fun ApplicationSection(onBack: () -> Unit, onNext: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Bg)) {
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 21.dp).padding(top = 24.dp, bottom = 100.dp)) {
            Text(text = "PERSONAL APPLICATION", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp, color = Color(0xFF92ADBE))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Formation Questions", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF184159), modifier = Modifier.padding(bottom = 20.dp))
            repeat(3) { idx ->
                val qs = listOf("What area of your life feels most uncertain right now?", "How do you usually respond when you don't have control?", "What would it look like to trust God in this situation?")
                Text(text = qs[idx], fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF184159))
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.fillMaxWidth().height(63.dp).clip(RoundedCornerShape(4.dp)).border(0.5.dp, Color(0x4D335E78), RoundedCornerShape(4.dp)).background(Color.White).padding(14.dp)) {
                    Text(text = "Your thoughts..", fontSize = 11.sp, color = Color(0xFFB3B3B3))
                }
                Text(text = "Write your thoughts (optional)", fontSize = 12.sp, fontStyle = FontStyle.Italic, color = Color(0xFF828282), modifier = Modifier.padding(top = 6.dp, bottom = 20.dp))
            }
        }
        // Footer with button per web ApplicationSection
        Column(modifier = Modifier.fillMaxWidth().background(Bg).border(width = 1.dp, color = Color(0xFFF1F5F9)).padding(horizontal = 21.dp, vertical = 16.dp)) {
            SessionFooter(activeIndex = 3, onDot = {}, onLeft = onBack, onRight = onNext, showButton = false)
            Spacer(modifier = Modifier.height(12.dp))
            Box(modifier = Modifier.fillMaxWidth().height(42.dp).clip(RoundedCornerShape(8.dp)).background(Primary).clickable { onNext() }, contentAlignment = Alignment.Center) {
                Text(text = "Continue to Prayer", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White)
            }
        }
    }
}

@Composable
private fun GuidedPrayerSection(onBack: () -> Unit, onComplete: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Bg)) {
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 21.dp).padding(top = 24.dp, bottom = 100.dp)) {
            Text(text = "GUIDED PRAYER", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp, color = Color(0xFF92ADBE))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Talk to God", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF184159), modifier = Modifier.padding(bottom = 18.dp))
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(4.dp)).background(Color(0x1A335E78)).padding(16.dp)) {
                Column {
                    Text(text = "Guided Prayer", fontSize = 11.sp, fontStyle = FontStyle.Italic, color = Primary, modifier = Modifier.padding(bottom = 6.dp))
                    Text(text = "Father, thank You for being my refuge when life feels loud and overwhelming. Teach me what it truly means to be still before You.", fontSize = 12.sp, lineHeight = 16.sp, color = TextBody)
                }
            }
        }
        Column(modifier = Modifier.fillMaxWidth().background(Bg).border(width = 1.dp, color = Color(0xFFF1F5F9)).padding(horizontal = 21.dp, vertical = 16.dp)) {
            SessionFooter(activeIndex = 4, onDot = {}, onLeft = onBack, onRight = {}, showRight = false)
            Spacer(modifier = Modifier.height(12.dp))
            Box(modifier = Modifier.fillMaxWidth().height(42.dp).clip(RoundedCornerShape(8.dp)).background(Primary).clickable { onComplete() }, contentAlignment = Alignment.Center) {
                Text(text = "Complete Session", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White)
            }
        }
    }
}

@Composable
private fun SessionFooter(activeIndex: Int, onDot: (Int) -> Unit = {}, onLeft: () -> Unit = {}, onRight: () -> Unit = {}, showRight: Boolean = true, showLeft: Boolean = true, showButton: Boolean = true) {
    Row(modifier = Modifier.fillMaxWidth().background(Bg).padding(horizontal = 21.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        if (showLeft) Box(modifier = Modifier.size(30.dp).clickable { onLeft() }, contentAlignment = Alignment.Center) {
            Text(text = "‹", fontSize = 24.sp, color = Primary)
        } else Spacer(modifier = Modifier.width(30.dp))
        Spacer(modifier = Modifier.weight(1f))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
            repeat(5) { idx ->
                Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(if (idx == activeIndex) DotActive else DotInactive).clickable { onDot(idx) })
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        if (showRight) Box(modifier = Modifier.size(30.dp).clickable { onRight() }, contentAlignment = Alignment.Center) {
            Text(text = "›", fontSize = 24.sp, color = Primary)
        } else Spacer(modifier = Modifier.width(30.dp))
    }
}

@Preview(showBackground = true, name = "MyJourney Session")
@Composable
fun MyJourneySessionPreview() {
    LampStandMobileTheme { MyJourneySessionScreen() }
}

@Preview(showBackground = true, name = "MyJourney Session - Insight")
@Composable
fun MyJourneySessionInsightPreview() {
    LampStandMobileTheme {
        Column(modifier = Modifier.fillMaxSize().background(Bg)) { InsightSection(onBack = {}, onNext = {}) }
    }
}

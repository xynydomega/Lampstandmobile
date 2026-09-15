package com.example.lampstandmobile.ui.journey.completed

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lampstandmobile.navigation.AppDestination
import com.example.lampstandmobile.ui.theme.LampStandMobileTheme

private val Primary = Color(0xFF335E78)
private val TitleBlue = Color(0xFF184159)
private val Bg = Color(0xFFFDFDFD)

@Composable
fun MyJourneyCompletedScreen(
    onBack: () -> Unit = {},
    onFinish: () -> Unit = {},
    onNavigate: (AppDestination) -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var q1 by remember { mutableStateOf<String?>(null) }
    var q2 by remember { mutableStateOf<String?>(null) }
    var q3 by remember { mutableStateOf<String?>(null) }
    var thoughts by remember { mutableStateOf("") }

    MyJourneyCompletedContent(
        q1 = q1, q2 = q2, q3 = q3, thoughts = thoughts,
        onQ1 = { q1 = it }, onQ2 = { q2 = it }, onQ3 = { q3 = it }, onThoughts = { thoughts = it },
        onBack = onBack, onFinish = { onFinish(); onNavigate(AppDestination.JOURNEY_DETAIL) },
        contentPadding = contentPadding
    )
}

@Composable
fun MyJourneyCompletedContent(
    q1: String?, q2: String?, q3: String?, thoughts: String,
    onQ1: (String) -> Unit, onQ2: (String) -> Unit, onQ3: (String) -> Unit, onThoughts: (String) -> Unit,
    onBack: () -> Unit, onFinish: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val q1Options = listOf("I'm more grounded", "Much deeper than anything I've used before", "I understand God more", "I'm not sure", "I don't notice a difference")
    val q2Options = listOf("Much deeper than anything I've used before", "Similar to other things I've tried", "Not quite what I expected", "This is relatively new for me")
    val q3Options = listOf("Start another formation path", "I need a break — I'll come back", "I'm not sure yet")

    Column(modifier = Modifier.fillMaxSize().background(Bg).padding(contentPadding).verticalScroll(rememberScrollState()).padding(horizontal = 21.dp).padding(top = 24.dp, bottom = 100.dp)) {
        Box(modifier = Modifier.size(24.dp).clickable { onBack() }, contentAlignment = Alignment.Center) {
            Text(text = "‹", fontSize = 24.sp, color = Primary)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 16.dp)) {
            Box(modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(Primary).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text(text = "JOURNEY COMPLETE", fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp, color = Color.White)
            }
            Text(text = "DAY 7/7", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TitleBlue)
        }
        Text(text = "You've completed your first journey.", fontSize = 24.sp, fontWeight = FontWeight.SemiBold, lineHeight = 32.sp, color = TitleBlue, modifier = Modifier.padding(bottom = 8.dp))
        Text(text = "We'd love to hear from you. This takes under two minutes.", fontSize = 14.sp, lineHeight = 18.sp, color = Color(0xFF535353), modifier = Modifier.padding(bottom = 32.dp))

        Text(text = "After 7 days, which of these feels most true for you?", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, lineHeight = 20.sp, color = TitleBlue, modifier = Modifier.padding(bottom = 12.dp))
        RenderOptions(q1Options, q1, onQ1, modifier = Modifier.padding(bottom = 32.dp))
        Text(text = "How does Lampstand compare with other ways you've engaged with scripture?", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, lineHeight = 20.sp, color = TitleBlue, modifier = Modifier.padding(bottom = 12.dp))
        RenderOptions(q2Options, q2, onQ2, modifier = Modifier.padding(bottom = 32.dp))
        Text(text = "What would you like to do next?", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, lineHeight = 20.sp, color = TitleBlue, modifier = Modifier.padding(bottom = 12.dp))
        RenderOptions(q3Options, q3, onQ3, modifier = Modifier.padding(bottom = 32.dp))

        Text(text = "Is there anything you'd like to share about your experience — what helped, what didn't, or what you're still carrying?", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, lineHeight = 20.sp, color = TitleBlue, modifier = Modifier.padding(bottom = 12.dp))
        OutlinedTextField(value = thoughts, onValueChange = onThoughts, placeholder = { Text(text = "Share my thoughts...", fontSize = 12.sp, color = Color(0xFFB3B3B3)) }, modifier = Modifier.fillMaxWidth().height(113.dp).padding(bottom = 32.dp), shape = RoundedCornerShape(4.dp), colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White, focusedBorderColor = Color(0xFFE2E8F0), unfocusedBorderColor = Color(0xFFE2E8F0)))
        Box(modifier = Modifier.fillMaxWidth().height(48.dp).clip(RoundedCornerShape(8.dp)).background(Primary).clickable { onFinish() }, contentAlignment = Alignment.Center) {
            Text(text = "Finish", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White)
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun RenderOptions(options: List<String>, selected: String?, onSelect: (String) -> Unit, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier) {
        options.forEach { opt ->
            val isSelected = selected == opt
            Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(4.dp)).background(Color.White).border(width = 1.dp, color = Color(0xFFB3B3B3), shape = RoundedCornerShape(4.dp)).clickable { onSelect(opt) }.padding(horizontal = 16.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = opt, fontSize = 14.sp, color = TitleBlue, modifier = Modifier.weight(1f).padding(end = 12.dp))
                Icon(imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked, contentDescription = null, tint = if (isSelected) Primary else Color(0xFFB3B3B3), modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Preview(showBackground = true, name = "MyJourney Completed")
@Composable
fun MyJourneyCompletedPreview() {
    LampStandMobileTheme { MyJourneyCompletedContent(q1 = "I'm more grounded", q2 = null, q3 = null, thoughts = "", onQ1 = {}, onQ2 = {}, onQ3 = {}, onThoughts = {}, onBack = {}, onFinish = {}) }
}

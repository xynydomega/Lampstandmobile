package com.example.lampstandmobile.ui.journey.feedback

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Icon
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults

private val Primary = Color(0xFF335E78)
private val Bg = Color(0xFFFDFDFD)
private val LabelBlue = Color(0xFF92ADBE)
private val TitleBlue = Color(0xFF184159)
private val BorderGrey = Color(0xFFB3B3B3)

enum class FeedbackOption(val label: String, val sublabel: String) {
    DIDNT_CONNECT("Didn't connect", "It wasn't what I needed today"),
    HELPFUL("It was helpful", "It provided some quiet clarity"),
    LANDED_DEEPLY("It landed deeply", "Exactly what my soul needed")
}

@Composable
fun MyJourneyFeedbackScreen(
    onBack: () -> Unit = {},
    onContinue: (String?, String) -> Unit = { _, _ -> },
    onNavigate: (AppDestination) -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var selected by remember { mutableStateOf<FeedbackOption?>(null) }
    var thoughts by remember { mutableStateOf("") }

    MyJourneyFeedbackContent(
        selected = selected,
        thoughts = thoughts,
        onSelected = { selected = it },
        onThoughts = { thoughts = it },
        onBack = onBack,
        onContinue = { onContinue(selected?.name, thoughts); onNavigate(AppDestination.JOURNEY_DETAIL) },
        contentPadding = contentPadding
    )
}

@Composable
fun MyJourneyFeedbackContent(
    selected: FeedbackOption?,
    thoughts: String,
    onSelected: (FeedbackOption) -> Unit,
    onThoughts: (String) -> Unit,
    onBack: () -> Unit,
    onContinue: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Bg).padding(contentPadding).verticalScroll(rememberScrollState()).padding(horizontal = 21.dp).padding(top = 24.dp, bottom = 20.dp)
    ) {
        Box(modifier = Modifier.size(30.dp).clip(CircleShape).background(Color.White).clickable { onBack() }, contentAlignment = Alignment.Center) {
            Icon(painter = androidx.compose.ui.res.painterResource(com.example.lampstandmobile.R.drawable.logo), contentDescription = "Back", tint = Primary, modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(bottom = 5.dp)) {
            Box(modifier = Modifier.size(12.dp).background(LabelBlue, CircleShape))
            Text(text = "HOW DID IT GO?", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp, color = LabelBlue)
        }
        Text(text = "How did today's session land for you?", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, lineHeight = 26.sp, color = TitleBlue, modifier = Modifier.padding(bottom = 42.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.padding(bottom = 32.dp)) {
            FeedbackOption.entries.forEach { option ->
                val isSelected = selected == option
                Row(
                    modifier = Modifier.fillMaxWidth().height(59.dp).clip(RoundedCornerShape(4.dp)).background(Color.White).border(width = 0.5.dp, color = BorderGrey, shape = RoundedCornerShape(4.dp)).clickable { onSelected(option) }.padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Box(modifier = Modifier.size(33.dp).clip(RoundedCornerShape(4.dp)).background(Color(0xFFE8E8E8)), contentAlignment = Alignment.Center) {
                            Text(text = when (option) { FeedbackOption.DIDNT_CONNECT -> "☹"; FeedbackOption.HELPFUL -> "☺"; FeedbackOption.LANDED_DEEPLY -> "♥" }, fontSize = 18.sp)
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = option.label, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Primary)
                            Text(text = option.sublabel, fontSize = 12.sp, color = Color(0xFF535353))
                        }
                    }
                    Icon(imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked, contentDescription = null, tint = if (isSelected) Primary else Color(0xFFB3B3B3), modifier = Modifier.size(24.dp))
                }
            }
        }
        if (selected != null) {
            Text(text = "Is there anything else you want to tell us about today? (Optional)", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, lineHeight = 18.sp, color = TitleBlue, modifier = Modifier.padding(bottom = 12.dp))
            OutlinedTextField(
                value = thoughts, onValueChange = onThoughts, placeholder = { Text(text = "Your thoughts..", fontSize = 12.sp, color = Color(0xFFB3B3B3)) },
                modifier = Modifier.fillMaxWidth().height(113.dp).padding(bottom = 40.dp), shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White, focusedBorderColor = Color(0x4D335E78), unfocusedBorderColor = Color(0x4D335E78))
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(modifier = Modifier.fillMaxWidth().height(42.dp).clip(RoundedCornerShape(8.dp)).background(Primary).clickable { onContinue() }, contentAlignment = Alignment.Center) {
            Text(text = "Continue to Completed", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White)
        }
    }
}

@Preview(showBackground = true, name = "MyJourney Feedback")
@Composable
fun MyJourneyFeedbackPreview() {
    LampStandMobileTheme { MyJourneyFeedbackContent(selected = FeedbackOption.HELPFUL, thoughts = "", onSelected = {}, onThoughts = {}, onBack = {}, onContinue = {}) }
}

@Preview(showBackground = true, name = "MyJourney Feedback - Empty")
@Composable
fun MyJourneyFeedbackEmptyPreview() {
    LampStandMobileTheme { MyJourneyFeedbackContent(selected = null, thoughts = "", onSelected = {}, onThoughts = {}, onBack = {}, onContinue = {}) }
}

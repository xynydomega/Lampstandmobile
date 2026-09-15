package com.example.lampstandmobile.ui.journey.lookingforward

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

private val Primary = Color(0xFF335E78)
private val TitleBlue = Color(0xFF184159)
private val Bg = Color(0xFFFDFDFD)

@Composable
fun MyJourneyLookingForwardScreen(
    onBack: () -> Unit = {},
    onSubmit: (List<String>) -> Unit = {},
    onNavigate: (AppDestination) -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var selected by remember { mutableStateOf(setOf<String>()) }
    MyJourneyLookingForwardContent(selected = selected, onToggle = { t -> selected = if (t in selected) selected - t else selected + t }, onBack = onBack, onSubmit = { onSubmit(selected.toList()); onNavigate(AppDestination.JOURNEY_DETAIL) }, contentPadding = contentPadding)
}

@Composable
fun MyJourneyLookingForwardContent(
    selected: Set<String>,
    onToggle: (String) -> Unit,
    onBack: () -> Unit,
    onSubmit: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val themes = listOf("Loss & Grief", "Identity & Worth", "Pressure & Endurance", "Relationships & Community")
    Column(modifier = Modifier.fillMaxSize().background(Bg).padding(contentPadding).verticalScroll(rememberScrollState()).padding(horizontal = 21.dp).padding(top = 24.dp, bottom = 100.dp)) {
        Box(modifier = Modifier.size(24.dp).clickable { onBack() }, contentAlignment = Alignment.Center) { Text(text = "‹", fontSize = 24.sp, color = Primary) }
        Spacer(modifier = Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(bottom = 5.dp)) {
            Box(modifier = Modifier.size(12.dp).background(Color(0xFF92ADBE), CircleShape))
            Text(text = "LOOKING FORWARD", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp, color = Color(0xFF92ADBE))
        }
        Text(text = "What themes would you like to explore next!", fontSize = 24.sp, fontWeight = FontWeight.SemiBold, lineHeight = 32.sp, color = TitleBlue, modifier = Modifier.padding(bottom = 24.dp))
        Text(text = "Please select as many as you would like", fontSize = 14.sp, lineHeight = 18.sp, color = Color(0xFF535353), modifier = Modifier.padding(bottom = 16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.padding(bottom = 40.dp)) {
            themes.forEach { theme ->
                val isSelected = theme in selected
                Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(4.dp)).background(Color.White).border(width = 1.dp, color = Color(0xFFB3B3B3), shape = RoundedCornerShape(4.dp)).clickable { onToggle(theme) }.padding(horizontal = 16.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = theme, fontSize = 14.sp, color = TitleBlue, modifier = Modifier.weight(1f))
                    Icon(imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked, contentDescription = null, tint = if (isSelected) Primary else Color(0xFFB3B3B3), modifier = Modifier.size(18.dp))
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth().height(48.dp).clip(RoundedCornerShape(8.dp)).background(Primary).clickable { onSubmit() }, contentAlignment = Alignment.Center) {
            Text(text = "Submit", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White)
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(showBackground = true, name = "MyJourney Looking Forward")
@Composable
fun MyJourneyLookingForwardPreview() {
    LampStandMobileTheme { MyJourneyLookingForwardContent(selected = setOf("Loss & Grief", "Pressure & Endurance"), onToggle = {}, onBack = {}, onSubmit = {}) }
}

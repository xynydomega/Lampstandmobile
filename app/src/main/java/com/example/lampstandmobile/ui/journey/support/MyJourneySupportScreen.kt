package com.example.lampstandmobile.ui.journey.support

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lampstandmobile.navigation.AppDestination
import com.example.lampstandmobile.ui.theme.LampStandMobileTheme

private val Primary = Color(0xFF335E78)
private val TitleBlue = Color(0xFF1E3F57)
private val Bg = Color.White

@Composable
fun MyJourneySupportScreen(
    onBack: () -> Unit = {},
    onMaybeLater: () -> Unit = {},
    onNavigate: (AppDestination) -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var currency by remember { mutableStateOf("USD") }
    var selectedOneTime by remember { mutableStateOf<Int?>(null) }
    var customOneTime by remember { mutableStateOf("") }
    var selectedMonthly by remember { mutableStateOf<String?>(null) }
    var customMonthly by remember { mutableStateOf("") }

    MyJourneySupportContent(
        currency = currency, onCurrency = { currency = it; selectedOneTime = null; customOneTime = ""; selectedMonthly = null; customMonthly = "" },
        selectedOneTime = selectedOneTime, onOneTime = { selectedOneTime = it; customOneTime = "" }, customOneTime = customOneTime, onCustomOneTime = { customOneTime = it; selectedOneTime = null },
        selectedMonthly = selectedMonthly, onMonthly = { selectedMonthly = it; customMonthly = "" }, customMonthly = customMonthly, onCustomMonthly = { customMonthly = it; selectedMonthly = null },
        onBack = onBack, onMaybeLater = { onMaybeLater(); onNavigate(AppDestination.HOME) }, contentPadding = contentPadding
    )
}

@Composable
fun MyJourneySupportContent(
    currency: String, onCurrency: (String) -> Unit,
    selectedOneTime: Int?, onOneTime: (Int) -> Unit, customOneTime: String, onCustomOneTime: (String) -> Unit,
    selectedMonthly: String?, onMonthly: (String) -> Unit, customMonthly: String, onCustomMonthly: (String) -> Unit,
    onBack: () -> Unit, onMaybeLater: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(modifier = Modifier.fillMaxSize().background(Bg).padding(contentPadding).verticalScroll(rememberScrollState()).padding(horizontal = 24.dp).padding(top = 40.dp, bottom = 20.dp)) {
        Text(text = "‹", fontSize = 24.sp, color = Color(0xFF305C76), modifier = Modifier.padding(bottom = 24.dp).clickable { onBack() })
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(bottom = 10.dp)) {
            Box(modifier = Modifier.size(12.dp).background(Color(0xFF92ADBE), androidx.compose.foundation.shape.CircleShape))
            Text(text = "SUPPORT", fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp, color = Color(0xFF92ADBE))
        }
        Text(text = "Support the Mission", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = TitleBlue, modifier = Modifier.padding(bottom = 12.dp))
        Text(text = "Lampstand is free right now because we believe formation should be accessible to every believer. We're a small team building something we believe matters deeply. If this journey has been meaningful to you, consider supporting what we're building.", fontSize = 13.5.sp, lineHeight = 20.sp, color = Color(0xFF5A7181), modifier = Modifier.padding(bottom = 32.dp))

        // Currency selector
        Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(Color(0xFFF8FAFC)).border(width = 1.dp, color = Color(0xFFE2E8F0), shape = RoundedCornerShape(8.dp)).padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Pay In Your Local Currency:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF64748B))
            Text(text = currency, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Primary, modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(Color.White).border(width = 1.dp, color = Color(0xFFE2E8F0), shape = RoundedCornerShape(6.dp)).padding(horizontal = 12.dp, vertical = 6.dp).clickable { onCurrency(if (currency == "USD") "NGN" else "USD") })
        }
        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "1.) One-Time Gift", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Primary)
        Text(text = "A simple way to support the mission", fontSize = 12.sp, color = Color(0xFF64748B), modifier = Modifier.padding(bottom = 16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            val amounts = if (currency == "NGN") listOf(3000, 7000, 15000) else listOf(5, 15, 30)
            amounts.forEach { amt ->
                val isSelected = selectedOneTime == amt
                Box(modifier = Modifier.weight(1f).height(46.dp).clip(RoundedCornerShape(6.dp)).background(if (isSelected) Color(0xFFEEF4F8) else Color.White).border(width = 1.dp, color = if (isSelected) Primary else Color(0xFFE2E8F0), shape = RoundedCornerShape(6.dp)).clickable { onOneTime(amt) }, contentAlignment = Alignment.Center) {
                    Text(text = if (currency == "NGN") "₦${amt}" else "$$amt", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = if (isSelected) Primary else Color(0xFF64748B))
                }
            }
        }
        Text(text = "Give Another Amount (${if (currency == "NGN") "₦" else "$"})", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF5A7181), modifier = Modifier.padding(bottom = 8.dp))
        OutlinedTextField(value = customOneTime, onValueChange = onCustomOneTime, placeholder = { Text(text = if (currency == "NGN") "Enter Amount (₦)" else "Enter Amount ($)", fontSize = 14.sp, color = Color(0xFF94A3B8)) }, modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), shape = RoundedCornerShape(6.dp), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White, focusedBorderColor = Primary, unfocusedBorderColor = Color(0xFFE2E8F0)))
        Box(modifier = Modifier.fillMaxWidth().height(46.dp).clip(RoundedCornerShape(6.dp)).background(Primary).clickable {}, contentAlignment = Alignment.Center) {
            val label = when { selectedOneTime != null -> if (currency == "NGN") "Give ₦$selectedOneTime" else "Give \$$selectedOneTime.00"; customOneTime.isNotEmpty() -> "Give $customOneTime"; else -> "Give with Card" }
            Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        }
        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "2.) Monthly Support", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Primary)
        Text(text = "Help us build and maintain the vision.", fontSize = 12.sp, color = Color(0xFF64748B), modifier = Modifier.padding(bottom = 16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            val tiers = if (currency == "NGN") listOf(Triple("₦2,500", "PER MONTH", "monthly_ngn_3"), Triple("₦5,000", "PER MONTH", "monthly_ngn_7"), Triple("₦10,000", "PER MONTH", "monthly_ngn_15")) else listOf(Triple("$3", "PER MONTH", "monthly_3"), Triple("$7", "PER MONTH", "monthly_7"), Triple("$15", "PER MONTH", "monthly_15"))
            tiers.forEach { (label, sub, key) ->
                val isSelected = selectedMonthly == key
                Column(modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (isSelected) Color(0xFFEEF4F8) else Color.White).border(width = 1.dp, color = if (isSelected) Primary else Color(0xFFE2E8F0), shape = RoundedCornerShape(6.dp)).clickable { onMonthly(key) }.padding(vertical = 12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = if (isSelected) Primary else Color(0xFF64748B))
                    Text(text = sub, fontSize = 8.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp, color = Color(0xFF94A3B8))
                }
            }
        }
        Text(text = "Give Another Amount (${if (currency == "NGN") "₦" else "$"})", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF5A7181), modifier = Modifier.padding(bottom = 8.dp))
        OutlinedTextField(value = customMonthly, onValueChange = onCustomMonthly, placeholder = { Text(text = if (currency == "NGN") "Enter Amount (₦)" else "Enter Amount ($)", fontSize = 14.sp, color = Color(0xFF94A3B8)) }, modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), shape = RoundedCornerShape(6.dp), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White, focusedBorderColor = Primary, unfocusedBorderColor = Color(0xFFE2E8F0)))
        Box(modifier = Modifier.fillMaxWidth().height(46.dp).clip(RoundedCornerShape(6.dp)).background(Primary).clickable {}, contentAlignment = Alignment.Center) {
            Text(text = "Give Monthly", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "** No pressure. Every amount helps. You can also skip and come back to this anytime.", fontSize = 12.sp, lineHeight = 16.sp, color = Color(0xFF5A7181), modifier = Modifier.padding(bottom = 16.dp))
        Box(modifier = Modifier.fillMaxWidth().height(46.dp).clip(RoundedCornerShape(6.dp)).background(Color.White).border(width = 1.dp, color = Primary, shape = RoundedCornerShape(6.dp)).clickable { onMaybeLater() }, contentAlignment = Alignment.Center) {
            Text(text = "Maybe Later", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Primary)
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showBackground = true, name = "MyJourney Support")
@Composable
fun MyJourneySupportPreview() {
    LampStandMobileTheme { MyJourneySupportContent(currency = "USD", onCurrency = {}, selectedOneTime = 15, onOneTime = {}, customOneTime = "", onCustomOneTime = {}, selectedMonthly = "monthly_7", onMonthly = {}, customMonthly = "", onCustomMonthly = {}, onBack = {}, onMaybeLater = {}) }
}

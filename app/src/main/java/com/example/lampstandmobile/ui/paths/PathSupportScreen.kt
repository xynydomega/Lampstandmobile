package com.example.lampstandmobile.ui.paths

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lampstandmobile.navigation.AppDestination

private val Primary = Color(0xFF335E78)
private val BackBlue = Color(0xFF305C76)
private val HeaderBlue = Color(0xFF1E3F57)
private val BodyBlue = Color(0xFF5A7181)
private val SupportBlue = Color(0xFF92ADBE)
private val Slate500 = Color(0xFF64748B)
private val Slate400 = Color(0xFF94A3B8)
private val Slate200 = Color(0xFFE2E8F0)
private val Slate100 = Color(0xFFF1F5F9)
private val SelectedBackground = Color(0xFFEEF4F8)

private val currencies = listOf(
    "USD ($)",
    "NGN (₦)",
    "GBP (£)",
    "EUR (€)",
    "CAD (C$)",
    "AUD (A$)",
    "JPY (¥)"
)

@Composable
fun PathSupportScreen(
    onNavigate: (AppDestination) -> Unit
) {
    var currency by remember { mutableStateOf("USD ($)") }

    var selectedOneTimePreset by remember {
        mutableStateOf<String?>(null)
    }

    var customOneTimeAmount by remember {
        mutableStateOf("")
    }

    var selectedMonthlyTier by remember {
        mutableStateOf<String?>(null)
    }

    var customMonthlyAmount by remember {
        mutableStateOf("")
    }

    var currencyMenuExpanded by remember {
        mutableStateOf(false)
    }

    val isNGN = currency.startsWith("NGN")

    val oneTimeAmounts =
        if (isNGN) {
            listOf("₦3,000", "₦7,000", "₦15,000")
        } else {
            listOf("$5", "$15", "$30")
        }

    val monthlyTiers =
        if (isNGN) {
            listOf(
                Triple("₦2,500", "PER MONTH", "monthly_ngn_3"),
                Triple("₦5,000", "PER MONTH", "monthly_ngn_7"),
                Triple("₦10,000", "PER MONTH", "monthly_ngn_15")
            )
        } else {
            listOf(
                Triple("$3", "PER MONTH", "monthly_3"),
                Triple("$7", "PER MONTH", "monthly_7"),
                Triple("$15", "PER MONTH", "monthly_15")
            )
        }

    val oneTimeButtonText =
        when {
            selectedOneTimePreset != null ->
                "Give ${selectedOneTimePreset}"

            customOneTimeAmount.isNotBlank() ->
                if (isNGN) {
                    "Give ₦${customOneTimeAmount}"
                } else {
                    "Give $${customOneTimeAmount}"
                }

            else ->
                "Give with Card"
        }

    val monthlyButtonText =
        when {
            selectedMonthlyTier == "monthly_3" ||
                selectedMonthlyTier == "monthly_ngn_3" ->
                if (isNGN) {
                    "Subscribe for ₦2,500 / month"
                } else {
                    "Subscribe for $3 / month"
                }

            selectedMonthlyTier == "monthly_15" ||
                selectedMonthlyTier == "monthly_ngn_15" ->
                if (isNGN) {
                    "Subscribe for ₦10,000 / month"
                } else {
                    "Subscribe for $15 / month"
                }

            selectedMonthlyTier == "monthly_7" ||
                selectedMonthlyTier == "monthly_ngn_7" ->
                if (isNGN) {
                    "Subscribe for ₦5,000 / month"
                } else {
                    "Subscribe for $7 / month"
                }

            customMonthlyAmount.isNotBlank() ->
                if (isNGN) {
                    "Subscribe to ₦5,000 / month"
                } else {
                    "Subscribe to $7 / month"
                }

            else ->
                "Give Monthly"
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 40.dp,
                bottom = 80.dp
            )
    ) {

        // WEB:
        // <header className="mb-6">
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = BackBlue,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onNavigate(AppDestination.PATHS)
                    }
            )
        }

        // WEB:
        // Main Header Content
        Column(
            modifier = Modifier.padding(bottom = 32.dp)
        ) {

            Row(
                modifier = Modifier.padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(SupportBlue)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "SUPPORT",
                    color = SupportBlue,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.1.sp
                )
            }

            Text(
                text = "Support the Mission",
                color = HeaderBlue,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 31.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = "Lampstand is free right now because we believe formation should be accessible to every believer. We're a small team building something we believe matters deeply. If this journey has been meaningful to you, consider supporting what we're building.",
                color = BodyBlue,
                fontSize = 13.5.sp,
                lineHeight = 21.sp
            )
        }

        // Currency selector
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF8FAFC))
                    .border(
                        1.dp,
                        Slate200,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Pay In Your Local Currency:",
                    color = Slate500,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Box {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.White)
                            .border(
                                1.dp,
                                Slate200,
                                RoundedCornerShape(6.dp)
                            )
                            .clickable {
                                currencyMenuExpanded = true
                            }
                            .padding(
                                horizontal = 12.dp,
                                vertical = 6.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = currency,
                            color = Primary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = currencyMenuExpanded,
                        onDismissRequest = {
                            currencyMenuExpanded = false
                        }
                    ) {
                        currencies.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontSize = 12.sp
                                    )
                                },
                                onClick = {
                                    currency = item
                                    selectedOneTimePreset = null
                                    customOneTimeAmount = ""
                                    selectedMonthlyTier = null
                                    customMonthlyAmount = ""
                                    currencyMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        // 1.) One-Time Gift
        Column(
            modifier = Modifier.padding(bottom = 32.dp)
        ) {

            Text(
                text = "1.) One-Time Gift",
                color = Primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "A simple way to support the mission",
                color = Slate500,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                oneTimeAmounts.forEach { amount ->
                    SupportPresetButton(
                        text = amount,
                        selected = selectedOneTimePreset == amount,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            selectedOneTimePreset = amount
                            customOneTimeAmount = ""
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Give Another Amount (${if (isNGN) "₦" else "$"})",
                color = BodyBlue,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = customOneTimeAmount,
                onValueChange = {
                    customOneTimeAmount = it
                    selectedOneTimePreset = null
                },
                placeholder = {
                    Text(
                        text = if (isNGN) {
                            "Enter Amount (₦)"
                        } else {
                            "Enter Amount ($)"
                        },
                        color = Slate400,
                        fontSize = 14.sp
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(6.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = Slate200,
                    focusedTextColor = Color(0xFF1E293B),
                    unfocusedTextColor = Color(0xFF1E293B)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            SupportPrimaryButton(
                text = oneTimeButtonText,
                onClick = {}
            )
        }

        // 2.) Monthly Support
        Column(
            modifier = Modifier.padding(bottom = 16.dp)
        ) {

            Text(
                text = "2.) Monthly Support",
                color = Primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Help us build and maintain the vision.",
                color = Slate500,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
            )

            if (!isNGN && !currency.startsWith("USD")) {
                Text(
                    text = "* Note: Recurring monthly support is billed in USD and automatically converted by your bank.",
                    color = Slate500,
                    fontSize = 11.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    lineHeight = 15.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color(0xFFF8FAFC),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            Slate100,
                            RoundedCornerShape(4.dp)
                        )
                        .padding(10.dp)
                        .padding(bottom = 12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                monthlyTiers.forEach { tier ->
                    MonthlyPresetButton(
                        amount = tier.first,
                        subtitle = tier.second,
                        selected = selectedMonthlyTier == tier.third,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            selectedMonthlyTier = tier.third
                            customMonthlyAmount = ""
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Give Another Amount (${if (isNGN) "₦" else "$"})",
                color = BodyBlue,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = customMonthlyAmount,
                onValueChange = {
                    customMonthlyAmount = it
                    selectedMonthlyTier = null
                },
                placeholder = {
                    Text(
                        text = if (isNGN) {
                            "Enter Amount (₦)"
                        } else {
                            "Enter Amount ($)"
                        },
                        color = Slate400,
                        fontSize = 14.sp
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(6.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = Slate200,
                    focusedTextColor = Color(0xFF1E293B),
                    unfocusedTextColor = Color(0xFF1E293B)
                )
            )

            if (customMonthlyAmount.isNotEmpty()) {
                Text(
                    text = "Note: Custom monthly amounts will be redirected to our suggested ${if (isNGN) "₦5,000" else "$7"} plan. Forspecific values, please select a preset tier above.",
                    color = Color(0xFFD97706),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 13.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            SupportPrimaryButton(
                text = monthlyButtonText,
                onClick = {}
            )
        }

        // Footer disclaimer
        Column(
            modifier = Modifier.padding(top = 16.dp)
        ) {

            Text(
                text = "** No pressure. Every amount helps. You can also skip and come back to this anytime.",
                color = BodyBlue,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    onNavigate(AppDestination.HOME)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Primary
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    Primary
                )
            ) {
                Text(
                    text = "Maybe Later",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun SupportPresetButton(
    text: String,
    selected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(50.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(
                if (selected) SelectedBackground else Color.White
            )
            .border(
                width = 1.dp,
                color = if (selected) Primary else Slate200,
                shape = RoundedCornerShape(6.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) Primary else Color(0xFF475569),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun MonthlyPresetButton(
    amount: String,
    subtitle: String,
    selected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .height(58.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(
                if (selected) SelectedBackground else Color.White
            )
            .border(
                width = 1.dp,
                color = if (selected) Primary else Slate200,
                shape = RoundedCornerShape(6.dp)
            )
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = amount,
            color = if (selected) Primary else Color(0xFF475569),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = subtitle,
            color = Slate400,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@Composable
private fun SupportPrimaryButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = Color.White
        )
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

package com.example.lampstandmobile.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lampstandmobile.R
import com.example.lampstandmobile.ui.components.ui.Input
import com.example.lampstandmobile.ui.theme.LampStandMobileTheme

@Composable
fun AuthScreen(
    state: AuthState,
    onAction: (AuthAction) -> Unit
) {
    if (state.isLoading) {
        AuthLoading()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 28.dp,
                top = 28.dp,
                end = 28.dp,
                bottom = 48.dp
            )
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Lampstand",
            modifier = Modifier.size(48.dp)
        )

        when (state.currentStep) {
            AuthStep.EMAIL -> {
                EmailStep(
                    state = state,
                    onAction = onAction
                )
            }

            AuthStep.OTP -> {
                OtpStep(
                    state = state,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun AuthLoading() {
    // Web loader.tsx w-full h-screen bg-[#335E78] flex center max-w-120 loader.svg
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF335E78)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color.White
        )
    }
}

@Composable
private fun EmailStep(
    state: AuthState,
    onAction: (AuthAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Welcome to Lampstand!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF335E78)
        )

        Text(
            text = "Your formation journey starts here.",
            fontSize = 18.sp,
            color = Color(0xFF535353)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Input(
            label = "Email Address",
            placeholder = "Enter your email",
            value = state.email,
            onValueChange = {
                onAction(AuthAction.EmailChanged(it))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )

        state.errorMessage?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                fontSize = 14.sp
            )
        }

        Button(
            onClick = { onAction(AuthAction.SendOtp) },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = state.email.isNotBlank() && !state.isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF335E78),
                contentColor = Color.White,
                disabledContainerColor = Color(0xFF828282),
                disabledContentColor = Color.White
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF335E78))
        ) {
            Text(text = "Continue", fontSize = 16.sp, fontWeight = FontWeight.Normal)
        }

        // Dev-only quick login like web auth/page.tsx:251 amber dashed border-amber-300 bg-amber-50
        if (androidx.compose.ui.platform.LocalInspectionMode.current) {
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFFFF8E1))
                    .border(width = 1.dp, color = Color(0xFFFFE082), shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(text = "⚡ Dev Mode — bypasses OTP email", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFFB45309), modifier = Modifier.padding(bottom = 8.dp))
                Button(
                    onClick = { onAction(AuthAction.DevLogin) },
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFBBF24), contentColor = Color.White),
                    enabled = state.email.isNotBlank() && !state.isLoading
                ) { Text(text = "Dev Login (one click)", fontSize = 14.sp, fontWeight = FontWeight.Bold) }
                Text(text = "Enter your email above · code auto-fills 0000", fontSize = 10.sp, color = Color(0xFFD97706), modifier = Modifier.padding(top = 6.dp))
            }
        }
    }
}

@Composable
private fun OtpStep(
    state: AuthState,
    onAction: (AuthAction) -> Unit
) {
    val focusRequesters = remember {
        List(4) { FocusRequester() }
    }

    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Text(
                text = "Verification Code",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF335E78)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enter the 4-digit code we sent to ${
                    state.email.ifEmpty { "your email" }
                }",
                fontSize = 18.sp,
                color = Color(0xFF535353)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(
                16.dp,
                Alignment.CenterHorizontally
            )
        ) {
            state.code.forEachIndexed { index, digit ->
                var isFocused by remember { mutableStateOf(false) }
                // Web: w-16 h-16 border-b-[3px] border-[#535353] bg-transparent text-center text-3xl font-bold text-[#335E78] focus:border-[#335E78]
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.Transparent)
                        .onFocusChanged { isFocused = it.isFocused },
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        value = digit,
                        onValueChange = { value ->
                            val newDigit = value.filter { it.isDigit() }.takeLast(1)
                            onAction(AuthAction.CodeChanged(index = index, value = newDigit))
                            if (newDigit.isNotEmpty() && index < 3) focusRequesters[index + 1].requestFocus()
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .focusRequester(focusRequesters[index])
                            .onFocusChanged { isFocused = it.isFocused }
                            .onPreviewKeyEvent { event ->
                                if (event.key == Key.Backspace && event.type == KeyEventType.KeyDown && digit.isEmpty() && index > 0) {
                                    focusRequesters[index - 1].requestFocus(); true
                                } else false
                            },
                        textStyle = TextStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF335E78),
                            textAlign = TextAlign.Center
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        cursorBrush = SolidColor(Color(0xFF335E78)),
                        decorationBox = { inner ->
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { inner() }
                        }
                    )
                    // Only bottom border 3px like web border-b-[3px] #535353 focused #335E78
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(3.dp)
                            .background(if (isFocused) Color(0xFF335E78) else Color(0xFF535353))
                    )
                }
            }
        }

        state.errorMessage?.let { error ->
            Text(
                text = error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                color = Color.Red,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Didn't receive a code?")

            if (state.resendCooldown > 0) {
                Text(
                    text = "Resend available in ${state.resendCooldown}s",
                    modifier = Modifier.padding(top = 4.dp),
                    color = Color(0xFF94A3B8),
                    fontSize = 14.sp
                )
            } else {
                TextButton(
                    onClick = {
                        onAction(AuthAction.ResendOtp)
                    }
                ) {
                    Text(
                        text = "Resend code",
                        color = Color(0xFF335E78),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Button(
            onClick = { onAction(AuthAction.VerifyOtp) },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = state.code.all { it.isNotEmpty() } && !state.isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF335E78),
                contentColor = Color.White,
                disabledContainerColor = Color(0xFF828282),
                disabledContentColor = Color.White
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF335E78))
        ) {
            Text(text = "Continue", fontSize = 16.sp, fontWeight = FontWeight.Normal)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthScreenPreview() {
    var state by remember { mutableStateOf(AuthState()) }
    LampStandMobileTheme {
        AuthScreen(
            state = state,
            onAction = { action ->
                when (action) {
                    is AuthAction.EmailChanged -> state = state.copy(email = action.email)
                    AuthAction.SendOtp -> state = state.copy(currentStep = AuthStep.OTP)
                    is AuthAction.CodeChanged -> {
                        val newCode = state.code.toMutableList()
                        newCode[action.index] = action.value
                        state = state.copy(code = newCode.toList())
                    }
                    else -> Unit
                }
            }
        )
    }
}

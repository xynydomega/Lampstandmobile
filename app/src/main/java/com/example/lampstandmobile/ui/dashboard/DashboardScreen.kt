package com.example.lampstandmobile.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.PaddingValues
import com.example.lampstandmobile.navigation.AppDestination
import com.example.lampstandmobile.ui.theme.LampStandMobileTheme
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.lampstandmobile.R



@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigate: (AppDestination) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val state by viewModel.uiState.collectAsState()

    DashboardContent(
        state = state,
        onAction = viewModel::onAction,
        onNavigate = onNavigate,
        contentPadding = contentPadding
    )
}

@Composable
fun DashboardContent(
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
    onNavigate: (AppDestination) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFDFD))
            .padding(contentPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {

            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 48.dp,
                        bottom = 24.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hi, there",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF64748B)
                    )

                    Text(
                        text = "Welcome Back",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF64748B)
                    )
                }
            }

            // Active Path Banner
            val homeState = state.homeState
            val status = homeState?.get("state") as? String ?: "no_path"
            val pathTitle = homeState?.get("pathTitle") as? String ?: "No Active Path"
            val currentDay = (homeState?.get("currentDay") as? Number)?.toInt() ?: 0
            val totalDays = (homeState?.get("totalDays") as? Number)?.toInt() ?: 0
            val todayDone = homeState?.get("todayDone") as? Boolean ?: false

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 32.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF9BB8C5))
                ) {
                    Image(
                        painter = painterResource(R.drawable.sailboat),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 12.dp,
                                end = 12.dp,
                                bottom = 12.dp
                            )
                            .align(Alignment.BottomCenter)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFFE3F0F8))
                                    .padding(
                                        horizontal = 8.dp,
                                        vertical = 4.dp
                                    )
                            ) {
                                Text(
                                    text = if (status == "no_path") "NO ACTIVE PATH" else pathTitle.uppercase(),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2A5975)
                                )
                            }

                            Text(
                                text = if (totalDays > 0) "Day $currentDay of $totalDays" else "Day X of Y",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF2A5975)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .clip(RoundedCornerShape(50))
                                .background(Color(0xFFF1F5F9))
                        ) {
                            val progress = if (totalDays > 0) (currentDay.toFloat() / totalDays).coerceIn(0f, 1f) else 0f
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(progress)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(50))
                                    .background(Color(0xFF305C76))
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = when {
                                status == "no_path" -> "Please select a path to get started."
                                todayDone -> "Today's session completed. Come back tomorrow."
                                else -> pathTitle
                            },
                            fontSize = 14.sp,
                            color = Color(0xFF64748B),
                            modifier = Modifier.padding(bottom = 20.dp)
                        )

                        Button(
                            onClick = {
                                if (status == "no_path") {
                                    onNavigate(AppDestination.PATHS)
                                } else {
                                    onAction(DashboardAction.Refresh)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF305C76),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = if (status == "no_path") "Go to Paths" else if (todayDone) "View Journey" else "Continue",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Spotlight
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp)
                    .padding(bottom = 32.dp)
            ) {
                Text(
                    text = "Spotlight",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF334155),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(end = 12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(256.dp)
                            .height(144.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF345D77))
                    ) {
                        Image(
                            painter = painterResource(R.drawable.spotlight1),
                            contentDescription = "Thumbnail",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Box(
                        modifier = Modifier
                            .width(256.dp)
                            .height(144.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF496E87))
                    ) {
                        Image(
                            painter = painterResource(R.drawable.spotlight2),
                            contentDescription = "Thumbnail",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            // Recommended For You
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp)
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = "Recommended For You",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF334155),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(end = 12.dp)
                ) {
                    Column(
                        modifier = Modifier.width(200.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(200.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFD4DDE4))
                        ) {
                            Image(
                                painter = painterResource(R.drawable.book_with_leaf),
                                contentDescription = "The Way of Wisdom",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                contentScale = ContentScale.Fit
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Fear & Anxiety",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "0 Formation Paths",
                            fontSize = 13.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    LampStandMobileTheme {
        DashboardContent(
            state = DashboardState(),
            onAction = {},
            onNavigate = {}
        )
    }
}

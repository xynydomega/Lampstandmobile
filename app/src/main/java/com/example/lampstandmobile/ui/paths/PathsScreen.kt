package com.example.lampstandmobile.ui.paths

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lampstandmobile.R
import com.example.lampstandmobile.navigation.AppDestination

@Composable
fun PathsScreen(
    onNavigate: (AppDestination) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    // Scaffold handles bottom nav insets; inner bottom padding reduced to 16.dp (web pb-24 handled by Scaffold)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                        bottom = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "‹",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF305C76),
                    modifier = Modifier
                        .width(32.dp)
                        .clip(RoundedCornerShape(50))
                )

                Text(
                    text = "Formation Paths",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2A5975),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(32.dp))
            }

            // Subtitle
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Available Categories",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2A5975)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Start your journey by exploring themes around life and purpose.",
                    fontSize = 13.sp,
                    color = Color(0xFF64748B),
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(end = 32.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Fear & Anxiety category
            CategoryCard(
                onClick = {
                    onNavigate(AppDestination.PATH_DETAIL)
                }
            )
        }

    }
}

@Composable
private fun CategoryCard(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(R.drawable.available),
                    contentDescription = "Fear & Anxiety",
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(6.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Fear & Anxiety",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B3B5A),
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "6 formation paths",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF64748B)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = onClick,
                modifier = Modifier.height(42.dp),
                shape = RoundedCornerShape(2.dp),
                contentPadding = PaddingValues(
                    horizontal = 20.dp,
                    vertical = 0.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF305C76),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "View paths",
                    fontSize = 14.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFE8E8E8))
        )

        Text(
            text = "NOW AVAILABLE - START YOUR JOURNEY",
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF1B3B5A),
            letterSpacing = 0.88.sp,
            modifier = Modifier.padding(
                start = 24.dp,
                top = 8.dp,
                bottom = 8.dp
            )
        )
    }
}

package com.example.lampstandmobile.ui.components.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoriesCard(
    id: String,
    title: String,
    image: String,
    available: Boolean,
    amount: String,
    onViewPaths: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFE8E8E8),
                shape = RoundedCornerShape(2.dp)
            )
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            /*
             * Web source uses the category's actual image.
             * Android image loading will be connected to the real
             * image source when the category data is replicated.
             */
            Spacer(
                modifier = Modifier
                    .size(72.dp)
                    .background(
                        color = Color(0xFF222222),
                        shape = RoundedCornerShape(6.dp)
                    )
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B3B5A),
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$amount formation paths",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF64748B)
                )
            }

            Button(
                onClick = { onViewPaths(id) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF305C76),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(2.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    horizontal = 20.dp,
                    vertical = 10.dp
                )
            ) {
                Text(
                    text = "View paths",
                    fontSize = 14.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color(0xFFE8E8E8)
                )
                .padding(
                    horizontal = 24.dp,
                    vertical = 8.dp
                )
        ) {
            Text(
                text = if (available) {
                    "NOW AVAILABLE - START YOUR JOURNEY"
                } else {
                    "COMING SOON - CHECK BACK LATER"
                },
                fontSize = 11.sp,
                color = Color(0xFF1B3B5A),
                fontWeight = FontWeight.Normal
            )
        }
    }
}
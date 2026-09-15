package com.example.lampstandmobile.ui.paths

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.PeopleOutline
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.PaddingValues
import com.example.lampstandmobile.R
import com.example.lampstandmobile.navigation.AppDestination
import com.example.lampstandmobile.ui.theme.LampStandMobileTheme

private val Background = Color(0xFFFDFDFD)
private val Primary = Color(0xFF305C76)
private val Heading = Color(0xFF2A5975)
private val Slate800 = Color(0xFF1E293B)
private val Slate500 = Color(0xFF64748B)
private val Slate400 = Color(0xFF94A3B8)
private val Slate200 = Color(0xFFE2E8F0)
private val Slate100 = Color(0xFFF1F5F9)
private val AvailableBlue = Color(0xFF335E78)
private val CalendarBlue = Color(0xFF4A6B82)
private val InterestBlue = Color(0xFF7BA1B6)
private val ComingSoonGrey = Color(0xFFB3B3B3)

@Composable
fun CategoryPathsScreen(
    onNavigate: (AppDestination) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var searchText by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(contentPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {

            /*
             * WEB:
             * <header className="flex items-center px-6 pt-12 pb-4">
             */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 0.dp
                    )
                    .padding(
                        top = 48.dp,
                        bottom = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Primary,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            onNavigate(AppDestination.PATHS)
                        }
                )
            }

            /*
             * WEB:
             * px-6 mb-5
             * h2 mt-7 mb-5 text-[20px]
             */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 20.dp)
            ) {
                Text(
                    text = "Fear & Anxiety",
                    color = Heading,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 24.sp,
                    modifier = Modifier.padding(
                        top = 28.dp,
                        bottom = 20.dp
                    )
                )

                Text(
                    text = "Grow in understanding God's character through these curated paths focused on Fear and Anxiety",
                    color = Slate500,
                    fontSize = 13.sp,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(end = 32.dp)
                )
            }

            /*
             * WEB:
             * Search input
             * px-6 mb-6
             */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        bottom = 24.dp
                    )
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                    },
                    placeholder = {
                        Text(
                            text = "Search Topics...",
                            color = Slate400,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Primary,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Slate200,
                        unfocusedBorderColor = Slate200,
                        cursorColor = Primary
                    )
                )
            }

            /*
             * WEB:
             * <div className="px-6 space-y-3">
             *
             * Actual Convex data is intentionally omitted for UI phase.
             */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                LivePathCard(
                    title = "Trust in Uncertainty",
                    description = "",
                    imageRes = R.drawable.sailboat,
                    onClick = {
                        // Navigation will be wired when dataflow is added.
                    }
                )

                ComingSoonPathCard(
                    title = "When Anxiety Won't Stop",
                    description = "",
                    imageRes = R.drawable.book_communion
                )

                ComingSoonPathCard(
                    title = "Fear of Failure",
                    description = "",
                    imageRes = R.drawable.dove
                )

                ComingSoonPathCard(
                    title = "When the Worst Happens",
                    description = "",
                    imageRes = R.drawable.book_with_leaf
                )

                ComingSoonPathCard(
                    title = "Afraid of What People Think",
                    description = "",
                    imageRes = R.drawable.crown_with_torch
                )

                ComingSoonPathCard(
                    title = "When You Don't Feel Safe",
                    description = "",
                    imageRes = R.drawable.sailboat
                )
            }
        }

    }
}

@Composable
private fun LivePathCard(
    title: String,
    description: String,
    imageRes: Int,
    onClick: () -> Unit
) {
    /*
     * WEB Pathcard:
     * p-3.5
     * bg-white
     * rounded-sm
     * border border-slate-100
     */
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Slate100,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            ) {

                Row(
                    modifier = Modifier.padding(bottom = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Duration",
                        tint = CalendarBlue,
                        modifier = Modifier.size(14.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "7 days",
                        color = CalendarBlue,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = title,
                    color = Slate800,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 19.sp,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                if (description.isNotEmpty()) {
                    Text(
                        text = description,
                        color = Slate500,
                        fontSize = 11.sp,
                        lineHeight = 14.sp
                    )
                }
            }

            PathThumbnail(
                imageRes = imageRes
            )
        }

        /*
         * WEB:
         * absolute top-0 right-0
         * bg-[#335E78]
         * px-2 py-1
         */
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(
                    color = AvailableBlue,
                    shape = RoundedCornerShape(
                        bottomStart = 2.dp
                    )
                )
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "AVAILABLE NOW",
                color = Color.White,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.7.sp
            )
        }
    }
}

@Composable
private fun ComingSoonPathCard(
    title: String,
    description: String,
    imageRes: Int
) {
    /*
     * WEB:
     * p-3.5
     * bg-white
     * rounded-sm
     * border border-slate-100
     */
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Slate100,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp)
        ) {

            Text(
                text = "COMING SOON — TAP TO EXPRESS INTEREST",
                color = ComingSoonGrey,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.6.sp,
                lineHeight = 12.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Text(
                text = title,
                color = Slate800,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 19.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    color = Slate500,
                    fontSize = 11.sp,
                    lineHeight = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.PeopleOutline,
                    contentDescription = "Interest",
                    tint = InterestBlue,
                    modifier = Modifier.size(12.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "Be the first to express interest",
                    color = AvailableBlue,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        PathThumbnail(
            imageRes = imageRes
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryPathsPreview() {
    LampStandMobileTheme {
        CategoryPathsScreen(onNavigate = {})
    }
}

@Composable
private fun PathThumbnail(
    imageRes: Int
) {
    /*
     * WEB:
     * w-[86px] h-[86px]
     * rounded-lg
     */
    Box(
        modifier = Modifier
            .size(86.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFE8E8E8)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier.size(73.dp),
            contentScale = ContentScale.Fit
        )
    }
}

package com.example.lampstandmobile.ui.journey.list

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.lampstandmobile.R
import com.example.lampstandmobile.navigation.AppDestination
import com.example.lampstandmobile.ui.theme.LampStandMobileTheme

@Composable
fun MyJourneyListScreen(
    viewModel: MyJourneyListViewModel,
    onNavigate: (AppDestination) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val state by viewModel.uiState.collectAsState()
    MyJourneyListContent(
        state = state,
        onAction = viewModel::onAction,
        onNavigate = onNavigate,
        contentPadding = contentPadding
    )
}

@Composable
fun MyJourneyListContent(
    state: MyJourneyListState,
    onAction: (MyJourneyListAction) -> Unit,
    onNavigate: (AppDestination) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFDFD))
            .padding(contentPadding)
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF335E78)
                )
            }
            state.paths.isEmpty() -> {
                // EmptyState — web app/my-journey/page.tsx EmptyState
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 21.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No path selected",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 22.sp,
                        color = Color(0xFF184159)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Sorry you do not have an active path selected.",
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = Color(0xFF535353)
                    )
                    Spacer(modifier = Modifier.height(78.dp))
                    Button(
                        onClick = { onNavigate(AppDestination.PATHS) },
                        modifier = Modifier
                            .width(275.dp)
                            .height(42.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF335E78),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Go to Paths",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 22.sp
                        )
                    }
                }
            }
            else -> {
                // Has paths — web hasPaths true branch
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 21.dp)
                            .padding(top = 48.dp, bottom = 20.dp)
                    ) {
                        Text(
                            text = "My Journey",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = 26.sp,
                            color = Color(0xFF184159)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 21.dp)
                            .padding(bottom = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        state.paths.forEach { path ->
                            PathCard(
                                path = path,
                                onClick = {
                                    onAction(MyJourneyListAction.PathClicked(path.id))
                                    onNavigate(AppDestination.JOURNEY_DETAIL)
                                }
                            )
                        }
                    }
                }
            }
        }
        state.errorMessage?.let { msg ->
            Text(
                text = msg,
                fontSize = 12.sp,
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
private fun PathCard(
    path: JourneyPathUi,
    onClick: () -> Unit
) {
    // Web: flex items-stretch w-full rounded-[8px] overflow-hidden border #E8E8E8
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .border(width = 1.dp, color = Color(0xFFE8E8E8), shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 13.dp, top = 9.dp, bottom = 9.dp, end = 13.dp)
                .height(85.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = Color(0xFF335E78),
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = "${path.durationDays} days",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 15.sp,
                    color = Color(0xFF335E78)
                )
            }
            Text(
                text = path.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 15.sp,
                color = Color(0xFF535353)
            )
            Text(
                text = path.description,
                fontSize = 11.sp,
                lineHeight = 15.sp,
                color = Color(0xFF535353),
                modifier = Modifier.width(205.dp)
            )
        }
        Box(
            modifier = Modifier
                .size(85.dp)
                .padding(9.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFF1E1E1E)),
            contentAlignment = Alignment.Center
        ) {
            if (path.thumbnailRes != null) {
                Image(
                    painter = painterResource(path.thumbnailRes),
                    contentDescription = path.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "MyJourney List - With Path")
@Composable
fun MyJourneyListPreview() {
    LampStandMobileTheme {
        MyJourneyListContent(
            state = MyJourneyListState(
                paths = listOf(
                    JourneyPathUi(
                        id = "1",
                        title = "Trust in Uncertainty",
                        description = "Grow in understanding God's character through these curated paths",
                        durationDays = 7,
                        thumbnailRes = R.drawable.sailboat
                    )
                )
            ),
            onAction = {},
            onNavigate = {}
        )
    }
}

@Preview(showBackground = true, name = "MyJourney List - Empty")
@Composable
fun MyJourneyListEmptyPreview() {
    LampStandMobileTheme {
        MyJourneyListContent(
            state = MyJourneyListState(paths = emptyList()),
            onAction = {},
            onNavigate = {}
        )
    }
}

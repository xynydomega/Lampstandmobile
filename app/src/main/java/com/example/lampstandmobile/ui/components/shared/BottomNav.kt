package com.example.lampstandmobile.ui.components.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lampstandmobile.R

@Composable
fun BottomNav(
    state: BottomNavState,
    onAction: (BottomNavAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val navItems = listOf(
        Triple(BottomNavTab.HOME, "Home", R.drawable.home_icon),
        Triple(BottomNavTab.PATHS, "Paths", R.drawable.path),
        Triple(BottomNavTab.JOURNEY, "My Journey", R.drawable.journey),
        Triple(BottomNavTab.PROFILE, "Profile", R.drawable.profile_icon)
    )

    // Web: fixed bottom-0 left-0 right-0 max-w-screen-md mx-auto bg-white
    // border-t border-slate-200 shadow-[0_-4px_12px_-4px_rgba(0,0,0,0.05)] pt-3 pb-7 z-50
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp)
            .border(width = 1.dp, color = Color(0xFFE2E8F0)),
        color = Color.White,
        shadowElevation = 8.dp,
        tonalElevation = 0.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(top = 12.dp, bottom = 28.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 768.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                navItems.forEach { (tab, label, icon) ->
                    val isActive = state.activeTab == tab
                    val interactionSource = remember { MutableInteractionSource() }

                    Column(
                        modifier = Modifier
                            .widthIn(min = 64.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = androidx.compose.foundation.LocalIndication.current,
                                onClick = { onAction(BottomNavAction.SelectTab(tab)) }
                            )
                            .semantics {
                                role = Role.Tab
                                selected = isActive
                            }
                            .padding(horizontal = 4.dp, vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = label,
                            modifier = Modifier.size(32.dp),
                            tint = if (isActive) Color(0xFF305C76) else Color(0xFF535353)
                        )
                        Text(
                            text = label,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.3).sp,
                            color = if (isActive) Color(0xFF305C76) else Color(0xFF535353)
                        )
                    }
                }
            }
        }
    }
}

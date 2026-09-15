package com.example.lampstandmobile.ui.profile

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lampstandmobile.R
import com.example.lampstandmobile.navigation.AppDestination
import com.example.lampstandmobile.ui.theme.LampStandMobileTheme

private val Bg = Color(0xFFFDFDFD)
private val TitleBlue = Color(0xFF2A5975)
private val TextGrey = Color(0xFF535353)
private val IconBg = Color(0xFFE3F0F8)
private val BorderLight = Color(0xFFF1F5F9)

@Composable
fun ProfileScreen(
    onNavigate: (AppDestination) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    ProfileContent(
        fullName = "Lampstand Pilgrim",
        email = "No email linked",
        phone = "Not provided",
        region = "Not specified",
        spiritualSeason = "Not chosen",
        avatarRes = null,
        onBack = { onNavigate(AppDestination.HOME) },
        onPersonalInfo = { onNavigate(AppDestination.PROFILE_PERSONAL_INFO) },
        onLogout = { onNavigate(AppDestination.AUTH) },
        contentPadding = contentPadding
    )
}

@Composable
fun ProfileContent(
    fullName: String,
    email: String,
    phone: String,
    region: String,
    spiritualSeason: String,
    avatarRes: Int?,
    onBack: () -> Unit,
    onPersonalInfo: () -> Unit,
    onLogout: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Bg).padding(contentPadding).verticalScroll(rememberScrollState()).padding(bottom = 32.dp)
    ) {
        // Header like web flex items-center px-6 pt-12 pb-4
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(top = 48.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(40.dp).clickable { onBack() }, contentAlignment = Alignment.Center) {
                Text(text = "‹", fontSize = 28.sp, color = Color(0xFF305C76))
            }
        }
        Column(modifier = Modifier.padding(horizontal = 24.dp).padding(start = 8.dp, top = 10.dp)) {
            Text(text = "Profile", fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = TitleBlue)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Manage your Lampstand Profile from here.", fontSize = 17.sp, color = TextGrey)
        }

        // User Info Card bg-white border slate-100 rounded-2xl p-6 shadow-sm flex gap-4
        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(top = 32.dp, bottom = 32.dp)
                .clip(RoundedCornerShape(16.dp)).background(Color.White).border(width = 1.dp, color = BorderLight, shape = RoundedCornerShape(16.dp)).padding(24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(
                    modifier = Modifier.size(64.dp).clip(CircleShape).background(Color(0xFFE2E8F0)).border(width = 2.dp, color = Color(0xFFDCE3E8), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (avatarRes != null) {
                        Image(painter = painterResource(avatarRes), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                    } else {
                        Text(text = fullName.take(1).uppercase(), fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TitleBlue)
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = fullName, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TitleBlue, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 2.dp)) {
                        Icon(imageVector = Icons.Default.MailOutline, contentDescription = null, tint = Color(0xFF94A3B8), modifier = Modifier.size(14.dp))
                        Text(text = email, fontSize = 14.sp, color = Color(0xFF64748B), maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
        }

        // Menu items divide-y #535353
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(bottom = 32.dp)) {
            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0xFF535353)))
            ProfileMenuRow(label = "Personal Information", description = "Edit your personal details.", onClick = onPersonalInfo)
            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0xFFE2E8F0)))
            ProfileMenuRow(label = "Log Out", description = "Sign out of your account.", onClick = onLogout)
            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0xFF535353)))
        }

        // Your Journey Details
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(bottom = 32.dp)) {
            Text(text = "Your Journey Details", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 1.sp, color = Color(0xFF94A3B8), modifier = Modifier.padding(bottom = 12.dp))
            Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).border(width = 1.dp, color = BorderLight, shape = RoundedCornerShape(16.dp))) {
                JourneyDetailRow(icon = Icons.Default.CompassCalibration, label = "SPIRITUAL SEASON", value = spiritualSeason)
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(BorderLight))
                JourneyDetailRow(icon = Icons.Default.Phone, label = "PHONE NUMBER", value = phone)
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(BorderLight))
                JourneyDetailRow(icon = Icons.Default.Public, label = "REGION", value = region)
            }
        }

        // Active Support card - hidden if no subscription, show mock empty for now
        // Web shows card only if activeSubscription - we keep empty to stay 1:1 structure without mock subscription
    }
}

@Composable
private fun ProfileMenuRow(label: String, description: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(vertical = 16.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f).padding(end = 12.dp)) {
            Text(text = label, fontSize = 19.sp, color = TextGrey, modifier = Modifier.padding(top = 2.dp))
            Text(text = description, fontSize = 14.sp, color = TextGrey, modifier = Modifier.padding(top = 2.dp))
        }
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFF335E78), modifier = Modifier.size(24.dp))
    }
}

@Composable
private fun JourneyDetailRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(IconBg), contentAlignment = Alignment.Center) {
            Icon(imageVector = icon, contentDescription = null, tint = TitleBlue, modifier = Modifier.size(20.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp, color = Color(0xFF94A3B8))
            Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TitleBlue, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(top = 2.dp))
        }
    }
}

@Preview(showBackground = true, name = "Profile")
@Composable
fun ProfilePreview() {
    LampStandMobileTheme { ProfileContent(fullName = "John Doe", email = "john@example.com", phone = "+234 801 234 5678", region = "Nigeria", spiritualSeason = "Struggling to trust God", avatarRes = null, onBack = {}, onPersonalInfo = {}, onLogout = {}, contentPadding = PaddingValues(0.dp)) }
}

@Preview(showBackground = true, name = "Profile - Empty")
@Composable
fun ProfileEmptyPreview() {
    LampStandMobileTheme { ProfileContent(fullName = "Lampstand Pilgrim", email = "No email linked", phone = "Not provided", region = "Not specified", spiritualSeason = "Not chosen", avatarRes = null, onBack = {}, onPersonalInfo = {}, onLogout = {}) }
}

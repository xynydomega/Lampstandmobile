package com.example.lampstandmobile.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lampstandmobile.navigation.AppDestination
import com.example.lampstandmobile.ui.components.ui.Input
import com.example.lampstandmobile.ui.theme.LampStandMobileTheme

@Composable
fun PersonalInformationScreen(
    onNavigate: (AppDestination) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var firstName by remember { mutableStateOf("John") }
    var lastName by remember { mutableStateOf("Doe") }
    var phone by remember { mutableStateOf("+234 801 234 5678") }
    var showSaveModal by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }

    PersonalInformationContent(
        firstName = firstName, lastName = lastName, phone = phone,
        isSaving = isSaving, showSaveModal = showSaveModal,
        onFirstName = { firstName = it }, onLastName = { lastName = it }, onPhone = { phone = it },
        onBack = { onNavigate(AppDestination.PROFILE) },
        onSave = { showSaveModal = true },
        onConfirm = {
            showSaveModal = false; isSaving = true
            // Simulate updateProfile {firstName,lastName,phone} -> /profile
            isSaving = false; onNavigate(AppDestination.PROFILE)
        },
        onCancel = { showSaveModal = false },
        contentPadding = contentPadding
    )
}

@Composable
fun PersonalInformationContent(
    firstName: String, lastName: String, phone: String,
    isSaving: Boolean, showSaveModal: Boolean,
    onFirstName: (String) -> Unit, onLastName: (String) -> Unit, onPhone: (String) -> Unit,
    onBack: () -> Unit, onSave: () -> Unit, onConfirm: () -> Unit, onCancel: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5)).padding(contentPadding)) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(bottom = 24.dp)) {
            // Header
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(top = 48.dp, bottom = 16.dp), contentAlignment = Alignment.CenterStart) {
                Box(modifier = Modifier.size(32.dp).clickable { onBack() }, contentAlignment = Alignment.Center) {
                    androidx.compose.material3.Text(text = "‹", fontSize = 28.sp, color = Color(0xFF305C76))
                }
            }
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(top = 16.dp, bottom = 32.dp)) {
                Text(text = "Personal Information", fontSize = 22.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF2A5975))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Edit your personal details", fontSize = 15.sp, color = Color(0xFF535353))
            }
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp), verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(4.dp)) {
                Input(label = "First Name", placeholder = "John", value = firstName, onValueChange = onFirstName, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text))
                Input(label = "Last Name", placeholder = "Doe", value = lastName, onValueChange = onLastName, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text))
                Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                    Text(text = "Phone Number", fontSize = 14.sp, color = Color.Black, modifier = Modifier.padding(bottom = 6.dp))
                    OutlinedTextField(
                        value = phone, onValueChange = onPhone, placeholder = { Text(text = "+234 90 0000 000", fontSize = 14.sp, color = Color(0xFF94A3B8)) },
                        modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(6.dp),
                        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp, color = Color(0xFF1E293B)),
                        singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White, focusedBorderColor = Color(0xFF335E78), unfocusedBorderColor = Color(0xFFD8D8D8)),
                        leadingIcon = {
                            androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(6.dp), modifier = Modifier.padding(start = 12.dp)) {
                                Text(text = "🇳🇬", fontSize = 18.sp)
                                Box(modifier = Modifier.size(width = 10.dp, height = 6.dp).background(Color.Transparent)) {
                                    Text(text = "›", fontSize = 10.sp, color = Color(0xFF535353), modifier = Modifier.padding(start = 2.dp))
                                }
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(64.dp))
                Button(
                    onClick = onSave, modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF335E78), contentColor = Color.White, disabledContainerColor = Color(0xFFE2E8F0)),
                    enabled = firstName.isNotBlank() && lastName.isNotBlank() && phone.isNotBlank() && !isSaving
                ) { Text(text = if (isSaving) "Saving..." else "Save Changes", fontSize = 16.sp) }
            }
        }
        if (showSaveModal) {
            AlertDialog(
                onDismissRequest = onCancel,
                title = { Text(text = "Save changes?", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF2A5975)) },
                text = { Text(text = "Are you sure you want to save these changes?", fontSize = 14.sp, color = Color(0xFF535353)) },
                confirmButton = { TextButton(onClick = onConfirm) { Text(text = "Save", color = Color(0xFF335E78), fontWeight = FontWeight.Bold) } },
                dismissButton = { TextButton(onClick = onCancel) { Text(text = "Cancel", color = Color(0xFF535353)) } },
                containerColor = Color.White, shape = RoundedCornerShape(16.dp)
            )
        }
    }
}

@Preview(showBackground = true, name = "Personal Information")
@Composable
fun PersonalInformationPreview() {
    LampStandMobileTheme { PersonalInformationContent(firstName = "John", lastName = "Doe", phone = "+234 801 234 5678", isSaving = false, showSaveModal = false, onFirstName = {}, onLastName = {}, onPhone = {}, onBack = {}, onSave = {}, onConfirm = {}, onCancel = {}) }
}

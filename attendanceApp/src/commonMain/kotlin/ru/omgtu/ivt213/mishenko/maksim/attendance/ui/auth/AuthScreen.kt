package ru.omgtu.ivt213.mishenko.maksim.attendance.ui.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AuthScreen(authComponent: AuthComponent) {
    val state by authComponent.state.collectAsState()
    AuthScreen(
        names = state.names,
        onInputName = authComponent::selectName,
        onInputPassword = authComponent::inputPassword,
        showPassword = state.showPassword,
        onLogIn = authComponent::auth,
        errorText = state.error ?: "",
        isLoading = state.inProgress,
        isError = state.error != null
    )
}

@Composable
fun AuthScreen(
    names: List<String>,
    onInputName: (String) -> Unit,
    onInputPassword: (String) -> Unit,
    showPassword: Boolean,
    onLogIn: () -> Unit,
    errorText: String,
    isLoading: Boolean,
    isError: Boolean
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column {
                Row {
                    Text("Name")
                    Column {
                        var selectName by remember { mutableStateOf("") }
                        var expanded by remember { mutableStateOf(false) }
                        TextField(
                            value = selectName,
                            onValueChange = { selectName = it },
                            modifier = Modifier.clickable { expanded = !expanded },
                            enabled = false,
                            readOnly = true
                        )
                        if (!isLoading) {
                            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                for (name in names) {
                                    Box(Modifier.clickable {
                                        selectName = name
                                        expanded = false
                                        onInputName(name)
                                    }) { Text(name) }
                                }
                            }
                        }

                    }
                }
                if (showPassword) {
                    Row {
                        Text("Password")
                        var password by remember { mutableStateOf("") }
                        TextField(
                            value = password,
                            onValueChange = {
                                password = it
                                onInputPassword(it)
                            },
                            enabled = !isLoading
                        )
                    }
                }
                Button(onLogIn) { Text("Log in") }
            }
        }
        AnimatedVisibility(visible = isError, enter = fadeIn(), exit = fadeOut()) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) { Text(errorText) }
        }
        AnimatedVisibility(visible = isLoading, enter = fadeIn(), exit = fadeOut()) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) { Text("Load") }
        }
    }
}
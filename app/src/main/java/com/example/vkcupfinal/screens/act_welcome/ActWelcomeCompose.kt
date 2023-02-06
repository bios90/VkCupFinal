package com.example.vkcupfinal.screens.act_welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.vkcupfinal.ui.subviews.AppInput
import com.example.vkcupfinal.ui.subviews.AppSpacer
import com.example.vkcupfinal.ui.subviews.BaseInput
import com.example.vkcupfinal.ui.subviews.ButtonPrimary
import com.example.vkcupfinal.ui.theme.AppTheme

@Composable
fun ActWelcomeCompose(
    state: ActWelcomeVm.State,
    onButtonClicked: () -> Unit,
    onInputTextChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.color.Surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppInput(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimens.x8),
            onValueChange = onInputTextChanged,
            value = state.inputText
        )
        AppSpacer(height = AppTheme.dimens.x8)
        ButtonPrimary(text = "Button", onClick = onButtonClicked)
    }
}
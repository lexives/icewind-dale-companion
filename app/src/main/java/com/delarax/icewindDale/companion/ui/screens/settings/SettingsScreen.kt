package com.delarax.icewindDale.companion.ui.screens.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.delarax.icewindDale.companion.data.PreferencesRepo
import com.delarax.icewindDale.companion.extensions.enumCaseToTitleCase
import com.delarax.icewindDale.companion.ui.common.Dimens
import com.delarax.icewindDale.companion.ui.common.PreviewSurface
import com.delarax.icewindDale.companion.ui.theme.ThemeVM

@Composable
fun SettingsScreen() {
    val themeVM: ThemeVM = hiltViewModel()
    SettingsScreenContent(
        themeViewState = themeVM.viewState,
        onSelectNightModeOption = themeVM::setNightModePreference
    )
}

@Composable
fun SettingsScreenContent(
    themeViewState: ThemeVM.ViewState,
    onSelectNightModeOption: (PreferencesRepo.DarkThemePreference) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(Dimens.Spacing.md)
            .verticalScroll(rememberScrollState())
    ) {
        // TODO: make the radio buttons their own component
        Text("Night Mode")
        Row(modifier = Modifier.padding(vertical = Dimens.Spacing.sm)) {
            themeViewState.darkThemePreferences.forEach {
                RadioButton(
                    selected = (it == themeViewState.currentDarkThemePreference),
                    onClick = { onSelectNightModeOption(it) }
                )
                Text(
                    text = it.name.enumCaseToTitleCase(),
                    modifier = Modifier.padding(end = Dimens.Spacing.md)
                )
            }
        }
        Divider(modifier = Modifier.padding(vertical = Dimens.Spacing.md))
    }
}

/**
 * Previews
 */

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DateConversionScreenPreview() {
    PreviewSurface(
        modifier = Modifier.fillMaxSize()
    ) {
        SettingsScreenContent(
            themeViewState = ThemeVM.ViewState(),
            onSelectNightModeOption = {}
        )
    }
}

package com.delarax.icewindDale.companion.ui.theme

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delarax.icewindDale.companion.data.PreferencesRepo
import com.delarax.icewindDale.companion.data.PreferencesRepo.DarkThemePreference
import com.delarax.icewindDale.companion.data.PreferencesRepo.DarkThemePreference.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeVM @Inject constructor(
    private val preferencesRepo: PreferencesRepo
) : ViewModel() {

    var viewState by mutableStateOf(ViewState())
        private set

    data class ViewState(
        val currentDarkThemePreference: DarkThemePreference = MATCH_SYSTEM
    ) {
        val darkThemePreferences: List<DarkThemePreference> = listOf(ON, OFF, MATCH_SYSTEM)
    }

    init {
        viewModelScope.launch {
            preferencesRepo.darkThemeFlow.collect {
                applyNightModePreference(it)
            }
        }
    }

    fun setNightModePreference(preference: DarkThemePreference) {
        viewModelScope.launch {
            preferencesRepo.setDarkThemePreference(preference)
        }
    }

    private fun applyNightModePreference(preference: DarkThemePreference) {
        val mode = when(preference) {
            OFF -> AppCompatDelegate.MODE_NIGHT_NO
            ON -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(mode)
        viewState = viewState.copy(currentDarkThemePreference = preference)
    }
}
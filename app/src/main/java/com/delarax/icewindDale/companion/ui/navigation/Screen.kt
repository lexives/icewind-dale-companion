package com.delarax.icewindDale.companion.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.delarax.icewindDale.companion.R
import com.delarax.icewindDale.companion.ui.common.ActionItem

enum class Screen(
    val route: String,
    @StringRes val titleRes: Int,
    val icon: ImageVector? = null,
    val topBarActionItems: List<ActionItem> = listOf(),
) {
    DATE_CONVERSION(
        route = "dateConversion",
        titleRes = R.string.date_conversion_screen_title,
        icon = Icons.Default.DateRange
    ),
    SETTINGS(
        route = "settings",
        titleRes = R.string.settings_screen_title,
        icon = Icons.Rounded.Settings
    ),
}

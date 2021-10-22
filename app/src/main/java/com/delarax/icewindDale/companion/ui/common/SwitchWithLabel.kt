package com.delarax.icewindDale.companion.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SwitchWithLabel(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    labelText: String = ""
) {
    Column(modifier = modifier) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(text = labelText)
    }
}

@Preview
@Composable
fun SwitchWithLabelPreview() {
    PreviewSurface(modifier = Modifier.height(50.dp).width(100.dp)) {
        SwitchWithLabel(
            labelText = "Label"
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SwitchWithLabelDarkPreview() {
    SwitchWithLabelPreview()
}
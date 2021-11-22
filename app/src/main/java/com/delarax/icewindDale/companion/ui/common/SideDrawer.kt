package com.delarax.icewindDale.companion.ui.common

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.delarax.icewindDale.companion.R
import com.delarax.icewindDale.companion.ui.navigation.Screen
import com.delarax.icewindDale.companion.ui.theme.IcewindDaleTheme

data class DrawerMenuItem(
    @StringRes val nameRes: Int,
    val icon: ImageVector? = null,
    val onClick: () -> Unit = {},
)

@Composable
fun IcewindDaleSideDrawerContent(
    menuItems: List<DrawerMenuItem>
) {
    Column {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(
                top = 24.dp,
                bottom = 0.dp,
                start = 24.dp,
                end = 24.dp
            )
        )
        Divider(
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 0.dp,
                    bottom = 24.dp,
                    start = 24.dp,
                    end = 24.dp
                )
        ) {
            menuItems.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { it.onClick() }
                        .padding(vertical = 6.dp),
                ) {
                    it.icon?.let { icon ->
                        Icon(
                            imageVector = icon,
                            contentDescription = stringResource(id = it.nameRes)
                        )
                        Spacer(modifier = Modifier.padding(end = 12.dp))
                    }
                    Text(
                        text = stringResource(id = it.nameRes)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun IcewindDaleSideDrawerPreview() {
    IcewindDaleTheme {
        Surface {
            IcewindDaleSideDrawerContent(Screen.values().map { screen ->
                DrawerMenuItem(
                    nameRes = screen.titleRes,
                    icon = screen.icon,
                    onClick = {}
                )
            })
        }
    }
}
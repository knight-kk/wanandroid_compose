package com.wkk.wanandroid

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.InsertComment
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.wkk.wanandroid.ui.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WanAndroidApp()
        }
    }
}


@Composable
private fun WanAndroidApp() {
    WanandroidTheme {
        //[转态管理](https://developer.android.google.cn/jetpack/compose/state)
        var currentSection by remember { mutableStateOf(HomeSections.INDEX) }
        Scaffold(
            topBar = { Toolbar() },
            bottomBar = { HomeBottomNav(currentSection) { currentSection = it } },
        ) {
            Crossfade(current = currentSection) {
                when (it) {
                    HomeSections.INDEX -> HomeScreen()
                    HomeSections.QA -> QAScreen()
                    HomeSections.TREE -> TreeScreen()
                    HomeSections.USER -> UserScreen()
                }
            }
        }
    }
}


@Composable
private fun Toolbar(title: String = stringResource(id = R.string.app_name)) =
    TopAppBar(title = { Text(text = title) })


@Composable
private fun HomeBottomNav(
    currentSection: HomeSections,
    onItemSelected: (section: HomeSections) -> Unit,
) {
    BottomNavigation(backgroundColor = MaterialTheme.colors.background) {
        val items = HomeSections.values()
        items.forEach { homeSection ->
            val selected = homeSection == currentSection
            val tintColor =
                if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
            BottomNavigationItem(
                icon = { Icon(imageVector = homeSection.icon, tint = tintColor) },
                selected = selected,
                label = {
                    Text(
                        text = stringResource(homeSection.title),
                        style = TextStyle(color = tintColor)
                    )
                },
                onClick = { onItemSelected(homeSection) })
        }
    }
}

private enum class HomeSections(
    @StringRes val title: Int,
    val icon: ImageVector
) {
    INDEX(R.string.main_tab_home, Icons.Outlined.Home),
    TREE(R.string.main_tab_qa, Icons.Outlined.InsertComment),
    QA(R.string.main_tab_tree, Icons.Outlined.Apps),
    USER(R.string.main_tab_user, Icons.Outlined.Person)
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WanAndroidApp()
}
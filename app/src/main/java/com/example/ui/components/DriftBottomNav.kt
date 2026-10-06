package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.Collections
import androidx.compose.material.icons.outlined.Diamond
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DriftBlack
import com.example.ui.theme.DriftEmerald
import com.example.ui.theme.DriftMuted
import com.example.ui.theme.DriftWhite
import com.example.ui.viewmodel.DriftTab

private data class NavItemData(
    val tab: DriftTab,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

@Composable
fun DriftBottomNav(
    currentTab: DriftTab,
    cartCount: Int,
    onTabSelected: (DriftTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavItemData(DriftTab.HOME, "Home", Icons.Filled.Home, Icons.Outlined.Home, "nav_home"),
        NavItemData(DriftTab.SHOP, "Shop", Icons.Filled.GridView, Icons.Outlined.GridView, "nav_shop"),
        NavItemData(DriftTab.LOOKBOOK, "Lookbook", Icons.Filled.Collections, Icons.Outlined.Collections, "nav_lookbook"),
        NavItemData(DriftTab.BAG, "Bag", Icons.Filled.ShoppingBag, Icons.Outlined.ShoppingBag, "nav_bag"),
        NavItemData(DriftTab.VIP, "VIP Club", Icons.Filled.Diamond, Icons.Outlined.Diamond, "nav_vip")
    )

    NavigationBar(
        modifier = modifier
            .navigationBarsPadding()
            .border(width = 0.5.dp, color = MaterialTheme.colorScheme.outline),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 6.dp
    ) {
        items.forEach { item ->
            val isSelected = currentTab == item.tab

            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(item.tab) },
                icon = {
                    if (item.tab == DriftTab.BAG && cartCount > 0) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = DriftBlack,
                                    contentColor = DriftWhite
                                ) {
                                    Text(text = "$cartCount", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.label,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.label,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 11.sp
                        )
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = DriftMuted,
                    unselectedTextColor = DriftMuted
                ),
                modifier = Modifier.testTag(item.testTag)
            )
        }
    }
}

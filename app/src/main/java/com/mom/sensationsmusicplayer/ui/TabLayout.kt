package com.mom.sensationsmusicplayer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mom.sensationsmusicplayer.ui.theme.DividerClr
import com.mom.sensationsmusicplayer.ui.theme.MainBackgroundColor
import com.mom.sensationsmusicplayer.ui.theme.NotSelectedTabOption
import com.mom.sensationsmusicplayer.ui.theme.SelectedTabOption
import com.mom.sensationsmusicplayer.ui.theme.TextWhite

@Composable
fun TabLayout(viewModel: MainViewModel, musicViewModel: MusicViewModel) {
    val tabIndex = viewModel.tabIndex.observeAsState()
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TabRow(
            selectedTabIndex = tabIndex.value!!,
            modifier = Modifier
                .background(MainBackgroundColor)
                .clickable( interactionSource = interactionSource,
                    indication = null){},
            contentColor = TextWhite,
            indicator = {  },
            divider = {}
        ) {
            viewModel.tabs.forEachIndexed { index, title ->
                val selected = tabIndex.value!! == index
                Tab(
                    modifier = Modifier
                        .background(MainBackgroundColor)
                        .clickable( interactionSource = interactionSource,
                            indication = null){}
                    ,
                    text = {
                        Text(
                            text = title,
                            fontSize = if(selected) 18.sp
                            else 16.sp,
                            color = if (selected) SelectedTabOption
                            else NotSelectedTabOption
                        )
                    },
                    selected = selected,
                    onClick = { viewModel.updateTabIndex(index) },
                    )

            }
        }
        Divider(
            modifier = Modifier
                .padding(horizontal = 15.dp),
            color = DividerClr,
            thickness = 2.dp
        )

        when (tabIndex.value) {
            0 -> SongScreen(viewModel = viewModel, musicViewModel)
            1 -> PlaylistScreen(viewModel = viewModel)
        }

    }
}
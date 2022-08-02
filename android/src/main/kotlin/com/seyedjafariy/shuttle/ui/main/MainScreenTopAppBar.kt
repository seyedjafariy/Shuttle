package com.seyedjafariy.shuttle.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.seyedjafariy.shuttle.R


@Composable
fun MainScreenTopAppBar(filterClicks: () -> Unit) {
    TopAppBar(modifier = Modifier.fillMaxWidth()) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(stringResource(id = R.string.spacex), modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center)
            Icon(
                imageVector = Icons.Default.FilterList,
                "Filters",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable(onClick = filterClicks)
            )
        }
    }
}
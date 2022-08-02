@file:Suppress("OPT_IN_IS_NOT_ENABLED")
@file:OptIn(ExperimentalMaterialApi::class)

package com.seyedjafariy.shuttle.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.seyedjafariy.shuttle.R
import com.seyedjafariy.shuttle.ui.main.state.DateFilterState
import com.seyedjafariy.shuttle.ui.main.state.DateSortingState
import com.seyedjafariy.shuttle.ui.main.state.LaunchFilterState
import com.seyedjafariy.shuttle.ui.main.state.LaunchOperationState

@Composable
fun LaunchFilterDialog(
    initialState: LaunchFilterState,
    onDismiss: () -> Unit,
    onSubmit: (LaunchFilterState) -> Unit
) {
    var launchFilterState by remember {
        mutableStateOf(initialState.operationState)
    }
    var dateSortingState by remember {
        mutableStateOf(initialState.sortingState)
    }

    var dateFiltering by remember {
        mutableStateOf(initialState.dateFilterState)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        buttons = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Button(
                    {
                        onSubmit(
                            LaunchFilterState(
                                launchFilterState, dateSortingState, dateFiltering
                            )
                        )
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = stringResource(id = R.string.submit))
                }
            }
        },
        title = {
            Text(stringResource(id = R.string.filters))
        },
        text = {
            LazyColumn(content = {
                item {
                    Text(stringResource(id = R.string.launch_operation_State))
                }

                item {

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                launchFilterState = LaunchOperationState.Disabled
                            }) {
                        Text(
                            text = stringResource(id = R.string.disabled), modifier = Modifier
                                .weight(1F)
                                .align(Alignment.CenterVertically)
                        )
                        RadioButton(
                            selected = launchFilterState == LaunchOperationState.Disabled,
                            onClick = {
                                launchFilterState = LaunchOperationState.Disabled
                            },
                            modifier = Modifier
                                .weight(.2F)
                                .align(Alignment.CenterVertically),
                        )
                    }
                }

                item {

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                launchFilterState = LaunchOperationState.ONLY_SUCCESSFUL
                            }) {
                        Text(
                            text = stringResource(R.string.only_successful), modifier = Modifier
                                .weight(1F)
                                .align(Alignment.CenterVertically)
                        )
                        RadioButton(
                            selected = launchFilterState == LaunchOperationState.ONLY_SUCCESSFUL,
                            onClick = {
                                launchFilterState = LaunchOperationState.ONLY_SUCCESSFUL
                            },
                            modifier = Modifier
                                .weight(.2F)
                                .align(Alignment.CenterVertically),
                        )
                    }
                }

                item {

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                launchFilterState = LaunchOperationState.ONLY_FAILED
                            }) {
                        Text(
                            text = stringResource(id = R.string.only_failed), modifier = Modifier
                                .weight(1F)
                                .align(Alignment.CenterVertically)
                        )
                        RadioButton(
                            selected = launchFilterState == LaunchOperationState.ONLY_FAILED,
                            onClick = {
                                launchFilterState = LaunchOperationState.ONLY_FAILED
                            },
                            modifier = Modifier
                                .weight(.2F)
                                .align(Alignment.CenterVertically),
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Text(stringResource(id = R.string.sort_by_date))
                }

                item {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                dateSortingState = DateSortingState.ASC
                            }) {
                        Text(
                            text = stringResource(id = R.string.ascending), modifier = Modifier
                                .weight(1F)
                                .align(Alignment.CenterVertically)
                        )
                        RadioButton(
                            selected = dateSortingState == DateSortingState.ASC,
                            onClick = {
                                dateSortingState = DateSortingState.ASC
                            },
                            modifier = Modifier
                                .weight(.2F)
                                .align(Alignment.CenterVertically),
                        )
                    }
                }

                item {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                dateSortingState = DateSortingState.DESC
                            }) {
                        Text(
                            text = stringResource(id = R.string.descending), modifier = Modifier
                                .weight(1F)
                                .align(Alignment.CenterVertically)
                        )
                        RadioButton(
                            selected = dateSortingState == DateSortingState.DESC,
                            onClick = {
                                dateSortingState = DateSortingState.DESC
                            },
                            modifier = Modifier
                                .weight(.2F)
                                .align(Alignment.CenterVertically)
                                .testTag("DESC-BUTTON"),
                        )
                    }
                }

                item {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                dateFiltering = if (dateFiltering == DateFilterState.Off) {
                                    DateFilterState.On(spaceXYearOptions.first())
                                } else {
                                    DateFilterState.Off
                                }
                            }) {
                        Text(
                            text = stringResource(id = R.string.year), modifier = Modifier
                                .weight(1F)
                                .align(Alignment.CenterVertically)
                        )
                        Switch(
                            checked = dateFiltering is DateFilterState.On,
                            onCheckedChange = {
                                dateFiltering = if (it) {
                                    DateFilterState.On(spaceXYearOptions.first())
                                } else {
                                    DateFilterState.Off
                                }
                            },
                            modifier = Modifier
                                .weight(.2F)
                                .align(Alignment.CenterVertically),
                        )
                    }
                }
                if (dateFiltering is DateFilterState.On) {
                    item {
                        YearDropDown(dateFiltering as DateFilterState.On) {
                            dateFiltering = DateFilterState.On(it)
                        }
                    }
                }
            })
        })
}

@Composable
private fun YearDropDown(state: DateFilterState.On, onValueChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
        expanded = !expanded
    }) {
        TextField(
            readOnly = true,
            value = state.date,
            onValueChange = { },
            label = { Text(stringResource(id = R.string.year)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            spaceXYearOptions.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(selectionOption)
                        expanded = false
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}

private val spaceXYearOptions = (2006..2024).toList().map { it.toString() }

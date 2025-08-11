package com.dmendano.megaverse.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmendano.domain.model.MegaverseOptions
import com.dmendano.megaverse.ui.theme.MegaverseTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MegaverseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box {
                        val screenState by mainViewModel.screenState.collectAsState()
                        MainContent(
                            showLoader = screenState.showLoader,
                            onCreateMapClicked = mainViewModel::executeCreateMap,
                            onClearClicked = mainViewModel::executeClear,
                            row = screenState.row,
                            column = screenState.column,
                            onRowChanged = mainViewModel::onRowChanged,
                            onColumnChanged = mainViewModel::onColumnChanged,
                            selectedType = screenState.selectedOption,
                            onObjectTypeSelected = mainViewModel::onObjectTypeSelected,
                            errorMessage = screenState.errorMessage,
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MainContent(
    showLoader: Boolean,
    onCreateMapClicked: () -> Unit,
    onClearClicked: () -> Unit,
    row: Int,
    column: Int,
    onRowChanged: (String) -> Unit,
    onColumnChanged: (String) -> Unit,
    selectedType: MegaverseOptions,
    onObjectTypeSelected: (MegaverseOptions) -> Unit,
    errorMessage: String?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CreateMapButton(onCreateMapClicked, !showLoader)
        if (showLoader) {
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(color = Color.Black)
            Text("Updating map...")
            Spacer(modifier = Modifier.height(4.dp))
        } else {
            InputRowAndColumn(row, column, onRowChanged, onColumnChanged)
            Spacer(modifier = Modifier.height(4.dp))
            MegaverseObjectDropdown(selectedType, onObjectTypeSelected)
            Spacer(modifier = Modifier.height(4.dp))
        }
        ClearButton(onClearClicked, !showLoader)
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            ErrorText(errorMessage)
        }
    }
}

@Composable
private fun CreateMapButton(
    onCreateMapClicked: () -> Unit,
    enabled: Boolean
) {
    Button({ onCreateMapClicked() }, Modifier, enabled = enabled) {
        Text("Create Map")
    }
}

@Composable
private fun ClearButton(
    onClearClicked: () -> Unit,
    enabled: Boolean
) {
    Button({ onClearClicked() }, Modifier, enabled = enabled) {
        Text("Clear")
    }
}

@Composable
private fun InputRowAndColumn(
    row: Int,
    column: Int,
    onRowChanged: (String) -> Unit,
    onColumnChanged: (String) -> Unit
) {
    Row(Modifier.padding(32.dp)) {
        OutlinedTextField(
            value = row.toString(),
            onValueChange = { onRowChanged(it) },
            label = { Text("Row") },
            modifier = Modifier
                .padding(4.dp)
                .weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = column.toString(),
            onValueChange = { onColumnChanged(it) },
            label = { Text("Column") },
            modifier = Modifier
                .padding(4.dp)
                .weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
private fun MegaverseObjectDropdown(
    selectedType: MegaverseOptions,
    onTypeSelected: (MegaverseOptions) -> Unit,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }
    val items = MegaverseOptions.entries.map { it to it.name }

    Box(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize(Alignment.TopStart)
    ) {
        OutlinedButton(
            onClick = { expanded = true },
            enabled = enabled
        ) {
            Text(selectedType.name)
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "Select object type"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { (type, label) ->
                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun ErrorText(errorMessage: String?) {
    Text(
        modifier = Modifier.padding(horizontal = 32.dp),
        text = errorMessage ?: "",
        color = Color.Red,
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MainPreview() {
    MegaverseTheme {
        MainContent(
            showLoader = false,
            onCreateMapClicked = {},
            onClearClicked = {},
            row = 1,
            column = 1,
            onRowChanged = {},
            onColumnChanged = {},
            selectedType = MegaverseOptions.POLYANET,
            errorMessage = "Error",
            onObjectTypeSelected = {}
        )
    }
}
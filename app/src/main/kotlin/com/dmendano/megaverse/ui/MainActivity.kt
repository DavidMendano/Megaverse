package com.dmendano.megaverse.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                        val showLoader by mainViewModel.showLoader.collectAsState()
                        MainContent(
                            showLoader,
                            { mainViewModel.executeCreateMap() },
                            { mainViewModel.executeClear() },
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
        }
        ClearButton(onClearClicked, !showLoader)
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MainPreview() {
    MegaverseTheme {
        MainContent(
            showLoader = false,
            onCreateMapClicked = {},
            onClearClicked = {}
        )
    }
}
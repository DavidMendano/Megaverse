package com.dmendano.megaverse.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                    MainContent(
                        { mainViewModel.executePhase1() },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
private fun MainContent(
    onPhase1Clicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Phase1Button(onPhase1Clicked)
    }
}

@Composable
private fun Phase1Button(
    onPhase1Clicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button({ onPhase1Clicked() }, modifier) {
        Text("Phase 1")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MainPreview() {
    MegaverseTheme {
        MainContent({})
    }
}
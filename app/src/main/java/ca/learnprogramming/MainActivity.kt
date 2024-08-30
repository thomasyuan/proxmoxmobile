// app/src/main/java/ca/learnprogramming/MainActivity.kt
package ca.learnprogramming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.learnprogramming.viewmodel.MainViewModel
import ca.learnprogramming.ui.NodeItem
import ca.learnprogramming.ui.theme.ProxmoxMobileTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchNodes()

        setContent {
            ProxmoxMobileTheme {
                val nodes by viewModel.nodes.collectAsState()
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(nodes) { node ->
                            NodeItem(node)
                        }
                    }
                }
            }
        }
    }
}
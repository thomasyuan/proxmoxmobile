package ca.learnprogramming.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.learnprogramming.model.Node

@Composable
fun NodeItem(node: Node) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Name: ${node.node}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Status: ${node.status}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "CPU: ${node.cpu}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Max CPU: ${node.maxcpu}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Memory: ${node.mem}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Max Memory: ${node.maxmem}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Uptime: ${node.uptime}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
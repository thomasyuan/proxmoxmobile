package ca.learnprogramming.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.learnprogramming.model.Node
import ca.learnprogramming.network.ProxmoxApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val TAG = "MainViewModel"
    private val _nodes = MutableStateFlow<List<Node>>(emptyList())
    val nodes: StateFlow<List<Node>> = _nodes

    fun fetchNodes() {
        viewModelScope.launch {
            try {
                _nodes.value = ProxmoxApiService.getNodes()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch nodes", e)
            }
        }
    }
}
package ca.learnprogramming

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ca.learnprogramming.network.ProxmoxApiService
import ca.learnprogramming.ui.LoginScreen
import ca.learnprogramming.ui.theme.ProxmoxMobileTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProxmoxMobileTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val coroutineScope = rememberCoroutineScope()
                var errorMessage by remember { mutableStateOf<String?>(null) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { CenteredSnackbarHost(snackbarHostState) }
                ) { innerPadding ->
                    ScaffoldContent(
                        snackbarHostState = snackbarHostState,
                        coroutineScope = coroutineScope,
                        errorMessage = errorMessage,
                        onErrorMessageChange = { errorMessage = it }
                    )
                }
            }
        }
    }

    @Composable
    private fun CenteredSnackbarHost(snackbarHostState: SnackbarHostState) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    actionColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    @Composable
    private fun ScaffoldContent(
        snackbarHostState: SnackbarHostState,
        coroutineScope: CoroutineScope,
        errorMessage: String?,
        onErrorMessageChange: (String?) -> Unit
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoginScreen { url, username, password ->
                handleLogin(url, username, password, coroutineScope, snackbarHostState)
            }
            errorMessage?.let {
                showSnackbar(it, snackbarHostState, coroutineScope) {
                    onErrorMessageChange(null)
                }
            }
        }
    }

    data class ProcessedCredentials(
        val url: String,
        val username: String,
        val password: String
    )

    private fun handleLogin(
        url: String,
        username: String,
        password: String,
        coroutineScope: CoroutineScope,
        snackbarHostState: SnackbarHostState
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                ProxmoxApiService.init(url)
                ProxmoxApiService.login(username, password)

                TokenManager.saveAccessToken(this@LoginActivity, ProxmoxApiService.getAccessToken())
                TokenManager.saveUrlAndUsername(this@LoginActivity, url, username)
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            } catch (e: Exception) {
                Log.e("LoginError", "Login failed", e)
                showSnackbar("Login failed: ${e.message}", snackbarHostState, coroutineScope)
            }
        }
    }

    private fun showSnackbar(
        message: String,
        snackbarHostState: SnackbarHostState,
        coroutineScope: CoroutineScope,
        onDismiss: () -> Unit = {}
    ) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message)
            onDismiss()
        }
    }
}
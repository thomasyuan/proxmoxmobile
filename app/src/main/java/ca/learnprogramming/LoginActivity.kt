package ca.learnprogramming

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ca.learnprogramming.network.ProxmoxApi
import ca.learnprogramming.network.RetrofitInstance
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoginScreen { url, username, password ->
                            // Handle login logic here
                            CoroutineScope(Dispatchers.IO).launch {
                                try {
                                    val api = RetrofitInstance.getInstance(url+"/api2/json/").create(ProxmoxApi::class.java)
                                    val accessTokenResponse = api.getAccessToken(username+"@pam", password)
                                    val accessToken = accessTokenResponse.data.ticket
                                    val csrfToken = accessTokenResponse.data.CSRFPreventionToken
                                    Log.d("AccessToken", accessToken)
                                    Log.d("CSRFToken", csrfToken)
                                    // Store the access token
                                    RetrofitInstance.setAccessToken(accessToken)
                                    // Navigate to the next UI
                                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                } catch (e: Exception) {
                                    Log.e("LoginError", "Login failed", e)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
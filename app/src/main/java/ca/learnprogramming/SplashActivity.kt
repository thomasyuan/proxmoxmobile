package ca.learnprogramming

import TokenManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import ca.learnprogramming.network.ProxmoxApi
import ca.learnprogramming.network.ProxmoxApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch {
            val token = TokenManager.getAccessToken(this@SplashActivity)
            val (url, _) = TokenManager.getUrlAndUsername(this@SplashActivity)
            Log.d("SplashActivity", "URL: $url, Token: $token")
            var hasSession = true
            if (token != null && url != null) {
                ProxmoxApiService.init(url)
                hasSession = ProxmoxApiService.isValidToken(token)
            }
            if (hasSession) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
            finish()
        }
    }
}
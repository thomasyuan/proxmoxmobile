package ca.learnprogramming

import TokenManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import ca.learnprogramming.network.ProxmoxApi
import ca.learnprogramming.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch {
            val token = TokenManager.getAccessToken(this@SplashActivity)
            val (url, _) = TokenManager.getUrlAndUsername(this@SplashActivity)

            Log.d("SplashActivity", "Token: $token, URL: $url")
            if (token != null && url != null && isValidToken(url, token)) {
                RetrofitInstance.setAccessToken(token)
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
            finish()
        }
    }

    private suspend fun isValidToken(url: String, token: String): Boolean {
        return try {
            Log.d("SplashActivity", "Validating token: $token")
            Log.d("SplashActivity", "URL: $url")
            val processedUrl = url.trim().removeSuffix("/") + "/api2/json/"

            val api = RetrofitInstance.getInstance(processedUrl).create(ProxmoxApi::class.java)
            val response = api.validateToken("PVEAuthCookie=$token")
            Log.d("SplashActivity", "Response code: ${response.code()}")
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}
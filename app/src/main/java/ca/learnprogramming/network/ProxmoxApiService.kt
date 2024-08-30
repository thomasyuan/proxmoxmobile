package ca.learnprogramming.network

import android.content.Intent
import android.util.Log
import ca.learnprogramming.MainActivity
import ca.learnprogramming.model.Node
import ca.learnprogramming.model.NodesResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProxmoxApiService {
    private const val TAG = "ProxmoxApiService"
    private lateinit var api: ProxmoxApi
    private lateinit var accessToken: String

    fun init(url: String) {
        try {
            val client = UnsafeOkHttpClient.getUnsafeOkHttpClient()
            val baseUrl = "$url/api2/json/"
            api = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProxmoxApi::class.java)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize API", e)
        }
    }

    fun setAccessToken(token: String) {
        accessToken = token
    }

    fun getAccessToken(): String {
        return accessToken
    }

    suspend fun login(username: String, password: String): Boolean {
        val accessTokenResponse = api.getAccessToken("$username@pam", password)
        Log.d(TAG, "Response: $accessTokenResponse")
        setAccessToken(accessTokenResponse.data.ticket)
        return true
    }

    suspend fun isValidToken(token: String): Boolean {
        try {
            val response = api.validateToken("PVEAuthCookie=$token")
            Log.d(TAG, "Response: $response")
            setAccessToken(token)
            return response.isSuccessful
        } catch (e: Exception) {
            Log.e(TAG, "Failed to validate token", e)
            return false
        }
    }

    suspend fun getNodes(): List<Node> {
        val response = api.getNodes("PVEAuthCookie=$accessToken")
        Log.d(TAG, "Response: $response")
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception("Failed to fetch nodes")
        }
    }
}
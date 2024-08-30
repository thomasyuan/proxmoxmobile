import android.content.Context
import android.content.SharedPreferences

object TokenManager {
    private const val PREFS_NAME = "prefs"
    private const val ACCESS_TOKEN_KEY = "access_token"
    private const val URL_KEY = "url"
    private const val USERNAME_KEY = "username"

    fun saveAccessToken(context: Context, token: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(ACCESS_TOKEN_KEY, token)
        editor.apply()
    }

    fun getAccessToken(context: Context): String? {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(ACCESS_TOKEN_KEY, null)
    }

    fun clearAccessToken(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.remove(ACCESS_TOKEN_KEY)
        editor.apply()
    }

    fun saveUrlAndUsername(context: Context, url: String, username: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(URL_KEY, url)
        editor.putString(USERNAME_KEY, username)
        editor.apply()
    }

    fun getUrlAndUsername(context: Context): Pair<String?, String?> {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val url = prefs.getString(URL_KEY, null)
        val username = prefs.getString(USERNAME_KEY, null)
        return Pair(url, username)
    }
}
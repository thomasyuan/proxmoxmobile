// app/src/main/java/ca/learnprogramming/network/AccessTokenResponse.kt
package ca.learnprogramming.model

data class AccessTokenResponse(
    val data: AccessTokenData
)

data class AccessTokenData(
    val ticket: String,
    val CSRFPreventionToken: String
)
// app/src/main/java/ca/learnprogramming/network/ProxmoxApi.kt
package ca.learnprogramming.network

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ProxmoxApi {
    @FormUrlEncoded
    @POST("access/ticket")
    suspend fun getAccessToken(
        @Field("username") username: String,
        @Field("password") password: String
    ): AccessTokenResponse

    @GET("version")
    suspend fun validateToken(@Header("Authorization") token: String): Response<AccessTokenResponse>

    @GET("access/ticket/info")
    suspend fun getTicketInfo(
        @Header("Authorization") authHeader: String
    ): TicketInfoResponse

    @GET("nodes")
    suspend fun getNodes(
        @Header("Authorization") authHeader: String
    ): NodesResponse

    @GET("nodes/{node}/status")
    suspend fun getNodeStatus(
        @Header("Authorization") authHeader: String,
        @Path("node") node: String
    ): NodeStatusResponse
}
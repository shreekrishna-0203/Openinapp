package com.example.openinapp6

import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("api/v1/dashboardNew")
    suspend fun getDashboardData(
        @Header("Authorization") token: String = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8t bjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI"
    ): DashboardData
    @GET("api/v1/top-links")
    suspend fun getTopLinks(): List<Link>

    @GET("api/v1/recent-links")
    suspend fun getRecentLinks(): List<Link>

    @GET("api/v1/all-links")
    suspend fun getAllLinks(): List<Link>
}

package com.example.openinapp6

class DashboardRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getDashboardData(): DashboardData {
        return apiService.getDashboardData()
    }
    suspend fun getTopLinks(): List<Link> {
        return apiService.getTopLinks()
    }

    suspend fun getRecentLinks(): List<Link> {
        return apiService.getRecentLinks()
    }

    suspend fun getAllLinks(): List<Link> {
        return apiService.getAllLinks()
    }
}
package com.example.openinapp6

data class DashboardData(
    val todayClicks: Int,
    val topLocation: String,
    val topSource: String,
    val chartData: List<ChartDataPoint>,
    val topLinks: List<Link>,
    val recentLinks: List<Link>
)
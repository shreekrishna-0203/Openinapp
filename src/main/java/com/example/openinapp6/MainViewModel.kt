package com.example.openinapp6

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = DashboardRepository()

    private val _dashboardData = MutableLiveData<DashboardData>()
    val dashboardData: LiveData<DashboardData> = _dashboardData

    init {
        fetchDashboardData()
    }

    private fun fetchDashboardData() {
        viewModelScope.launch {
            try {
                val result = repository.getDashboardData()
                _dashboardData.value = result
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    private val _links = MutableLiveData<List<Link>>()
    val links: LiveData<List<Link>> = _links

    fun getTopLinks() {
        viewModelScope.launch {
            val topLinks = repository.getTopLinks()
            _links.value = topLinks
        }
    }

    fun getRecentLinks() {
        viewModelScope.launch {
            val recentLinks = repository.getRecentLinks()
            _links.value = recentLinks
        }
    }
    fun loadTopLinks() {
        viewModelScope.launch {
            val topLinks = repository.getTopLinks()
            _links.value = topLinks
        }
    }

    fun loadRecentLinks() {
        viewModelScope.launch {
            val recentLinks = repository.getRecentLinks()
            _links.value = recentLinks
        }
    }

    fun loadAllLinks() {
        viewModelScope.launch {
            val allLinks = repository.getAllLinks()
            _links.value = allLinks
        }
    }

}
package com.example.openinapp6

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var tvGreeting: TextView
    private lateinit var tvName: TextView
    private lateinit var chart: LineChart
    private lateinit var tvTodayClicks: TextView
    private lateinit var tvTopLocation: TextView
    private lateinit var tvTopSource: TextView
    private lateinit var btnViewAnalytics: Button
    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerViewLinks: RecyclerView
    private lateinit var btnViewAllLinks: Button
    private lateinit var btnTalkWithUs: Button
    private lateinit var btnFAQ: Button
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupViewModel()
        setupObservers()
        setupClickListeners()
        updateGreeting()
    }

    private fun initViews() {
        tvGreeting = findViewById(R.id.tvGreeting)
        tvName = findViewById(R.id.tvName)
        chart = findViewById(R.id.chart)
        tvTodayClicks = findViewById(R.id.tvTodayClicks)
        tvTopLocation = findViewById(R.id.tvTopLocation)
        tvTopSource = findViewById(R.id.tvTopSource)
        btnViewAnalytics = findViewById(R.id.btnViewAnalytics)
        tabLayout = findViewById(R.id.tabLayout)
        recyclerViewLinks = findViewById(R.id.recyclerViewLinks)
        btnViewAllLinks = findViewById(R.id.btnViewAllLinks)
        btnTalkWithUs = findViewById(R.id.btnTalkWithUs)
        btnFAQ = findViewById(R.id.btnFAQ)
        bottomNavView = findViewById(R.id.bottomNavView)
        setupBottomNavigation()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.dashboardData.observe(this) { data ->
            // Update UI with dashboard data
            tvTodayClicks.text = data.todayClicks.toString()
            tvTopLocation.text = data.topLocation
            tvTopSource.text = data.topSource
            updateChart(data.chartData)
            // Update RecyclerView with links data
        }
    }

    private fun setupClickListeners() {
        btnViewAnalytics.setOnClickListener {
            // Instead of opening a new activity, we can show a dialog or a bottom sheet with analytics info
            showAnalyticsDialog()
        }

        btnViewAllLinks.setOnClickListener {
            // Instead of opening a new activity, we can expand the current list to show all links
            viewModel.loadAllLinks()
        }

        btnTalkWithUs.setOnClickListener {
            // Open WhatsApp chat or a contact form
            val phoneNumber = "+91 8105639425" // Replace with your WhatsApp business number
            val message = "Hello, I need assistance with OpenInApp."

            val uri = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}")
            val intent = Intent(Intent.ACTION_VIEW, uri)

            try {
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "WhatsApp is not installed on your device", Toast.LENGTH_SHORT).show()
            }
        }

        btnFAQ.setOnClickListener {
            // Open FAQ web page
            val faqUrl = "https://yourwebsite.com/faq" // Replace with your FAQ page URL
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(faqUrl))
            startActivity(intent)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> viewModel.loadTopLinks()
                    1 -> viewModel.loadRecentLinks()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }





    private fun setupBottomNavigation() {
        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_links -> {
                    // Handle links selection
                    true
                }
                R.id.navigation_courses -> {
                    // Handle courses selection
                    true
                }
                R.id.navigation_campaigns -> {
                    // Handle campaigns selection
                    true
                }
                R.id.navigation_profile -> {
                    // Handle profile selection
                    true
                }
                else -> false
            }
        }
    }



    private fun showAnalyticsDialog() {
        // Create and show a dialog with analytics information
        // This is a placeholder implementation. You should create a custom dialog or use a library like MaterialDialogs
        AlertDialog.Builder(this)
            .setTitle("Analytics")
            .setMessage("Here you can display some analytics information")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun loadTopLinks() {
        viewModel.getTopLinks()
    }

    private fun loadRecentLinks() {
        viewModel.getRecentLinks()
    }



    private fun updateGreeting() {
        val calendar = Calendar.getInstance()
        val greeting = when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "Good morning"
            in 12..15 -> "Good afternoon"
            else -> "Good evening"
        }
        tvGreeting.text = greeting
    }

    private fun updateChart(chartData: List<ChartDataPoint>) {
        // Convert ChartDataPoint to Entry objects
        val entries = chartData.mapIndexed { index, dataPoint ->
            Entry(index.toFloat(), dataPoint.clicks.toFloat())
        }

        // Create a LineDataSet with the entries
        val dataSet = LineDataSet(entries, "Clicks").apply {
            color = ContextCompat.getColor(this@MainActivity, R.color.blue)
            setCircleColor(ContextCompat.getColor(this@MainActivity, R.color.blue))
            lineWidth = 2f
            circleRadius = 4f
            setDrawCircleHole(false)
            valueTextSize = 9f
            setDrawFilled(true)
            fillColor = ContextCompat.getColor(this@MainActivity, R.color.blue)
        }

        // Create LineData object with the dataset
        val lineData = LineData(dataSet)

        // Set the data to the chart
        chart.data = lineData

        // Configure X-axis
        val xAxis = chart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(chartData.map { it.date })
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = -45f

        // Customize the chart
        chart.apply {
            description.isEnabled = false
            legend.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            axisRight.isEnabled = false
            axisLeft.setDrawGridLines(false)
            xAxis.setDrawGridLines(false)
        }

        // Refresh the chart
        chart.invalidate()
    }
}
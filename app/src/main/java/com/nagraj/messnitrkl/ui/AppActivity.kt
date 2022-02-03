package com.nagraj.messnitrkl.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import com.google.android.material.navigation.NavigationView
import com.nagraj.messnitrkl.R
import com.nagraj.messnitrkl.common.Constants
import com.nagraj.messnitrkl.common.DataStorePreference
import com.nagraj.messnitrkl.databinding.ActivityAppBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var dataStoreManager: DataStorePreference
    private var rollNo: String? = ""
    private var hostel: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataStoreManager = DataStorePreference(this)
        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_complete_menu, R.id.nav_faqs
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        NavigationUI.setupWithNavController(navView, navController);
        navView.setNavigationItemSelectedListener {
            val handled = onNavDestinationSelected(it, navController)
            if (!handled) {
                when (it.itemId) {
                    R.id.nav_share -> {
                        share()
                        true
                    }
                    R.id.nav_rate -> {
                        rateUs()
                        true
                    }
                    R.id.nav_logout -> {
                        logOut()
                        true
                    }
                    else -> false
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            handled;
        }
        getStoreValues()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun share() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, Constants.APK_SHARE_MSG)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share my app")
        startActivity(shareIntent)
    }

    private fun rateUs() {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }

    private fun logOut() {
        lifecycleScope.launch(Dispatchers.IO) {
            dataStoreManager.setLogout()
            gotoLoginPage()
        }
    }

    private fun gotoLoginPage() {
        val loginActivity = Intent(this, LoginActivity::class.java)
        finish()
        startActivity(loginActivity)
    }

    private fun getStoreValues() {
        val header: View = binding.navView.getHeaderView(0)
        lifecycleScope.launch(
            Dispatchers.IO
        ) {
            dataStoreManager.getHostel.collect {
                withContext(Dispatchers.Main) {
                    hostel = it
                    val tvHostel: TextView = header.findViewById(R.id.tv_hostel);
                    tvHostel.text = hostel
                }
            }
        }

        lifecycleScope.launch(
            Dispatchers.IO
        ) {
            dataStoreManager.getRollNo.collect {
                withContext(Dispatchers.Main) {
                    rollNo = it
                    val tvRollNo: TextView = header.findViewById(R.id.tv_roll_no);
                    tvRollNo.text = rollNo
                }
            }
        }
    }

}
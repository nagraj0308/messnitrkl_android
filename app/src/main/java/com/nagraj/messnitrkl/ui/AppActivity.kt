package com.nagraj.messnitrkl.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import com.google.android.material.navigation.NavigationView
import com.nagraj.messnitrkl.R
import com.nagraj.messnitrkl.common.Constants
import com.nagraj.messnitrkl.databinding.ActivityAppBinding


class AppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppBinding
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                        logout()
                        true
                    }
                    else -> false
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            handled;

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun share() {
        Constants.toast(this, "share");
    }

    private fun rateUs() {
        Constants.toast(this, "rate");
    }

    private fun logout() {
        Constants.toast(this, "logout");
    }
}
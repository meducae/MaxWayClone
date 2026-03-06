package uz.gita.maxwayclone

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import uz.gita.maxwayclone.presentation.basket.BasketFragment
import uz.gita.maxwayclone.presentation.basket.BasketViewModel
import uz.gita.maxwayclone.presentation.basket.BasketViewModelFactory
import kotlin.getValue

class MainActivity : AppCompatActivity() {
    private val viewModel: BasketViewModel by viewModels { BasketViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
        window.statusBarColor =
            ContextCompat.getColor(this, R.color.white)

        setupBars()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment

        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val basketBadge = bottomNav.getOrCreateBadge(R.id.nav_basket)
        basketBadge.isVisible = false
        lifecycleScope.launchWhenStarted {
            viewModel.getBasketItemCount.collect { count ->
                basketBadge.number = count
                basketBadge.isVisible = count > 0
            }
        }

        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_home, R.id.nav_profile, R.id.nav_orders, R.id.nav_basket -> {
                    bottomNav.visibility = android.view.View.VISIBLE
                }

                else -> {
                    bottomNav.visibility = android.view.View.GONE
                }
            }
        }
    }

    fun setupBars() {
        window.statusBarColor = getColor(R.color.root_color)
        window.navigationBarColor = getColor(R.color.white)

        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }
    }
}
package uz.gita.maxwayclone.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.presentation.home.HomeFragment

class SplashFragment: Fragment(R.layout.fragment_splash) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 2.5 soniya kutib, keyin Home yoki Order sahifasiga o'tish
        // SplashFragment.kt
        viewLifecycleOwner.lifecycleScope.launch {
            delay(2500)

            val navController = findNavController()
            // AGAR hozirgi manzil hali ham Splash bo'lsa, keyin sakraymiz
            if (navController.currentDestination?.id == R.id.splashFragment) {
                navController.navigate(R.id.action_splashFragment_to_nav_home)
            }
        }
    }
}
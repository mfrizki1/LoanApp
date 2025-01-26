package id.calocallo.loanapp

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import id.calocallo.loanapp.databinding.ActivityMainBinding
import id.calocallo.loanapp.utils.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupNavHost()

    }

    private fun setupNavHost() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcvLoan) as NavHostFragment
        navController = navHostFragment.navController

        val inflater = navController.navInflater
        val graph = inflater.inflate(R.navigation.navigation_loan)
        navController.graph = graph

        appBarConfiguration = AppBarConfiguration(navController.graph)

        setSupportActionBar(binding.toolbarBT)
        setupActionBarWithNavController(
            navController,
            appBarConfiguration,
        )
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)


        onBackPressedDispatcher.addCallback(this) {
            if (!navController.popBackStack()) {
                finish()
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
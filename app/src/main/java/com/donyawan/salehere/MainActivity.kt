package com.donyawan.salehere

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.donyawan.salehere.databinding.ActivityMainBinding
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import ui.achievement.AchievementFragment
import ui.goal.GoalFragment
import ui.home.HomeFragment
import java.net.URISyntaxException
import kotlin.coroutines.coroutineContext


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val mainViewmodel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setupView()
        observe()
        setupSocket()
    }

    private fun setupSocket() {
        var mSocket: Socket? = null
        try {
            mSocket = IO.socket(socketPath)
        } catch (e: URISyntaxException) {
            Log.d("socket", e.toString())
        }
        mSocket?.on("new notification") {
            this@MainActivity.runOnUiThread {
                run {
                    binding.homeBottomNavMenu.getOrCreateBadge(0)
                }
            }
        }
        mSocket?.connect()
    }


    override fun onAttachFragment(fragment: android.app.Fragment?) {
        super.onAttachFragment(fragment)
        loadFragment(HomeFragment.newInstance())
    }

    private fun setupView() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding.homeBottomNavMenu.setOnItemSelectedListener { selected ->
            when (selected.itemId) {
                R.id.menu_home -> {
                    loadFragment(HomeFragment.newInstance())
                    true
                }

                R.id.menu_wallet -> {
                    true
                }

                R.id.menu_achieve -> {
                    loadFragment(AchievementFragment.newInstance())
                    true
                }

                R.id.menu_profile -> {
                    true
                }

                else -> false
            }
        }
    }

    private fun observe() {
        with(mainViewmodel) {
            navigateToGoalFragment.observe(this@MainActivity) {
                loadFragment(GoalFragment.newInstance())
            }

            navigateToHomeFragment.observe(this@MainActivity) {
                loadFragment(HomeFragment.newInstance())
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.home_fragment_view, fragment)
        transaction.commit()
    }

    companion object {
        val socketPath = "https://px-socket-emitter.saleherethailand.com"
    }
}
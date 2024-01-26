package com.donyawan.salehere

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ui.goal.GoalViewModel
import ui.home.HomeViewModel

val appModule = module {
    viewModel { HomeViewModel() }
    viewModel { MainViewModel() }
    viewModel { GoalViewModel() }
}
package com.donyawan.salehere

import androidx.lifecycle.ViewModel
import model.home.GoalModel
import model.home.GoalStatus
import model.home.GoalTypes
import org.joda.time.LocalDate
import utils.SingleLiveEvent
import java.math.BigDecimal

class MainViewModel : ViewModel() {

    val navigateToGoalFragment = SingleLiveEvent<Unit>()
    val navigateToHomeFragment = SingleLiveEvent<Unit>()

    fun navigateToGoal() {
        navigateToGoalFragment.value = Unit
    }

    fun navigateToHome() {
        navigateToHomeFragment.value = Unit
    }

    val mockGoalList = mutableListOf(
        GoalModel(
            title = "ไปเที่ยวญี่ปุ่น",
            type = GoalTypes.Travel,
            targetDate = LocalDate(2024,11,20),
            targetBudget = BigDecimal(20000),
            currentBudget = BigDecimal(10000),
            bankAccount = "xxxx-xxxx-xxxx-xxxx",
            status = GoalStatus.Good
        ),
        GoalModel(
            title = "ซื้อกองทุนรวม",
            type = GoalTypes.Education,
            targetDate = LocalDate(2024,2,20),
            targetBudget = BigDecimal(6000),
            currentBudget = BigDecimal(500),
            bankAccount = "xxxx-xxxx-xxxx-xxxx",
            status = GoalStatus.Unhappy
        ),
        GoalModel(
            title = "ชุดว่ายน้ำ",
            type = GoalTypes.Clothing,
            targetDate = LocalDate(2024,12,20),
            targetBudget = BigDecimal(5000),
            currentBudget = BigDecimal(3500),
            bankAccount = "xxxx-xxxx-xxxx-xxxx",
            status = GoalStatus.Good
        )
    )

    fun addGoal(goal: GoalModel) {
        mockGoalList.add(goal)
    }
}
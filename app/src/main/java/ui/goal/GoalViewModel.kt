package ui.goal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import model.home.GoalModel
import model.home.GoalStatus
import model.home.GoalTypes
import org.joda.time.LocalDate
import java.math.BigDecimal

class GoalViewModel : ViewModel() {
    var goalModel = GoalModel()
    val submitModel = MutableLiveData<GoalModel>()

    fun setBankAccount(bankAccount: String) {
        if (bankAccount != "Select Bank Account") {
            goalModel = goalModel.copy(bankAccount = bankAccount)
        }
    }

    fun setTargetDate(date: LocalDate) {
        goalModel = goalModel.copy(targetDate = date) ?: GoalModel(targetDate = date)
    }

    fun onSelectedType(position: Int) {
        val type = when (position) {
            0 -> GoalTypes.Travel
            1 -> GoalTypes.Education
            2 -> GoalTypes.Invest
            3 -> GoalTypes.Clothing
            else -> GoalTypes.Travel
        }
        goalModel = goalModel?.copy(type = type) ?: GoalModel(type = type)
    }

    fun onClickSubmitButton(goal: String, targetBudget: String, monthly: String) {
        val finalModel = goalModel.copy(
            title = goal,
            targetBudget = if (targetBudget.isNullOrEmpty()) BigDecimal.ZERO else targetBudget.toBigDecimal(),
            status = GoalStatus.Unhappy
        )
        goalModel = finalModel
        submitModel?.value = finalModel
    }
}
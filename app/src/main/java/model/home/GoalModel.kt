package model.home

import org.joda.time.LocalDate
import java.math.BigDecimal

data class GoalModel(
    var title: String? = null,
    var type: GoalTypes? = null,
    var targetBudget: BigDecimal = BigDecimal.ZERO,
    var targetDate: LocalDate? = null,
    var bankAccount: String? = null,
    var currentBudget: BigDecimal = BigDecimal.ZERO,
    var status: GoalStatus? = null
)

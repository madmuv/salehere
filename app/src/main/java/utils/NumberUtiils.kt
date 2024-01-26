package utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

fun BigDecimal.addComma(): String {
    val df = DecimalFormat("###,##0.00")
    return df.format(this.toDouble())
}

fun calculateProgress(targetBudget: BigDecimal, currentBudget: BigDecimal): Float {
    return 1f - ((targetBudget - currentBudget).divide(targetBudget, 2, RoundingMode.HALF_UP)).toFloat()
}
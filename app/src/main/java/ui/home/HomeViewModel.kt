package ui.home

import androidx.lifecycle.ViewModel
import model.home.GoalModel
import model.home.GoalStatus
import model.home.GoalTypes
import org.joda.time.LocalDate
import java.math.BigDecimal

class HomeViewModel : ViewModel() {

    val mockFirstBanner: Pair<String, List<String>> = Pair(
        "Best Offer",
        listOf(
            "text1",
            "text2",
            "text3",
            "text4",
            "text5",
        )
    )

    val mockSecondBanner: Pair<String, List<String>> = Pair(
        "Suggest for you",
        listOf(
            "text1",
            "text2",
            "text3",
            "text4",
            "text5",
        )
    )
}
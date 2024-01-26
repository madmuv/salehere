package ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.donyawan.salehere.MainViewModel
import com.donyawan.salehere.R
import com.donyawan.salehere.databinding.FragmentHomeBinding
import model.home.GoalModel
import model.home.GoalStatus
import model.home.GoalTypes
import org.joda.time.LocalDate
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import utils.addComma
import utils.calculateProgress
import utils.diffDays
import utils.font_ibm
import java.math.BigDecimal

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(layoutInflater, container, false).apply {
            homeComposeView.setContent {
                HomeContent(viewModel = viewModel)
            }
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    private fun HomeContent(viewModel: HomeViewModel) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            GoalsSection(mainViewModel.mockGoalList)

            Button(
                onClick = {
                    mainViewModel.navigateToGoal()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.red_CA5D48)
                ),
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                    contentDescription = ""
                )
                Text(
                    text = stringResource(id = R.string.new_goals_label),
                    color = colorResource(id = R.color.white),
                    fontFamily = font_ibm,
                    fontWeight = FontWeight.Bold
                )
            }

            BannerSection(
                sectionHeader = viewModel.mockFirstBanner.first,
                list = viewModel.mockFirstBanner.second
            )
            BannerSection(
                sectionHeader = viewModel.mockSecondBanner.first,
                list = viewModel.mockSecondBanner.second
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun GoalsSection(list: List<GoalModel>) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = colorResource(
                        id = R.color.yello_E4A323w
                    )
                )
                .padding(8.dp)
        ) {

            LazyRow(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(list) { item ->
                    GoalItem(goal = item)
                }
            }

            Row {
                Text(
                    text = stringResource(
                        id = R.string.goals_number,
                        list.size
                    ),
                    color = colorResource(id = R.color.white),
                    fontFamily = font_ibm,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(
                        id = R.string.all_save_number,
                        list.sumOf { it.currentBudget }.addComma()
                    ),
                    color = colorResource(id = R.color.white),
                    fontFamily = font_ibm,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun GoalItem(goal: GoalModel) {
        val goalImage = when (goal.type) {
            GoalTypes.Travel -> R.drawable.ic_luggage
            GoalTypes.Education -> R.drawable.ic_education
            GoalTypes.Invest -> R.drawable.ic_line_chart
            GoalTypes.Clothing -> R.drawable.ic_clothing
            null -> R.drawable.ic_star
        }

        val status = if (goal.status == GoalStatus.Good) R.color.green_1A7901
        else R.color.red_CA5D48

        val shape = RoundedCornerShape(16.dp)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .clip(shape)
                .border(
                    width = 1.dp,
                    color = colorResource(id = status),
                    shape = shape
                )
                .background(color = colorResource(id = R.color.white))
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .size(width = 180.dp, height = 140.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(goalImage),
                    modifier = Modifier
                        .width(36.dp)
                        .height(36.dp),
                    contentDescription = ""
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.currency,
                            goal.targetBudget.addComma()
                        ),
                        fontSize = 16.sp,
                        color = colorResource(
                            id = R.color.red_CA5D48
                        ),
                        fontFamily = font_ibm,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = stringResource(
                            id = R.string.currency,
                            goal.currentBudget.addComma()
                        ),
                        fontSize = 14.sp,
                        color = colorResource(
                            id = R.color.black
                        ),
                        fontFamily = font_ibm,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            LinearProgressIndicator(
                progress = calculateProgress(goal.targetBudget, goal.currentBudget),
                modifier = Modifier.height(8.dp),
                color = colorResource(id = R.color.red_CA5D48),
                backgroundColor = colorResource(id = R.color.black),
                strokeCap = StrokeCap.Round
            )

            Text(
                text = goal.title ?: "",
                fontFamily = font_ibm,
                fontWeight = FontWeight.Normal,
                color = colorResource(
                    id = R.color.black
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            val statusText = if (goal.status == GoalStatus.Good) GoalStatus.Good.name
            else GoalStatus.Unhappy.name
            val statusColor = if (goal.status == GoalStatus.Good) R.color.green_1A7901
            else R.color.red_CA5D48

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    statusText,
                    fontFamily = font_ibm,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(statusColor)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(id = R.string.remaining_date, goal.targetDate.diffDays()),
                    fontFamily = font_ibm,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(R.color.black)
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Preview
    @Composable
    fun PreviewGoalItem() {
        GoalItem(
            GoalModel(
                title = "ไปเที่ยวญี่ปุ่น",
                type = GoalTypes.Travel,
                targetDate = LocalDate(2024, 11, 20),
                targetBudget = BigDecimal(6000),
                currentBudget = BigDecimal(500),
                bankAccount = "xxxx-xxxx-xxxx-xxxx",
                status = GoalStatus.Good
            )
        )
    }

    @Composable
    fun BannerSection(sectionHeader: String, list: List<String>) {
        Column(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Text(text = sectionHeader, fontFamily = font_ibm, fontWeight = FontWeight.Bold)

            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(list) {
                    BannerItem(id = it)
                }
            }
        }
    }

    @Composable
    fun BannerItem(id: String) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(colorResource(id = R.color.white))
                .border(
                    1.dp,
                    color = colorResource(id = R.color.grey_898989),
                    shape = RoundedCornerShape(8.dp)
                )
                .size(width = 200.dp, height = 140.dp)
        ) {
            Text(text = id, fontFamily = font_ibm, fontWeight = FontWeight.Normal)
        }
    }

    @Preview
    @Composable
    fun PreviewBannerItem() {
        BannerItem(id = "asdfa")
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
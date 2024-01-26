package ui.achievement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.donyawan.salehere.MainViewModel
import com.donyawan.salehere.R
import com.donyawan.salehere.databinding.FragmentAchievementBinding
import model.home.GoalStatus
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import utils.font_ibm

class AchievementFragment : Fragment() {

    lateinit var binding: FragmentAchievementBinding
    val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAchievementBinding.inflate(layoutInflater, container, false)
        setupView(mainViewModel)
        return binding.root
    }

    private fun setupView(mainViewModel: MainViewModel) {
        binding.achievementComposeView.setContent {
            LazyVerticalGrid(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 16.dp,
                    end = 12.dp,
                    bottom = 16.dp
                )
            ) {
                items(mainViewModel.mockGoalList.filter { it.status == GoalStatus.Good }.size) {
                    AchievementItem()
                }
            }
        }
    }

    @Composable
    fun AchievementItem() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(colorResource(id = R.color.white))
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.red_CA5D48),
                    shape = RectangleShape
                )
                .padding(16.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_medal),
                modifier = Modifier.size(48.dp),
                contentDescription = ""
            )

            Text(
                text = stringResource(id = R.string.achievement_label),
                fontWeight = FontWeight.Bold,
                fontFamily = font_ibm,
                fontSize = 14.sp
            )
        }
    }

    @Preview
    @Composable
    fun PreviewAchievement() {
        AchievementItem()
    }

    companion object {
        fun newInstance() = AchievementFragment()
    }
}
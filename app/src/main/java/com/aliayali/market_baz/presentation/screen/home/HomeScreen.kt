package com.aliayali.market_baz.presentation.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aliayali.market_baz.R
import com.aliayali.market_baz.data.model.CategoryDto
import com.aliayali.market_baz.presentation.ui.CategoryItem
import com.aliayali.market_baz.presentation.ui.SearchTextField
import com.aliayali.market_baz.ui.theme.BrightOrange
import com.aliayali.market_baz.ui.theme.IceMist
import com.aliayali.market_baz.ui.theme.MidnightBlue
import com.aliayali.market_baz.ui.theme.SlateGray
import com.aliayali.market_baz.ui.theme.White

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {

    var categoryList by remember {
        mutableStateOf(CategoryDto.categoryDto)
    }
    val user = homeViewModel.user.value

    Column(
        Modifier
            .fillMaxSize()
            .background(White)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box {
                Image(
                    painterResource(R.drawable.shopping),
                    null,
                    Modifier
                        .background(MidnightBlue, CircleShape)
                        .padding(15.dp)
                        .size(20.dp)
                )
                Text(
                    text = "2",
                    color = White,
                    modifier = Modifier
                        .background(
                            BrightOrange,
                            CircleShape
                        )
                        .padding(horizontal = 7.dp),
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "تحویل به",
                    color = BrightOrange,
                    fontSize = 18.sp
                )
                Text(
                    text = user?.address ?: "null",
                    color = SlateGray,
                    fontSize = 18.sp
                )
            }

            Image(
                painterResource(R.drawable.menu),
                null,
                Modifier
                    .background(IceMist, CircleShape)
                    .padding(15.dp)
                    .size(20.dp)
            )
        }

        Text(
            text = "!سلام ${user?.name ?: "کاربر"}، روزت بخیر",
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )


        SearchTextField(
            ""
        ) {}

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    null
                )
                Text(
                    text = "دیدن همه",
                    fontSize = 18.sp
                )
            }
            Text(
                text = "همه دسته ها",
                fontSize = 18.sp
            )
        }


        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            reverseLayout = true,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(categoryList.size) { index ->
                CategoryItem(data = categoryList[index]) {
                    categoryList = categoryList.mapIndexed { i, item ->
                        item.copy(selected = i == index)
                    }
                }
            }
        }


    }
}
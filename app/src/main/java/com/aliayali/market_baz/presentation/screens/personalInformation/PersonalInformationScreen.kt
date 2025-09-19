package com.aliayali.market_baz.presentation.screens.personalInformation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.aliayali.market_baz.R
import com.aliayali.market_baz.navigation.NavigationScreen
import com.aliayali.market_baz.presentation.components.ItemImageUser
import com.aliayali.market_baz.ui.theme.BrightOrange
import com.aliayali.market_baz.ui.theme.IceMist
import com.aliayali.market_baz.ui.theme.SlateGray
import com.aliayali.market_baz.ui.theme.White

@Composable
fun PersonalInformationScreen(
    navController: NavController,
    personalInformationViewModel: PersonalInformationViewModel = hiltViewModel(),
) {

    val user = personalInformationViewModel.user.value
    var nameVisibility by remember { mutableStateOf(false) }
    var passwordVisibility by remember { mutableStateOf(false) }
    var imageVisibility by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(user?.name ?: "") }
    var password by remember { mutableStateOf(user?.password ?: "") }
    val imageList = remember {
        mutableListOf(
            R.drawable.user1,
            R.drawable.user2,
            R.drawable.user3,
            R.drawable.user4,
            R.drawable.user5,
            R.drawable.user6,
            R.drawable.user7,
            R.drawable.user8,
            R.drawable.user9,
            R.drawable.user10,
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                null,
                modifier = Modifier
                    .background(IceMist, CircleShape)
                    .padding(9.dp)
                    .clickable {
                        navController.navigate(NavigationScreen.Profile.route) {
                            popUpTo(NavigationScreen.Profile.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
            )
            Text(
                text = "اطلاعات شخصی",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(Modifier.height(16.dp))

        user?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = user.name.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.width(40.dp))
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                ) {
                    Image(
                        rememberAsyncImagePainter(model = user.image),
                        null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(CircleShape)
                            .size(150.dp),
                        alignment = Alignment.Center
                    )
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            null,
                            modifier = Modifier
                                .background(BrightOrange, CircleShape)
                                .padding(9.dp)
                                .clickable {
                                    imageVisibility = !imageVisibility
                                },
                            tint = White
                        )
                    }
                }
            }
        }

        AnimatedVisibility(imageVisibility) {
            LazyRow(
                reverseLayout = true,
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                items(imageList) {
                    ItemImageUser(it){
                        personalInformationViewModel.changeImage(it)
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    IceMist,
                    RoundedCornerShape(10.dp)
                )
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        nameVisibility = !nameVisibility
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    null
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "نام کاربری"
                        )
                        Text(
                            text = user?.name ?: "",
                            color = SlateGray
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Icon(
                        painterResource(R.drawable.personal),
                        null,
                        modifier = Modifier
                            .background(White, CircleShape)
                            .padding(10.dp)
                            .size(18.dp),
                        tint = BrightOrange
                    )
                }
            }

            AnimatedVisibility(nameVisibility) {
                Column {
                    Spacer(Modifier.height(10.dp))
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        label = {
                            Text(
                                text = "نام کاربری",
                                fontSize = 15.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End
                            )
                        },
                        isError = name.isBlank(),
                        textStyle = TextStyle(
                            textAlign = TextAlign.End
                        ),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person, null
                            )
                        },
                        shape = RoundedCornerShape(10),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = IceMist,
                            focusedIndicatorColor = IceMist,
                            errorIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                    Spacer(Modifier.height(10.dp))
                    Button(
                        onClick = {
                            personalInformationViewModel.changeName(name)
                            nameVisibility = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        enabled = name.isNotBlank()
                    ) {
                        Text(text = "تایید", fontSize = 20.sp)
                    }
                }
            }
        }

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    IceMist,
                    RoundedCornerShape(10.dp)
                )
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "شماره همراه"
                )
                Text(
                    text = user?.phone ?: "",
                    color = SlateGray
                )
            }
            Spacer(Modifier.width(10.dp))
            Icon(
                painterResource(R.drawable.call),
                null,
                modifier = Modifier
                    .background(White, CircleShape)
                    .padding(10.dp)
                    .size(18.dp),
                tint = Color(0xFF369BFF)
            )
        }

        Spacer(Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    IceMist,
                    RoundedCornerShape(10.dp)
                )
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        passwordVisibility = !passwordVisibility
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    null
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "رمز عبور"
                        )
                        Text(
                            text = user?.password ?: "",
                            color = SlateGray
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Icon(
                        Icons.Default.Lock,
                        null,
                        modifier = Modifier
                            .background(White, CircleShape)
                            .padding(10.dp)
                            .size(18.dp),
                        tint = Color(0xFF413DFB)
                    )
                }
            }

            AnimatedVisibility(passwordVisibility) {
                Column {
                    Spacer(Modifier.height(10.dp))
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        label = {
                            Text(
                                text = "رمز عبور",
                                fontSize = 15.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End
                            )
                        },
                        isError = password.isBlank(),
                        textStyle = TextStyle(
                            textAlign = TextAlign.End
                        ),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock, null
                            )
                        },
                        shape = RoundedCornerShape(10),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = IceMist,
                            focusedIndicatorColor = IceMist,
                            errorIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                    Spacer(Modifier.height(10.dp))
                    Button(
                        onClick = {
                            personalInformationViewModel.changePassword(password)
                            passwordVisibility = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        enabled = password.isNotBlank()
                    ) {
                        Text(text = "تایید", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}
package com.rure.deepmedi.presentation.home

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import com.rure.deepmedi.utils.designDp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rure.deepmedi.MainActivity
import com.rure.deepmedi.R
import com.rure.deepmedi.data.entity.AttributeTag
import com.rure.deepmedi.presentation.MainViewModel
import com.rure.deepmedi.presentation.home.component.BloodPressureAttrBox
import com.rure.deepmedi.presentation.home.component.HeartRateAttrBox
import com.rure.deepmedi.presentation.model.Attribute
import com.rure.deepmedi.presentation.model.BirthAttr
import com.rure.deepmedi.presentation.model.BloodPressureAttr
import com.rure.deepmedi.presentation.model.GenderAttr
import com.rure.deepmedi.presentation.model.HeartRateAttr
import com.rure.deepmedi.presentation.model.find
import com.rure.deepmedi.presentation.state.ApiIntent
import com.rure.deepmedi.ui.theme.Black
import com.rure.deepmedi.ui.theme.HomeBackgroundBlur
import com.rure.deepmedi.ui.theme.TextLightGray
import com.rure.deepmedi.ui.theme.Typography
import com.rure.deepmedi.ui.theme.White
import com.rure.deepmedi.ui.theme.pretendard
import com.rure.deepmedi.utils.calculateAge
import com.rure.deepmedi.utils.designSp

@Composable
fun HomeScreen(
    context: Context = LocalContext.current,
    mainViewModel: MainViewModel = viewModel(context as MainActivity),
    toCamera: () -> Unit
) {
    val userData by mainViewModel.userDataState.collectAsState()
    val userAttribute by mainViewModel.userAttrState.collectAsState()

    val gender by remember { derivedStateOf {
        userAttribute.find(AttributeTag.Gender) as GenderAttr?
            ?: GenderAttr.emptyObject()
    } }
    val birth by remember { derivedStateOf {
        userAttribute.find(AttributeTag.Birth) as BirthAttr?
            ?: BirthAttr.emptyObject()
    } }
    val heartRate by remember { derivedStateOf {
        userAttribute.find(AttributeTag.HeartRate) as HeartRateAttr?
    } }
    val bloodPressure by remember { derivedStateOf {
        userAttribute.find(AttributeTag.BloodPressure) as BloodPressureAttr?
    } }

    LaunchedEffect(userData) {
        userData?.let {
            mainViewModel.emit(ApiIntent.RetrieveUserAttr(it))
        }
    }

    //TODO: 배경 화면 Vector file 오류
//    Box(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Image(
//            imageVector = ImageVector.vectorResource(R.drawable.home_background),
//            contentDescription = null,
//            contentScale = ContentScale.FillHeight,
//            modifier = Modifier.fillMaxSize()
//                .background(
//                    brush = Brush.verticalGradient(listOf(White, HomeBackgroundBlur.copy(alpha = 0f)))
//                ).blur(20.designDp())
//                .border(
//                    width = 1.designDp(),
//                    brush = Brush.verticalGradient(listOf(White, White.copy(alpha = 0f))),
//                    shape = RectangleShape
//                )
//        )
//    }

    Column(
        modifier = Modifier.fillMaxSize().padding(top = 124.designDp(), start = 60.designDp(), end = 60.designDp()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.home_need_attention_guide),
            style = Typography.titleMedium,
            color = Black
        )
        Spacer(modifier = Modifier.height(12.designDp()))
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = stringResource(R.string.gender_birth_text, birth.value.calculateAge(), gender.value),
                style = Typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = Black
            )
            Text(
                text = stringResource(R.string.measure_result_text),
                style = Typography.bodySmall,
                fontWeight = FontWeight.Normal,
                color = TextLightGray
            )
        }

        Spacer(modifier = Modifier.height(20.designDp()))

        Column(
            modifier = Modifier.fillMaxWidth().wrapContentSize(),
            verticalArrangement = Arrangement.spacedBy(12.designDp())
        ) {
            heartRate?.let { HeartRateAttrBox(it) }
            bloodPressure?.let { BloodPressureAttrBox(it) }
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.home_text),
                fontFamily = pretendard,
                fontSize = 30.designSp(),
                fontWeight = FontWeight.SemiBold,
                lineHeight = 45.designSp(),
                modifier = Modifier
                    .clickable {
                        toCamera()
                    }
                    .padding(vertical = 40.designDp(), horizontal = 68.designDp())
            )
        }

    }
}
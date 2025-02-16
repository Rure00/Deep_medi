package com.rure.deepmedi.presentation.home

import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rure.deepmedi.MainActivity
import com.rure.deepmedi.R
import com.rure.deepmedi.data.entity.AttributeTag
import com.rure.deepmedi.presentation.MainViewModel
import com.rure.deepmedi.presentation.component.LoadingDialog
import com.rure.deepmedi.presentation.home.component.AttributeSkeletonBox
import com.rure.deepmedi.presentation.home.component.BloodPressureAttrBox
import com.rure.deepmedi.presentation.home.component.HeartRateAttrBox
import com.rure.deepmedi.presentation.model.BirthAttr
import com.rure.deepmedi.presentation.model.BloodPressureAttr
import com.rure.deepmedi.presentation.model.GenderAttr
import com.rure.deepmedi.presentation.model.HeartRateAttr
import com.rure.deepmedi.presentation.model.find
import com.rure.deepmedi.presentation.state.ApiIntent
import com.rure.deepmedi.ui.theme.Black
import com.rure.deepmedi.ui.theme.TextLightGray
import com.rure.deepmedi.ui.theme.Typography
import com.rure.deepmedi.ui.theme.pretendard
import com.rure.deepmedi.utils.calculateAge
import com.rure.deepmedi.utils.toDesignDp
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun HomeScreen(
    loginId: String,
    password: String,
    mainViewModel: MainViewModel = hiltViewModel(),
    toCamera: () -> Unit
) {
    val tag = "HomeScreen"
    var showLoadingDialog by remember { mutableStateOf(false) }

    val userAttribute by remember { mainViewModel.userAttrState }.collectAsStateWithLifecycle()
    val gender by remember { derivedStateOf {
        userAttribute.find(AttributeTag.Gender) as GenderAttr?
    } }
    val birth by remember { derivedStateOf {
        userAttribute.find(AttributeTag.Birth) as BirthAttr?
    } }
    val heartRate by remember { derivedStateOf {
        userAttribute.find(AttributeTag.HeartRate) as HeartRateAttr?
    } }
    val bloodPressure by remember { derivedStateOf {
        userAttribute.find(AttributeTag.BloodPressure) as BloodPressureAttr?
    } }


    LaunchedEffect(userAttribute) {
        showLoadingDialog = true
        if(userAttribute.isNotEmpty()) showLoadingDialog = false
        else {
            mainViewModel.emit(ApiIntent.RetrieveUserAttr(loginId, password))

        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(top = 124.toDesignDp(), start = 60.toDesignDp(), end = 60.toDesignDp()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.home_need_attention_guide),
            style = Typography.titleMedium,
            color = Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = stringResource(R.string.gender_birth_text, birth?.value?.calculateAge() ?: 0, gender?.value ?: "OO"),
                style = Typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = TextLightGray
            )
            Text(
                text = stringResource(R.string.measure_result_text),
                style = Typography.bodySmall,
                fontWeight = FontWeight.Normal,
                color = TextLightGray
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.fillMaxWidth().wrapContentSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            heartRate?.let { HeartRateAttrBox(it) } ?: AttributeSkeletonBox()
            bloodPressure?.let { BloodPressureAttrBox(it) } ?: AttributeSkeletonBox()
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.fillMaxWidth().padding(bottom = 18.toDesignDp()),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.home_text),
                fontFamily = pretendard,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 45.sp,
                modifier = Modifier
                    .clip(RoundedCornerShape(35.toDesignDp()))
                    .clickable {
                        toCamera()
                    }
                    .padding(vertical = 40.toDesignDp(), horizontal = 68.toDesignDp())
            )
        }
    }

    if(showLoadingDialog) {
        LoadingDialog()
    }
}
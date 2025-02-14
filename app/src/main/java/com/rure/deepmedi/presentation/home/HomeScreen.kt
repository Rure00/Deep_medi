package com.rure.deepmedi.presentation.home

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rure.deepmedi.MainActivity
import com.rure.deepmedi.R
import com.rure.deepmedi.presentation.MainViewModel
import com.rure.deepmedi.ui.theme.Black
import com.rure.deepmedi.ui.theme.Typography

@Composable
fun HomeScreen(
    context: Context = LocalContext.current,
    mainViewModel: MainViewModel = viewModel(context as MainActivity),
    toCamera: () -> Unit
) {
    val userData by mainViewModel.userDataState.collectAsState()
    val userAttribute by mainViewModel.userAttrState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.home_need_attention_guide),
            style = Typography.titleMedium,
            color = Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = userAttribute[],
            style = Typography.titleMedium,
            color = Black
        )


    }
}
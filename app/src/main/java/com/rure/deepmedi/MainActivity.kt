package com.rure.deepmedi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.rure.deepmedi.presentation.camera.CameraScreen
import com.rure.deepmedi.presentation.home.HomeScreen
import com.rure.deepmedi.presentation.navigation.Destination
import com.rure.deepmedi.presentation.navigation.mainNavGraph
import com.rure.deepmedi.ui.theme.DeepMediTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DeepMediTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val pagerState = rememberPagerState(0, 0f) {
                        Destination::class.nestedClasses.size
                    }

                    HorizontalPager(
                        modifier = Modifier.padding(innerPadding),
                        state = pagerState,
                        userScrollEnabled = false
                    ) {
                        NavHost(
                            navController,
                            startDestination = "main/") {
                            mainNavGraph(navController)
                        }
                    }

                }


            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DeepMediTheme {
        Greeting("Android")
    }
}
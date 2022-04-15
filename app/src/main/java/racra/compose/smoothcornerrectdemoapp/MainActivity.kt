package racra.compose.smoothcornerrectdemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import racra.compose.smooth_corner_rect_library.AbsoluteSmoothCornerShape
import racra.compose.smoothcornerrectdemoapp.ui.theme.SmoothCornerRectDemoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()

            SideEffect {
                systemUiController.setSystemBarsColor(
                    color = Color(0xffFDF6EC),
                    darkIcons = true
                )
            }
            DemoApp()
        }
    }
}

@Composable
fun DemoApp() {
    var radiusTL by remember {
        mutableStateOf(50f)
    }
    var radiusTR by remember {
        mutableStateOf(50f)
    }
    var radiusBR by remember {
        mutableStateOf(50f)
    }
    var radiusBL by remember {
        mutableStateOf(50f)
    }
    var smoothnessTL by remember {
        mutableStateOf(50f)
    }
    var smoothnessTR by remember {
        mutableStateOf(50f)
    }
    var smoothnessBR by remember {
        mutableStateOf(50f)
    }
    var smoothnessBL by remember {
        mutableStateOf(50f)
    }

    SmoothCornerRectDemoAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xffFDF6EC))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(60.dp, 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier
                        .padding(25.dp)
                        .height(200.dp)
                        .width(200.dp),
                    color = Color(0xFF557B83),
                    shape = AbsoluteSmoothCornerShape(
                        Dp(radiusTL), smoothnessTL.toInt(),
                        Dp(radiusTR), smoothnessTR.toInt(),
                        Dp(radiusBR), smoothnessBR.toInt(),
                        Dp(radiusBL), smoothnessBL.toInt()
                    )
                ) {}
                Text(text = "Radius top-left: ${radiusTL.toInt()}")
                Slider(
                    value = radiusTL,
                    onValueChange = { radiusTL = it },
                    valueRange = 0f..100f
                )
                Text(text = "Smoothness top-left: ${smoothnessTL.toInt()}")
                Slider(
                    value = smoothnessTL,
                    onValueChange = { smoothnessTL = it },
                    valueRange = 0f..100f
                )

                Text(text = "Radius top-right: ${radiusTR.toInt()}")
                Slider(
                    value = radiusTR,
                    onValueChange = { radiusTR = it },
                    valueRange = 0f..100f
                )
                Text(text = "Smoothness top-right: ${smoothnessTR.toInt()}")
                Slider(
                    value = smoothnessTR,
                    onValueChange = { smoothnessTR = it },
                    valueRange = 0f..100f
                )

                Text(text = "Radius bottom-right: ${radiusBR.toInt()}")
                Slider(
                    value = radiusBR,
                    onValueChange = { radiusBR = it },
                    valueRange = 0f..100f
                )
                Text(text = "Smoothness bottom-right: ${smoothnessBR.toInt()}")
                Slider(
                    value = smoothnessBR,
                    onValueChange = { smoothnessBR = it },
                    valueRange = 0f..100f
                )

                Text(text = "Radius bottom-left: ${radiusBL.toInt()}")
                Slider(
                    value = radiusBL,
                    onValueChange = { radiusBL = it },
                    valueRange = 0f..100f
                )
                Text(text = "Smoothness bottom-left: ${smoothnessBL.toInt()}")
                Slider(
                    value = smoothnessBL,
                    onValueChange = { smoothnessBL = it },
                    valueRange = 0f..100f
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DemoAppPreview() {
    DemoApp()
}
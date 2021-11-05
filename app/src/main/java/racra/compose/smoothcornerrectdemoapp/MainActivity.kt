package racra.compose.smoothcornerrectdemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import racra.compose.smooth_corner_rect_library.AbsoluteSmoothCornerShape
import racra.compose.smoothcornerrectdemoapp.ui.theme.SmoothCornerRectDemoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                color = Color(0xFFC8E3D4),
                shape = RoundedCornerShape(50.dp)
            ) {}
            Surface(
                modifier = Modifier
                    .padding(25.dp)
                    .height(200.dp)
                    .width(200.dp),
                color = Color(0xFFF6EABE),
                shape = AbsoluteSmoothCornerShape(
                    Dp(radiusTL), smoothnessTL.toInt(),
                    Dp(radiusTR), smoothnessTR.toInt(),
                    Dp(radiusBR), smoothnessBR.toInt(),
                    Dp(radiusBL), smoothnessBL.toInt()
                )
            ) {}
            Text(text = "RadiusTL: ${radiusTL.toInt()}")
            Slider(
                value = radiusTL,
                onValueChange = { radiusTL = it },
                valueRange = 0f..100f
            )
            Text(text = "SmoothnessTL: ${smoothnessTL.toInt()}")
            Slider(
                value = smoothnessTL,
                onValueChange = { smoothnessTL = it },
                valueRange = 0f..100f
            )

            Text(text = "RadiusTR: ${radiusTR.toInt()}")
            Slider(
                value = radiusTR,
                onValueChange = { radiusTR = it },
                valueRange = 0f..100f
            )
            Text(text = "SmoothnessTR: ${smoothnessTR.toInt()}")
            Slider(
                value = smoothnessTR,
                onValueChange = { smoothnessTR = it },
                valueRange = 0f..100f
            )

            Text(text = "RadiusBR: ${radiusBR.toInt()}")
            Slider(
                value = radiusBR,
                onValueChange = { radiusBR = it },
                valueRange = 0f..100f
            )
            Text(text = "SmoothnessBR: ${smoothnessBR.toInt()}")
            Slider(
                value = smoothnessBR,
                onValueChange = { smoothnessBR = it },
                valueRange = 0f..100f
            )

            Text(text = "RadiusBL: ${radiusBL.toInt()}")
            Slider(
                value = radiusBL,
                onValueChange = { radiusBL = it },
                valueRange = 0f..100f
            )
            Text(text = "SmoothnessBL: ${smoothnessBL.toInt()}")
            Slider(
                value = smoothnessBL,
                onValueChange = { smoothnessBL = it },
                valueRange = 0f..100f
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DemoAppPreview() {
    DemoApp()
}
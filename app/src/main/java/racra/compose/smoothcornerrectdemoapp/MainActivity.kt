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
import kotlin.math.roundToInt

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
    var radius by remember {
        mutableStateOf(50f)
    }
    var smoothness by remember {
        mutableStateOf(50f)
    }

    SmoothCornerRectDemoAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(60.dp, 0.dp)
                .verticalScroll(rememberScrollState()),
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
                shape = AbsoluteSmoothCornerShape(Dp(radius), smoothness.toInt())
            ) {}
            Text(text = "Radius: ${radius.toInt()}")
            Slider(
                value = radius,
                onValueChange = { radius = it },
                valueRange = 0f..100f
            )
            Text(text = "Smoothness: ${smoothness.toInt()}")
            Slider(
                value = smoothness,
                onValueChange = { smoothness = it },
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
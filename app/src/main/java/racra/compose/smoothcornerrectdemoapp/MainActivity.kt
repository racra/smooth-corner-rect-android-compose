package racra.compose.smoothcornerrectdemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    SmoothCornerRectDemoAppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
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
                shape = RoundedCornerShape(50.dp)
            ) {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DemoAppPreview() {
    DemoApp()
}
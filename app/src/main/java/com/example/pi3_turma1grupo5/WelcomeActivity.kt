package com.example.pi3_turma1grupo5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pi3_turma1grupo5.ui.theme.PI3_turma1grupo5Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PI3_turma1grupo5Theme {
                WelcomeScreenPreview()
            }
        }
    }
}

@Composable
fun WelcomeScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colorStops = arrayOf(
                    0.0f to Color(0xFFD2D5E2),
                    0.33f to Color(0xFFD2D5E2),
                    0.69f to Color(0xFF5770AF),
                    0.90f to Color(0xFF455E9E),
                    1.0f to Color(0xFF1033BC)
                )
            )
        )
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .align(Alignment.BottomCenter)
            ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawArc(
            color = Color(0xFFCEE1F6).copy(alpha = 0.75f),
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = true,
            topLeft = androidx.compose.ui.geometry.Offset(
                x = -(canvasWidth / 2),
                y = 0f
            ),
            size = androidx.compose.ui.geometry.Size(
                width = canvasWidth * 2,
                height = canvasHeight * 2
            )
        )
    }
}
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen()
}
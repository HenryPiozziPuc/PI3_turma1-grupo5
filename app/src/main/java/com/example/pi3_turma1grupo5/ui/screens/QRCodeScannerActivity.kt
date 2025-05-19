package com.example.pi3_turma1grupo5.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.pi3_turma1grupo5.ui.theme.PI3_turma1grupo5Theme
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.pi3_turma1grupo5.ui.theme.BackgroundLight
import com.example.pi3_turma1grupo5.ui.theme.Blue
import androidx.camera.core.Preview
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import androidx.camera.core.ExperimentalGetImage
import androidx.compose.material3.AlertDialog


class QRCodeScannerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PI3_turma1grupo5Theme {
                QRCodeScannerScreen()
            }
        }
    }
}

@Composable
fun QRCodeScannerScreen() {
    val context = LocalContext.current

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    var scannedValue by remember { mutableStateOf<String?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        if (hasCameraPermission) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.Center)
                    .background(Blue)
            ) {
                CameraPreview { qrValue ->
                    scannedValue = qrValue
                    if (isQRCodeValid(qrValue)) {
                        showSuccessDialog = true
                    } else {
                        showErrorDialog = true
                    }
                }
            }
        } else {
            Text(
                text = "Permissão da câmera não concedida",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }

        // Diálogo de sucesso
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                title = { Text("QR Code Válido") },
                text = { Text("Conteúdo: ${scannedValue ?: ""}") },
                confirmButton = {
                    Text("OK", modifier = Modifier.padding(8.dp))
                }
            )
        }

        // Diálogo de erro
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("QR Code Inválido") },
                text = { Text("Esse código não é reconhecido.") },
                confirmButton = {
                    Text("OK", modifier = Modifier.padding(8.dp))
                }
            )
        }
    }
}


@Composable
fun CameraPreview(
    onQRCodeScanned: (String) -> Unit = {}
) {
    //val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx)

            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build().apply {
                    setSurfaceProvider(previewView.surfaceProvider)
                }

                val analysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(
                            ContextCompat.getMainExecutor(ctx),
                            QRCodeAnalyzer(onQRCodeScanned)
                        )
                    }

                val selector = CameraSelector.DEFAULT_BACK_CAMERA

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    selector,
                    preview,
                    analysis
                )
            }, ContextCompat.getMainExecutor(ctx))

            previewView
        },
        modifier = Modifier.fillMaxSize()
    )
}


class QRCodeAnalyzer(
    private val onQRCodeScanned: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private val scanner = BarcodeScanning.getClient()
    private var alreadyScanned = false

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        if (alreadyScanned) {
            imageProxy.close()
            return
        }

        val mediaImage = imageProxy.image ?: run {
            imageProxy.close()
            return
        }

        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                val value = barcodes.firstOrNull()?.rawValue
                if (value != null) {
                    alreadyScanned = true
                    onQRCodeScanned(value)
                }
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
}

fun isQRCodeValid(qrValue: String): Boolean {
    return qrValue.startsWith("https:")
}

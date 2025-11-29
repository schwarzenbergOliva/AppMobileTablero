package com.example.tableroapp.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tableroapp.api.PositiveApiRepository

@Composable
fun WelcomeScreen(navController: NavController) {
    var frasePositiva by remember { mutableStateOf("Siempre puedes lograrlo") }
    var isLoading by remember { mutableStateOf(true) }
    var showBanner by remember { mutableStateOf(false) }
    
    // Animación de opacidad para el banner
    val bannerAlpha by animateFloatAsState(
        targetValue = if (showBanner) 1f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "banner_alpha"
    )
    
    // Llamar a la API al montar el componente
    LaunchedEffect(Unit) {
        // Intentar obtener frase de la API
        val result = PositiveApiRepository.getPositivePhrase()
        
        result.onSuccess { response ->
            frasePositiva = response.text
        }.onFailure {
            // Si falla, usar frase por defecto (ya está asignada)
            frasePositiva = "Siempre puedes lograrlo"
        }
        
        isLoading = false
        showBanner = true
        
        // Esperar 3 segundos antes de navegar a la pantalla principal
        kotlinx.coroutines.delay(3000)
        navController.navigate("pantalla_principal") {
            // Limpiar el back stack para que no pueda volver a esta pantalla
            popUpTo("welcome_screen") { inclusive = true }
        }
    }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            // Banner en la parte superior
            if (showBanner) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .alpha(bannerAlpha),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                        .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "💫 Frase del día",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Text(
                            text = frasePositiva,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

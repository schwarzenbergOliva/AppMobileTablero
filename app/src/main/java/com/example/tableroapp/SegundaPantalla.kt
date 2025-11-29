package com.example.tableroapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tableroapp.CardsRepository

@Composable
fun SegundaPantalla(
    navController: NavController,
    estadoFiltro: EstadoCard?
) {
    // Lista de todas las cards usando el repositorio
    val todasLasCards = remember { CardsRepository.getAllCards() }
    
    // Filtrar cards según el estado seleccionado
    val cardsFiltradas = if (estadoFiltro != null) {
        todasLasCards.filter { it.estado == estadoFiltro }
    } else {
        emptyList()
    }
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("agregar_tarea") },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar tarea")
        }
    }
    ) { paddingValues ->
    Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = estadoFiltro?.getDisplayName() ?: "Segunda Pantalla",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            
                Spacer(modifier = Modifier.height(16.dp))
            
                // Mostrar las cards filtradas
                if (cardsFiltradas.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        MiCarta(
                            cards = cardsFiltradas,
                            onCardClick = { cardData ->
                                navController.navigate("editor_card/${cardData.id}")
                            }
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
            Text(
                            text = "No hay cards para este estado",
                            style = MaterialTheme.typography.bodyLarge
            )
                    }
                }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth(0.6f) // Reducir ancho al 60%
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Volver",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            }
        }
    }
}


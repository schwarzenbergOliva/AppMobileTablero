package com.example.tableroapp.ui

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
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tableroapp.CardData
import com.example.tableroapp.CardsRepository
import com.example.tableroapp.EstadoCard
import com.example.tableroapp.utils.VibrationHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarTarea(navController: NavController) {
    // Contexto para acceder a los servicios del sistema
    val context = LocalContext.current
    
    // Estados para los campos del formulario
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var estadoSeleccionado by remember { mutableStateOf(EstadoCard.TODO) }
    var menuExpandido by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Nueva Tarea") }
            )
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
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Nueva Tarea",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Campo de título
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Ingrese el título de la tarea") }
                )
                
                // Campo de descripción
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    maxLines = 10,
                    placeholder = { Text("Ingrese la descripción de la tarea") }
                )
                
                // Selector de estado
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Estado",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    Button(
                        onClick = { menuExpandido = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = estadoSeleccionado.getDisplayName())
                    }
                    
                    DropdownMenu(
                        expanded = menuExpandido,
                        onDismissRequest = { menuExpandido = false }
                    ) {
                        EstadoCard.values().forEach { estado ->
                            DropdownMenuItem(
                                text = { Text(estado.getDisplayName()) },
                                onClick = {
                                    estadoSeleccionado = estado
                                    menuExpandido = false
                                }
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Botones de acción
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = {
                            // Validar que los campos no estén vacíos
                            if (titulo.isNotBlank() && descripcion.isNotBlank()) {
                                // Crear nueva card
                                val nuevaCard = CardData(
                                    id = CardsRepository.getNextId(),
                                    titulo = titulo.trim(),
                                    descripcion = descripcion.trim(),
                                    estado = estadoSeleccionado
                                )
                                
                                // Agregar la card al repositorio
                                CardsRepository.addCard(nuevaCard)
                                
                                // Hacer vibrar el dispositivo
                                VibrationHelper.vibrate(context, 200)
                                
                                // Volver a la pantalla principal
                                navController.popBackStack()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = titulo.isNotBlank() && descripcion.isNotBlank()
                    ) {
                        Text("Guardar")
                    }
                    
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Salir")
                    }
                }
            }
        }
    }
}


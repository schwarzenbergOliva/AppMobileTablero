package com.example.tableroapp.ui.theme

class NavController {
}
/*
package com.example.tableroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tableroapp.ui.theme.TableroAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TableroAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // ---------------------------------------------------------
                    // 1. AQUÍ SE CREA EL CONTROLADOR (El "Cerebro")
                    // ---------------------------------------------------------
                    val navController = rememberNavController()

                    // ---------------------------------------------------------
                    // 2. AQUÍ SE CREA EL MAPA (NavHost)
                    // Define qué pantalla se muestra con cada nombre ("ruta")
                    // ---------------------------------------------------------
                    NavHost(
                        navController = navController,
                        startDestination = "pantalla_inicio" // La primera pantalla al abrir la app
                    ) {

                        // Ruta A: La Pantalla Principal
                        composable("pantalla_inicio") {
                            // Llamamos a la función de abajo y le pasamos el controlador
                            PantallaInicio(navController)
                        }

                        // Ruta B: La Segunda Pantalla
                        composable("pantalla_detalle") {
                            // Llamamos a la función de abajo
                            PantallaDetalle(navController)
                        }
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------
// 3. AQUÍ AFUERA SE CREAN LAS PANTALLAS (Fuera de la class MainActivity)
// ---------------------------------------------------------

@Composable
fun PantallaInicio(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Soy la Pantalla 1 (Inicio)", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.padding(20.dp))

        Button(onClick = {
            // Usamos el controlador para viajar
            navController.navigate("pantalla_detalle")
        }) {
            Text("Ir a la segunda pantalla ->")
        }
    }
}

@Composable
fun PantallaDetalle(navController: NavController) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Soy la Pantalla 2 (Detalle)", style = MaterialTheme.typography.titleLarge)

            // El truco para empujar el botón al fondo
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { navController.popBackStack() }, // Volver atrás
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver / Botón al fondo")
            }
        }
    }
}
*/
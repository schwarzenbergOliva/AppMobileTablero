package com.example.tableroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tableroapp.ui.AgregarTarea
import com.example.tableroapp.ui.EditorCard
import com.example.tableroapp.ui.WelcomeScreen
import com.example.tableroapp.SegundaPantalla
import com.example.tableroapp.ui.theme.TableroAppTheme
import com.example.tableroapp.CardsRepository
import kotlinx.coroutines.launch

// Enum para los estados de las cards
enum class EstadoCard {
    TODO,
    IN_PROGRESS,
    DONE;
    
    /**
     * Obtiene el nombre legible del estado para mostrar en la UI
     */
    fun getDisplayName(): String {
        return when (this) {
            TODO -> "To do"
            IN_PROGRESS -> "In Progress"
            DONE -> "Done"
        }
    }
}

// Data class para representar una card con su estado
data class CardData(
    val id: Int,
    val titulo: String = "Este es el Título",
    val descripcion: String = "Este es el texto principal de la carta. Aquí puedes escribir una descripción más larga de lo que necesites mostrar.",
    val estado: EstadoCard
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TableroAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Crear el NavController
                    val navController = rememberNavController()
                    
                    // Configurar el NavHost con las rutas
                    NavHost(
                        navController = navController,
                        startDestination = "welcome_screen"
                    ) {
                        composable("welcome_screen") {
                            WelcomeScreen(navController = navController)
                        }
                        composable("pantalla_principal") {
                            PantallaConCarta(navController = navController)
                        }
                        composable("segunda_pantalla/{estado}") { backStackEntry ->
                            val estadoStr = backStackEntry.arguments?.getString("estado") ?: ""
                            val estado = try {
                                EstadoCard.valueOf(estadoStr)
                            } catch (_: IllegalArgumentException) {
                                null
                            }
                            SegundaPantalla(
                                navController = navController,
                                estadoFiltro = estado
                            )
                        }
                        composable("editor_card/{cardId}") { backStackEntry ->
                            val cardIdStr = backStackEntry.arguments?.getString("cardId") ?: "0"
                            val cardId = cardIdStr.toIntOrNull() ?: 0
                            EditorCard(
                                navController = navController,
                                cardId = cardId
                            )
                        }
                        composable("agregar_tarea") {
                            AgregarTarea(navController = navController)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ContenidoDeLaCarta(
    cardData: CardData,
    onClick: () -> Unit = {}
) {
    // Una Columna para apilar el título y el texto verticalmente
    Column(
        modifier = Modifier
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        // 1. El Título
        Text(
            text = cardData.titulo,
            style = MaterialTheme.typography.titleLarge, // Estilo de texto predefinido
            fontWeight = FontWeight.Bold // Lo ponemos en negrita
        )

        // Un espacio de 8.dp entre el título y el texto
        Spacer(modifier = Modifier.height(8.dp))

        // 2. El Texto del cuerpo
        Text(
            text = cardData.descripcion,
            style = MaterialTheme.typography.bodyMedium // Estilo de texto predefinido
        )

        // Mostrar el estado de la card con badge resaltado
        Spacer(modifier = Modifier.height(8.dp))
        // Badge con color según el estado
        val estadoColor = when (cardData.estado) {
            EstadoCard.TODO -> MaterialTheme.colorScheme.error
            EstadoCard.IN_PROGRESS -> MaterialTheme.colorScheme.primary
            EstadoCard.DONE -> MaterialTheme.colorScheme.tertiary
        }
        Surface(
            color = estadoColor.copy(alpha = 0.2f),
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Text(
                text = cardData.estado.getDisplayName(),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = estadoColor,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        Button(onClick = onClick) {
            Text(text = "Editar")
        }
    }
}

/**
 * Este es el Composable específico para tu carta.
 * Es buena práctica dividir tu UI en pequeñas funciones reutilizables.
 */
@Composable
fun MiCarta(
    cards: List<CardData>,
    modifier: Modifier = Modifier,
    onCardClick: (CardData) -> Unit = {}
) {
    // Agrupar cards en filas de 2
    val filas = cards.chunked(2)

    Column(
        modifier = modifier.fillMaxWidth(), // La carta ocupa el ancho disponible
        verticalArrangement = Arrangement.spacedBy(20.dp), // Espacio de 20.dp entre cada fila
        horizontalAlignment = Alignment.Start // Centra horizontalmente
    ) {
        filas.forEach { fila ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp) // Espacio entre las cards
            ) {
                fila.forEach { cardData ->
        Card(
                        modifier = Modifier.weight(1f), // Cada card ocupa la mitad del ancho
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Sombra de 4dp
        ) {
                        ContenidoDeLaCarta(
                            cardData = cardData,
                            onClick = { onCardClick(cardData) }
                        )
                    }
                }
                // Si hay una sola card en la fila, agregar un espacio vacío para mantener el layout
                if (fila.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

/**
 * Este es nuestro Composable principal.
 * Un Composable es una función que describe una parte de la UI.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaConCarta(navController: NavController) {
    // Estado del drawer
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Lista de todas las cards con sus estados (usando el repositorio)
    var todasLasCards by remember { mutableStateOf(CardsRepository.getAllCards()) }

    // Refrescar cards cuando se monte la pantalla
    LaunchedEffect(Unit) {
        todasLasCards = CardsRepository.getAllCards()
    }

    // Refrescar cards cuando se vuelva a esta pantalla desde otra
    LaunchedEffect(navController.currentBackStackEntry) {
        todasLasCards = CardsRepository.getAllCards()
    }

    // Agrupar cards por estado
    val cardsAgrupadas = remember(todasLasCards) {
        todasLasCards.groupBy { it.estado }
    }

    // Estados disponibles para el drawer
    val estados = EstadoCard.entries.toList()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Filtrar por Estado",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    estados.forEach { estado ->
                        NavigationDrawerItem(
                            label = { Text(estado.getDisplayName()) },
                            selected = false,
                            onClick = {
                                scope.launch { drawerState.close() }
                                navController.navigate("segunda_pantalla/${estado.name}")
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Tablero de Cards") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
                        }
                    }
                )
            }
        ) { paddingValues ->
    // Usamos una Columna para organizar los elementos verticalmente
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa toda la pantalla
                    .padding(paddingValues)
            .padding(16.dp), // Añade un margen general
        horizontalAlignment = Alignment.CenterHorizontally // Centra todo horizontalmente
    ) {
                // 1. Columna para las cartas agrupadas por estado
        // Usamos weight(1f) para que ocupe todo el espacio disponible, empujando al botón hacia abajo.
        Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    estados.forEach { estado ->
                        val cardsDelEstado = cardsAgrupadas[estado] ?: emptyList()
                        if (cardsDelEstado.isNotEmpty()) {
                            // Título del grupo
                            Text(
                                text = estado.getDisplayName(),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            // Cards del grupo
                            MiCarta(
                                cards = cardsDelEstado,
                                onCardClick = { cardData ->
                                    navController.navigate("editor_card/${cardData.id}")
                                }
                            )
                        }
                    }
        }
        
                // Lista desplegable para agregar nueva tarea
                var menuExpandido by remember { mutableStateOf(false) }
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { menuExpandido = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Nueva tarea",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    
                    DropdownMenu(
                        expanded = menuExpandido,
                        onDismissRequest = { menuExpandido = false },
                        modifier = Modifier.fillMaxWidth(0.6f)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Agregar nueva tarea") },
                            onClick = {
                                menuExpandido = false
                                navController.navigate("agregar_tarea")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MiCartaPreview() {
    TableroAppTheme {
        // Envolvemos la preview en una Columna para verla centrada
        // como en la app real.
        val cardsPreview = listOf(
            CardData(1, "Tarea Pendiente", "Esta es una tarea que necesita ser completada.", EstadoCard.TODO),
            CardData(2, "Tarea en Progreso", "Esta tarea está siendo trabajada actualmente.", EstadoCard.IN_PROGRESS)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MiCarta(cards = cardsPreview)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaConCartaPreview() {
    TableroAppTheme {
        // Para preview necesitamos un NavController mock, pero como es solo preview
        // podemos usar rememberNavController() aquí también
        val navController = rememberNavController()
        PantallaConCarta(navController = navController)
    }
}
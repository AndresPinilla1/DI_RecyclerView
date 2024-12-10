package com.example.di_recyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.di_recyclerview.ui.theme.DI_RecyclerViewTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DI_RecyclerViewTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun principal() {
    // Estados para cambiar entre las "aplicaciones"
    var actividad1 by remember { mutableStateOf(false) }
    var actividad2 by remember { mutableStateOf(false) }
    var actividad3 by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color(0xFFA1A0EB))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Selecciona una Aplicación", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            // Botón para la primera actividad
            Button(onClick = {
                actividad1 = true
                actividad2 = false
                actividad3 = false
            }) {
                Text("LazyColumn - Lista Tareas")
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Botón para la segunda actividad
            Button(onClick = {
                actividad1 = false
                actividad2 = true
                actividad3 = false
            }) {
                Text("LazyVerticalGrid - Galería Imágenes")
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Botón para la tercera actividad
            Button(onClick = {
                actividad1 = false
                actividad2 = false
                actividad3 = true
            }) {
                Text("StickyHeader - Sección de contactos")
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            // Mostrar la sección activa según el estado
            if (actividad1) {
                LazyColumn_ListaTareas { actividad1 = false }
            }
            if (actividad2) {
                LazyVerticalGrid_Galeria { actividad2 = false }
            }
            if (actividad3) {
                StickyHeader_Contactos { actividad3 = false }
            }
        }
    }
}

data class Tarea(
    val id: Int,
    val titulo: String,
    var completada: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyColumn_ListaTareas(function: () -> Unit) {
    var tareas by remember {mutableStateOf(
            listOf(
                Tarea(1, "Comprar leche", false),
                Tarea(2, "Estudiar Kotlin", false),
                Tarea(3, "Ir al gimnasio", false),
                Tarea(4, "Correr 5 km", false),
                Tarea(5, "Preparar la comida", false)
            )
        )
    }
    // Pantalla principal
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Tareas", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFA1A0EB))
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(Color(0xFFA1A0EB))) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(tareas) { tarea ->
                    TareaItem(
                        tarea = tarea,
                        onTareaEstadoCambiado = { tareaId ->
                            tareas = tareas.map {
                                if (it.id == tareaId) it.copy(completada = !it.completada)
                                else it
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun TareaItem(tarea: Tarea, onTareaEstadoCambiado: (Int) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2C))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Ícono
            Icon(
                imageVector = if (tarea.completada) Icons.Default.Check else Icons.Default.Clear,
                contentDescription = if (tarea.completada) "Tarea completada" else "Tarea pendiente",
                tint = if (tarea.completada) Color.Green else Color.Red,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            // Título de la tarea
            Text(
                text = tarea.titulo,
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            // Botón para cambiar el estado de la tarea
            Button(
                onClick = { onTareaEstadoCambiado(tarea.id) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DB954))
            ) {
                Text(text = if (tarea.completada) "Reabrir" else "Completar")
            }
        }
    }
}

data class ImagenItem(val id: Int, val titulo: String, val resourceId: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyVerticalGrid_Galeria(function: () -> Unit) {
    val context = LocalContext.current
    val imagenes = remember {
        listOf(
            ImagenItem(1, "Foto 1", R.drawable.paisaje1),
            ImagenItem(2, "Foto 2", R.drawable.paisaje),
            ImagenItem(3, "Foto 3", R.drawable.paisaje2),
            ImagenItem(4, "Foto 4", R.drawable.paisaje3)
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .background(Color.Blue)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            "Galería de Imágenes",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFA1A0EB))
                    .padding(16.dp),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(imagenes) { imagen ->
                    ImageCard(imagen = imagen, onClick = {
                        Toast.makeText(context, imagen.titulo, Toast.LENGTH_SHORT).show()
                    })
                }
            }
        }
    }
}

@Composable
fun ImageCard(imagen: ImagenItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imagen.resourceId),
            contentDescription = imagen.titulo,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(4.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = imagen.titulo,
            style = TextStyle(color = Color.Black, fontSize = 16.sp),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun StickyHeader_Contactos(function: () -> Unit) {
    val contactosAgrupados = remember {
        mapOf(
            'A' to listOf("Anuel", "Antonio", "Andrés"),
            'B' to listOf("Baltasar", "Bisonte", "Blanca"),
            'C' to listOf("Curro", "Clinton", "Cristina"),
            'D' to listOf("Daniel", "Dario", "David"),
            'E' to listOf("Eduardo", "Emilio", "Efren"),
            'F' to listOf("FILIPO", "Federico", "Francisco")
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contactos", fontWeight = FontWeight.Bold) }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            contactosAgrupados.forEach { (inicial, contactos) ->
                stickyHeader {
                    Text(
                        text = inicial.toString(),
                        style = TextStyle(color = Color.White, fontSize = 20.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Gray)
                            .padding(8.dp)
                    )
                }
                items(contactos) { contacto ->
                    Text(
                        text = contacto,
                        style = TextStyle(fontSize = 18.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    principal()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DI_RecyclerViewTheme {
        Greeting("Android")
    }
}
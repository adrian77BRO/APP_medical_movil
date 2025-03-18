package com.example.pushnotificationsapp.appointment.presentation

/*import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import com.example.pushnotificationsapp.appointment.data.models.Task

@Composable
fun TaskListScreen(
    onTaskClick: (Task) -> Unit,
    onAddTask: () -> Unit,
    taskViewModel: TaskViewModel = viewModel()
) {
    /*val context = LocalContext.current
    val taskViewModel: TaskViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
                    return TaskViewModel(context.applicationContext as Application) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    )*/

    LaunchedEffect(Unit) { taskViewModel.loadTasks() }

    val tasksDto by taskViewModel.tasks.observeAsState()
    val tasksList = tasksDto?.tasks ?: emptyList()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTask) {
                Icon(Icons.Default.Add, contentDescription = "Agregar tarea")
            }
        }
    ) { paddingValues ->
        if (tasksList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No hay tareas registradas")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(tasksList) { task ->
                    TaskItem(task = task, onClick = { onTaskClick(task) })
                    Divider()
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Text(text = task.title, style = MaterialTheme.typography.headlineMedium)
        Text(text = task.description, style = MaterialTheme.typography.bodyMedium)
        Text(text = "Due: ${task.dueDate}", style = MaterialTheme.typography.labelSmall)
    }
}*/
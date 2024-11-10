package com.example.countries.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.countries.model.Country
import com.example.countries.ui.viewmodels.CountryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryCreateNew(navController: NavController, countryViewModel: CountryViewModel = viewModel()) {
    var countryName by remember { mutableStateOf(TextFieldValue("")) }
    var countryCapital by remember { mutableStateOf(TextFieldValue("")) }
    var flagUrl by remember { mutableStateOf(TextFieldValue("")) }
    var showAlert by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "List Countries")
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Agregar un nuevo país", style = MaterialTheme.typography.headlineSmall)
                OutlinedTextField(
                    value = countryName,
                    onValueChange = { countryName = it },
                    label = { Text("Nombre del país") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = countryCapital,
                    onValueChange = { countryCapital = it },
                    label = { Text("Capital del país") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = flagUrl,
                    onValueChange = { flagUrl = it },
                    label = { Text("URL de la bandera") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        countryViewModel.addCountry(
                            Country(
                                name = countryName.text,
                                capital = countryCapital.text,
                                flagUrl = flagUrl.text
                            )
                        )
                        countryName = TextFieldValue("")
                        countryCapital = TextFieldValue("")
                        flagUrl = TextFieldValue("")
                        showAlert = true
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text("Agregar país")
                }
            }
        }
        if (showAlert) {
            AlertDialog(
                onDismissRequest = {
                    showAlert = false // Cerrar alerta
                },
                confirmButton = {
                    Button(onClick = {
                        showAlert = false
                        navController.popBackStack()
                    }) {
                        Text("OK")
                    }
                },
                title = {
                    Text("País agregado")
                },
                text = {
                    Text("El país se ha agregado exitosamente.")
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountryCreateNewPreview() {
    val navController = rememberNavController()
    CountryCreateNew(navController = navController)
}
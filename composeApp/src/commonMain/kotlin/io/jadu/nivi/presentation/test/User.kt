package io.jadu.nivi.presentation.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class User(val id: String, val name: String)

@OptIn(ExperimentalUuidApi::class)
@Composable
fun MissingKeysDemo() {
    var users by remember {
        mutableStateOf(
            List(20) { i -> User(Uuid.random().toString(), "User #$i") }
        )
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = { users = users.shuffled() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Shuffle List")
        }

        Spacer(Modifier.height(16.dp))

        Row(Modifier.fillMaxSize()) {
            // --- BAD LIST ---
//            Column(Modifier.weight(1f)) {
//                Text("No Keys (Redraws All)", color = Color.Red)
//                LazyColumn {
//                    items(users) { user ->
//                        ItemRow(user)
//                    }
//                }
//            }

            Spacer(Modifier.width(8.dp))

            // --- GOOD LIST ---
            Column(Modifier.weight(1f)) {
                Text("With Keys (Skips)", color = Color.Green)
                LazyColumn {
                    items(
                        items = users,
                        key = { it.id }
                    ) { user ->
                        ItemRow(user)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemRow(user: User) {
    val color = remember { Color(Random.nextLong(0xFFFFFFFF)) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(4.dp)
            .background(color.copy(alpha = 0.2f))
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(user.name)
    }
}
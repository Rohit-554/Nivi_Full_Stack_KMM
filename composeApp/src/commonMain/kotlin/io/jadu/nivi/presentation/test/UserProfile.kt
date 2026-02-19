package io.jadu.nivi.presentation.test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

data class UserProfile(val name: String, val clickCount: Int)

@Composable
fun OversharingParentDemo() {
    var user by remember {
        mutableStateOf(UserProfile("Alice", 0))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { user = user.copy(clickCount = user.clickCount + 1) }) {
            Text("Update User Clicks: ${user.clickCount}")
        }

//        Spacer(Modifier.height(32.dp))
//
//        Text("Bad: Recomposes on click", color = Color.Red)
//        OversharingChild(user)

        Spacer(Modifier.height(16.dp))

        Text("Good: Skips on click", color = Color.Green)
        SmartChild(name = user.name)
    }
}

@Composable
fun OversharingChild(user: UserProfile) {
    println("Mistake5 : OversharingChild: Recomposing...")
    Text("User Name: ${user.name}")
}

@Composable
fun SmartChild(name: String) {
    println("Mistake5 : SmartChild: Recomposing...")
    Text("User Name: $name")
}
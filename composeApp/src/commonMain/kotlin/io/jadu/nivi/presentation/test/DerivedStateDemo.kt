package io.jadu.nivi.presentation.test
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DerivedStateDemo() {
    Column(Modifier.fillMaxSize()) {
        Text(
            "Scroll these lists and watch the 'Recompositions' count of the PARENT function.",
            modifier = Modifier.padding(16.dp)
        )

        // --- THE WRONG WAY ---
//        Text(
//            "Bad List: Recomposes on EVERY pixel scroll",
//            color = Color.Red,
//            modifier = Modifier.padding(8.dp)
//        )
//        BadList()

        //HorizontalDivider(Modifier, thickness = 2.dp, color = Color.Black)

        // --- THE RIGHT WAY ---
        Text("Good List: Recomposes ONLY when button appears/hides", color = Color.Green, modifier = Modifier.padding(8.dp))
        GoodList()
    }
}

@Composable
fun BadList() {
    // 1. We create the state
    val listState = rememberLazyListState()

    val showButton = listState.firstVisibleItemIndex > 2

    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth().background(Color.Red.copy(alpha = 0.1f))) {
        LazyColumn(state = listState) {
            items(50) { index ->
                Text("Item $index", modifier = Modifier.padding(16.dp))
            }
        }

        if (showButton) {
            Text("TOP", modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp).background(Color.White))
        }
    }
}

@Composable
fun GoodList() {

    // 1. We create the state
    val listState = rememberLazyListState()
    val showButton by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 2 }
    }

    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth().background(Color.Green.copy(alpha = 0.1f))) {
        LazyColumn(state = listState) {
            items(50) { index ->
                Text("Item $index", modifier = Modifier.padding(16.dp))
            }
        }

        if (showButton) {
            Text("TOP", modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp).background(Color.White))
        }
    }
}
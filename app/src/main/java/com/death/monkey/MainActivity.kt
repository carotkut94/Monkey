package com.death.monkey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.death.monkey.screen.HomeScreen
import com.death.monkey.screen.HomeViewModel
import com.death.monkey.screen.ViewAction
import com.death.monkey.screen.ViewEvent
import com.death.monkey.ui.theme.MonkeyTheme
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: HomeViewModel by viewModels()
            val modalBottomSheetState =
                rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
            val scope = rememberCoroutineScope()

            LaunchedEffect(key1 = Unit) {
                viewModel.actions.collect {
                    when (it) {
                        is ViewAction.ShowSheetWithApps -> scope.launch {
                            modalBottomSheetState.show()
                        }
                    }
                }
            }

            MonkeyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ModalBottomSheetLayout(
                        sheetContent = {
                            AppSheetContent(viewModel)
                        },
                        sheetState = modalBottomSheetState,
                        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    ) {
                        Scaffold(
                            topBar = {
                                TopAppBar(elevation = 8.dp) {
                                    Text(
                                        modifier = Modifier.padding(8.dp),
                                        text = stringResource(id = R.string.app_name),
                                        style = MaterialTheme.typography.body1.copy(
                                            color = Color.White
                                        )
                                    )
                                }
                            },
                            floatingActionButton = {
                                FloatingActionButton(onClick = {
                                    viewModel.processViewEvent(ViewEvent.AddApp)
                                }) {
                                    Icon(Icons.Rounded.Add, contentDescription = "add")
                                }
                            },
                            floatingActionButtonPosition = FabPosition.End,
                        ) {
                            HomeScreen(viewModel)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppSheetContent(viewModel: HomeViewModel) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (viewModel.viewState.value.isLoading) {
            CircularProgressIndicator()
        }
        LazyVerticalGrid(
            cells = GridCells.Fixed(count = 4),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = PaddingValues(8.dp),
            content = {
                items(viewModel.viewState.value.appList) { item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            modifier = Modifier.height(48.dp),
                            painter = rememberDrawablePainter(drawable = item.icon),
                            contentDescription = item.name
                        )
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp),
                            text = item.name,
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }
            })
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MonkeyTheme {
        Scaffold(
            topBar = {
                TopAppBar(elevation = 8.dp) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(id = R.string.app_name)
                    )
                }
            }
        ) {

        }
    }
}
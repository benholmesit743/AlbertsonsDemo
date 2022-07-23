package com.example.AlbertsonsDemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : ComponentActivity() {

    lateinit var viewModel: MainViewModel

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = MainViewModel()

        setContent {
            Scaffold(
                topBar = { TopBar() },
                backgroundColor = colorResource(id = R.color.colorPrimary)
            ) {
                Navigation(viewModel)
            }
        }
    }
}


@Composable
fun Navigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController = navController, viewModel)
        }
    }
}

@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {
    val textState = remember { mutableStateOf(TextFieldValue("HMM")) }
    val textString = textState.value.text
    viewModel.searchText(textString)

    val originalList by viewModel.SFLiveData.observeAsState()

    Column {
        SearchView(textState)
        SearchList(navController = navController, textState, originalList)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    MainScreen(navController = navController, MainViewModel())
}

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = colorResource(id = R.color.colorPrimary),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    SearchView(textState)
}

@Composable
fun SearchList(navController: NavController, state: MutableState<TextFieldValue>, passedList: List<AcromineSF>?) {
    var originalList = ArrayList<AcromineSF>(emptyList())
    if (passedList != null) originalList = ArrayList(passedList)
    var filteredList: ArrayList<String>
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        val searchedText = state.value.text
        filteredList = if (searchedText.isEmpty()) {
            ArrayList(emptyList())
        } else {
            val resultList = ArrayList<String>()
            for (item in originalList) {
                for (abbrv in item.lfs) {
                    if (abbrv.lf.lowercase(Locale.getDefault())
                            .contains(searchedText.lowercase(Locale.getDefault()))
                    ) {
                        resultList.add(abbrv.lf)
                    }
                }
            }
            resultList
        }
        items(filteredList) { item ->
            SearchListItem(
                text = item,
                onItemClick = { selectedItem ->
                    //Do Nothing
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchListPreview() {
    val navController = rememberNavController()
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    SearchList(navController = navController, state = textState, ArrayList<AcromineSF>(emptyList()))
}

@Composable
fun SearchListItem(text: String, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onItemClick(text) })
            .background(colorResource(id = R.color.colorPrimary))
            .height(57.dp)
            .fillMaxWidth()
            .padding(PaddingValues(8.dp, 16.dp))
    ) {
        Text(text = text, fontSize = 18.sp, color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun CountryListItemPreview() {
    SearchListItem(text = "HMM 🇺🇸", onItemClick = { })
}

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
        backgroundColor = colorResource(id = R.color.colorPrimary),
        contentColor = Color.White
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}

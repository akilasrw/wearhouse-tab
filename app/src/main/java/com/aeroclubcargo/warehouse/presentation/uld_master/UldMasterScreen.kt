package com.aeroclubcargo.warehouse.presentation.uld_master

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aeroclubcargo.warehouse.presentation.components.CommonTextField
import com.aeroclubcargo.warehouse.presentation.components.top_bar.GetTopBar
import com.aeroclubcargo.warehouse.theme.BlueLight
import com.aeroclubcargo.warehouse.theme.BlueLight2
import com.aeroclubcargo.warehouse.utils.ListState

//@Preview(device = Devices.AUTOMOTIVE_1024p,)
@Composable
fun ULDMasterScreen(
    navController: NavController,
                    viewModel: ULDMasterVIewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        GetTopBar(navController = navController, isDashBoard = false)
    }) {
        GetULTMasterUI(viewModel)
    }
}


@Composable
fun GetULTMasterUI(viewModel: ULDMasterVIewModel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            Text(text = "ULT Master", style =  MaterialTheme.typography.subtitle1)
            Button(onClick = {
            /*TODO*/
            }, colors = ButtonDefaults.buttonColors(backgroundColor = BlueLight2)) {
                Text(text = "Add New ULD",style = MaterialTheme.typography.button.copy(color = Color.White))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
//            MainUIPanel(viewModel)
        }
    }
}

@Preview(device = Devices.AUTOMOTIVE_1024p,)
@Composable
fun MainUIPanel(
//    viewModel: ULDMasterVIewModel,
) {
//    val noOfPackagesValue =  viewModel.uldNumber.collectAsState()
    val itemList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, top = 3.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Row(modifier = Modifier.weight(1f)) {
                CommonTextField(label = "ULD number",
                    value = "", //noOfPackagesValue.value,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = { it ->

                    },
                    padding = 0.dp
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(
                border = BorderStroke(0.5.dp, BlueLight),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    /*TODO*/

                }) {
                Text(
                    text = "Add Filter",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.button.copy(color = BlueLight)
                )
            }
            Spacer(modifier = Modifier.weight(5f))
        }
        Spacer(modifier = Modifier.height(5.dp))
        ULDDataTable()
    }
}

@Composable
fun ULDDataTable() {
    val lazyColumnListState = rememberLazyListState()
    var articles = listOf<String>("A","B","C")
    LazyColumn(state = lazyColumnListState) {
        items(
            items = articles,
            key = { it.toString() },
        ) { article ->
            Text(
                modifier = Modifier
                    .height(75.dp),
                text = article,
            )

            Divider()
        }
//        item (
//            key = viewModel.listState,
//        ) {
//            when(viewModel.listState) {
//                ListState.LOADING -> {
//                    Loading()
//                }
//                ListState.PAGINATING -> {
//                    PaginationLoading()
//                }
//                ListState.PAGINATION_EXHAUST -> {
//                    PaginationExhaust()
//                }
//                else -> {}
//            }
//        }
    }
}

@Composable
fun ListItem(item: String) {
    Text(text = item, color = Color.Black)
}
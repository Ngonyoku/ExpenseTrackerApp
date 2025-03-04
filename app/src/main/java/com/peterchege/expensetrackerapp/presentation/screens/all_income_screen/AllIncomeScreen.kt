/*
 * Copyright 2023 Expense Tracker App By Peter Chege
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peterchege.expensetrackerapp.presentation.screens.all_income_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.peterchege.expensetrackerapp.core.util.Screens
import com.peterchege.expensetrackerapp.domain.models.Income
import com.peterchege.expensetrackerapp.presentation.bottomsheets.view.AddExpenseBottomSheet
import com.peterchege.expensetrackerapp.presentation.bottomsheets.view.AddExpenseCategoryBottomSheet
import com.peterchege.expensetrackerapp.presentation.bottomsheets.view.AddIncomeBottomSheet
import com.peterchege.expensetrackerapp.presentation.bottomsheets.view.AddTransactionBottomSheet
import com.peterchege.expensetrackerapp.presentation.bottomsheets.view.AddTransactionCategoryBottomSheet
import com.peterchege.expensetrackerapp.presentation.bottomsheets.view.IncomeInfoBottomSheet
import com.peterchege.expensetrackerapp.presentation.components.IncomeCard
import com.peterchege.expensetrackerapp.presentation.components.TransactionCard
import com.peterchege.expensetrackerapp.presentation.screens.home_screen.BottomSheets
import kotlinx.coroutines.launch

@Composable
fun AllIncomeScreen(
    navController: NavController,
    viewModel: AllIncomeScreenViewModel = hiltViewModel()
) {
    val incomes = viewModel.incomes.collectAsStateWithLifecycle()
    val activeIncomeId = viewModel.activeIncomeId.value

    AllIncomeScreenContent(
        navController = navController,
        incomes = incomes.value,
        activeIncomeId = activeIncomeId,
        onChangeActiveIncomeId = {
            viewModel.onChangeActiveIncomeId(it)
        }
    )

}


@OptIn(ExperimentalCoilApi::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AllIncomeScreenContent(
    navController: NavController,
    incomes: List<Income>,
    activeIncomeId:String?,
    onChangeActiveIncomeId:(String) -> Unit,
){
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val scaffoldState = rememberScaffoldState()
    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetBackgroundColor = MaterialTheme.colors.onBackground,

        sheetContent = {
            IncomeInfoBottomSheet(
                activeIncomeId = activeIncomeId
            )
        }
    ){
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.onBackground,
                    title = {
                        Text(
                            style = TextStyle(color = MaterialTheme.colors.primary),
                            text = "My Income",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    }
                )
            },
        ){
            val scope = rememberCoroutineScope()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
            ) {

                items(items = incomes) { income ->
                    IncomeCard(
                        income = income,
                        onIncomeNavigate = {
                            scope.launch{
                                onChangeActiveIncomeId(it)
                                modalSheetState.show()
                            }
                        }
                    )
                }
            }
        }
    }


}

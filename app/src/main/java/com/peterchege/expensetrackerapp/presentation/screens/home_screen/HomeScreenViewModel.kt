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
package com.peterchege.expensetrackerapp.presentation.screens.home_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import com.peterchege.expensetrackerapp.core.util.FilterConstants
import com.peterchege.expensetrackerapp.core.util.UiEvent
import com.peterchege.expensetrackerapp.domain.use_case.GetAllExpensesUseCase
import com.peterchege.expensetrackerapp.domain.use_case.GetAllIncomeUseCase
import com.peterchege.expensetrackerapp.domain.use_case.GetFilteredTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class BottomSheets  {
    ADD_TRANSACTION,
    ADD_TRANSACTION_CATEGORY,
    ADD_EXPENSE,
    ADD_EXPENSE_CATEGORY,
    ADD_INCOME,
    VIEW_INCOME,
}
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getFilteredTransactionsUseCase: GetFilteredTransactionsUseCase,
    private val getAllIncomeUseCase: GetAllIncomeUseCase,
    private val getAllExpensesUseCase: GetAllExpensesUseCase,


) : ViewModel() {

    private val _selectedIndex = mutableStateOf(0)
    val selectedIndex: State<Int> = _selectedIndex

    private val _transactions =
        mutableStateOf<Flow<List<TransactionEntity>>>(flow { emptyList<TransactionEntity>() })
    val transactions: State<Flow<List<TransactionEntity>>> = _transactions

    private val _activeIncomeId = mutableStateOf<String?>(null)
    val activeIncomeId :State<String?> = _activeIncomeId


    private val _activeBottomSheet = mutableStateOf<BottomSheets?>(null)
    val activeBottomSheet: State<BottomSheets?> = _activeBottomSheet

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val income = getAllIncomeUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )
    val expenses = getAllExpensesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )



    init {
        getTransactions(filter = FilterConstants.FilterList[_selectedIndex.value])
    }


    fun onChangeSelectedIndex(index: Int) {
        _selectedIndex.value = index
        getTransactions(filter = FilterConstants.FilterList[index])
    }
    fun onChangeActiveBottomSheet(bottomSheet: BottomSheets){
        _activeBottomSheet.value = bottomSheet
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.OpenBottomSheet(bottomSheet = bottomSheet))
        }
    }
    fun onChangeActiveIncomeId(id:String) {
        _activeIncomeId.value = id
    }


    fun getTransactions(filter: String) {
        viewModelScope.launch {
            val transactionsInfo = getFilteredTransactionsUseCase(filter = filter)
            _transactions.value = transactionsInfo
        }
    }


}


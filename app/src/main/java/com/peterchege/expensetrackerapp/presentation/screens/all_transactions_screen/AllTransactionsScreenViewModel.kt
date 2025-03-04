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
package com.peterchege.expensetrackerapp.presentation.screens.all_transactions_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.expensetrackerapp.core.util.FilterConstants
import com.peterchege.expensetrackerapp.domain.models.Transaction
import com.peterchege.expensetrackerapp.domain.toExternalModel
import com.peterchege.expensetrackerapp.domain.use_case.GetAllTransactionCategoriesUseCase
import com.peterchege.expensetrackerapp.domain.use_case.GetFilteredTransactionsUseCase
import com.peterchege.expensetrackerapp.domain.use_case.GetTransactionsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AllTransactionsScreenViewModel @Inject constructor(
    private val getFilteredTransactionsUseCase: GetFilteredTransactionsUseCase,
    private val getTransactionsByCategoryUseCase: GetTransactionsByCategoryUseCase,
    private val getAllTransactionCategoriesUseCase: GetAllTransactionCategoriesUseCase,
):ViewModel() {

    val transactionCategories = getAllTransactionCategoriesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList(),
        )

    val _activeTransactionCategoryFilterId = mutableStateOf(FilterConstants.ALL)
    val activeTransactionCategoryFilterId :State<String> = _activeTransactionCategoryFilterId


    init {
        getTransactions()
    }


    val _transactions = mutableStateOf<List<Transaction>>(emptyList())
    val transactions: State<List<Transaction>> = _transactions



    fun onChangeActiveTransactionFilter(filter:String){
        _activeTransactionCategoryFilterId.value = filter
    }

    fun getTransactions(){
        viewModelScope.launch {
            val transactions = getTransactionsByCategoryUseCase(
                categoryId = _activeTransactionCategoryFilterId.value)
            transactions.collectLatest { transactionEntities ->
                _transactions.value = transactionEntities.map { it.toExternalModel() }

            }
        }
    }

}
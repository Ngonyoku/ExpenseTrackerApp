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
package com.peterchege.expensetrackerapp.core.di

import com.peterchege.expensetrackerapp.core.room.database.ExpenseTrackerAppDatabase
import com.peterchege.expensetrackerapp.data.ExpenseCategoryRepositoryImpl
import com.peterchege.expensetrackerapp.data.ExpenseRepositoryImpl
import com.peterchege.expensetrackerapp.data.IncomeRepositoryImpl
import com.peterchege.expensetrackerapp.data.TransactionCategoryRepositoryImpl
import com.peterchege.expensetrackerapp.data.TransactionRepositoryImpl
import com.peterchege.expensetrackerapp.domain.repository.ExpenseCategoryRepository
import com.peterchege.expensetrackerapp.domain.repository.ExpenseRepository
import com.peterchege.expensetrackerapp.domain.repository.IncomeRepository
import com.peterchege.expensetrackerapp.domain.repository.TransactionCategoryRepository
import com.peterchege.expensetrackerapp.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideExpenseCategoryRepository(database: ExpenseTrackerAppDatabase):
            ExpenseCategoryRepository {
        return ExpenseCategoryRepositoryImpl(
            db = database
        )
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(database: ExpenseTrackerAppDatabase):
            ExpenseRepository {
        return ExpenseRepositoryImpl(
            db = database
        )
    }

    @Provides
    @Singleton
    fun provideTransactionCategoryRepository(database: ExpenseTrackerAppDatabase):
            TransactionCategoryRepository {
        return TransactionCategoryRepositoryImpl(
            db = database
        )
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(database: ExpenseTrackerAppDatabase):
            TransactionRepository {
        return TransactionRepositoryImpl(
            db = database
        )
    }

    @Provides
    @Singleton
    fun provideIncomeRepository(database: ExpenseTrackerAppDatabase):
            IncomeRepository {
        return IncomeRepositoryImpl(
            db = database
        )
    }
}
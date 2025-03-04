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
package com.peterchege.expensetrackerapp.core.util

import com.peterchege.expensetrackerapp.presentation.screens.home_screen.BottomSheets

abstract class Event

sealed class UiEvent: Event() {
    data class ShowSnackbar(val uiText: String) : UiEvent()
    data class Navigate(val route: String) : UiEvent()

    data class OpenBottomSheet(val bottomSheet: BottomSheets) : UiEvent()


}
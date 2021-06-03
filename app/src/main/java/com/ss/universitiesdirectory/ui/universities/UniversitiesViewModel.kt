package com.ss.universitiesdirectory.ui.universities

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class UniversitiesViewModel @Inject constructor(
    private val repository: UniversitiesRepository
) : ViewModel() {

    fun getUniversities() = repository.getUniversities()
}
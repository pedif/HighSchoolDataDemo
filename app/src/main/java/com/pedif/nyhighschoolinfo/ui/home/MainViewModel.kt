package com.pedif.nyhighschoolinfo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedif.nyhighschoolinfo.data.model.HighSchool
import com.pedif.nyhighschoolinfo.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _schools = MutableLiveData<List<HighSchool>>()
    val schools: LiveData<List<HighSchool>>
        get() = _schools

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        viewModelScope.launch {
            val result = repository.getHighSchoolData()
            if (result.isSuccessful) {
                _schools.value =result.data!! as List<HighSchool>
            } else {
                _errorMessage.value = result.data.toString()
            }
        }
    }

    fun errorDisplayed(){
        _errorMessage.value = ""
    }
}
package com.pedif.nyhighschoolinfo.ui.highschool

import androidx.lifecycle.*
import com.pedif.nyhighschoolinfo.common.Constants
import com.pedif.nyhighschoolinfo.data.model.HighSchool
import com.pedif.nyhighschoolinfo.data.model.SAT
import com.pedif.nyhighschoolinfo.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HighSchoolViewModel @Inject constructor(
    private val repository: Repository,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _SATs = MutableLiveData<List<SAT>>()
    val SATs: LiveData<List<SAT>>
        get() = _SATs

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _school = MutableLiveData<HighSchool>()
    val school: LiveData<HighSchool>
        get() = _school

    var schoolTitle = ""
    init {
        viewModelScope.launch {
            Timber.e(state.get<String>(Constants.EXTRA_SCHOOL_DBN))
            state.get<String>(Constants.EXTRA_SCHOOL_DBN)?.let {

                val result = repository.getHighSchoolByDbn(it)
                if (result.isSuccessful) {
                    _school.value = result.data!! as HighSchool
                } else {
                    _errorMessage.value = result.data.toString()
                }

                val SATResult = repository.getHighSchoolSatData(it)
                if (SATResult.isSuccessful) {
                    _SATs.value = SATResult.data!! as List<SAT>
                } else {
                    _errorMessage.value = SATResult.data.toString()
                }
            }
        }
    }

    fun errorDisplayed() {
        _errorMessage.value = ""
    }
}
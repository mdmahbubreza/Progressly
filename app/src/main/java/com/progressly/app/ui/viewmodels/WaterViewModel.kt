package com.progressly.app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.progressly.app.data.database.entities.*
import com.progressly.app.data.repositories.WaterRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class WaterViewModel(
    private val repository: WaterRepository
) : ViewModel() {

    fun getWaterLogsByDate(date: LocalDate) = repository.getWaterLogsByDate(date).asLiveData()

    fun getTotalWaterByDate(date: LocalDate) = repository.getTotalWaterByDate(date).asLiveData()

    fun getWaterStreak(date: LocalDate) = repository.getWaterStreak(date).asLiveData()

    fun getLatestWaterStreak() = repository.getLatestWaterStreak().asLiveData()

    fun logWater(amount: Int, date: LocalDate = LocalDate.now()) {
        viewModelScope.launch {
            val time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
            val waterLog = WaterLogEntity(
                amount = amount,
                loggedDate = date,
                time = time
            )
            repository.insertWaterLog(waterLog)
        }
    }

    fun deleteWaterLog(log: WaterLogEntity) {
        viewModelScope.launch {
            repository.deleteWaterLog(log)
        }
    }

    fun updateWaterStreak(streak: WaterStreakEntity) {
        viewModelScope.launch {
            repository.updateWaterStreak(streak)
        }
    }
}

class WaterViewModelFactory(private val repository: WaterRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WaterViewModel(repository) as T
    }
}

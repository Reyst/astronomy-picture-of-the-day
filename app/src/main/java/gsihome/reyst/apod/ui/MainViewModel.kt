package gsihome.reyst.apod.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gsihome.reyst.apod.domain.APODRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    private val formatter: SimpleDateFormat,
    private val repository: APODRepository,
) : ViewModel() {

    private val mutableDataPresentation = MutableStateFlow(-1L to "")
    val dataPresentation: StateFlow<Pair<Long, String>> = mutableDataPresentation.asStateFlow()

    private val mutableUrlState = MutableStateFlow("")
    val urlStateFlow: StateFlow<String> = mutableUrlState.asStateFlow()

    fun setDate(calendar: Calendar) {
        viewModelScope.launch {
            formatter.format(calendar.time)
                .also { mutableDataPresentation.emit(calendar.timeInMillis to it) }
                .let { repository.getImageUrlByDate(it) }
                .catch { it.printStackTrace() }
                .collect { mutableUrlState.emit(it) }
        }
    }
}
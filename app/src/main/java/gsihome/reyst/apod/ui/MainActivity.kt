package gsihome.reyst.apod.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import gsihome.reyst.apod.databinding.ActivityMainBinding
import gsihome.reyst.apod.utils.launchWhenCreated
import gsihome.reyst.apod.utils.launchWhenStarted
import gsihome.reyst.apod.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding<ActivityMainBinding>()

    private val vm by viewModel<MainViewModel>()

    private val dateConstraints: Pair<Long, Long>
        get() {
            val maxDate = Calendar.getInstance().timeInMillis
            val minDate = Calendar.getInstance()
                .apply { set(1995, Calendar.JUNE, 20) }
                .timeInMillis


            return minDate to maxDate
        }

    private val listener = { _: View, year: Int, month: Int, dayOfMonth: Int ->
        onDateSelected(year, month, dayOfMonth)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // R.layout.activity_main

        binding.calendar?.init()
        binding.date?.init()
        initImage()
    }

    private fun initImage() {
        vm.urlStateFlow.launchWhenStarted(lifecycleScope) {
            Glide.with(this).load(it).into(binding.image)
        }
    }

    private fun getTodayImage() = vm.setDate(Calendar.getInstance())

    private fun selectDate() {

        val (mills, _) = vm.dataPresentation.value
        val current = Calendar.getInstance()
            .apply { if (mills > 0) timeInMillis = mills }

        val dialog = DatePickerDialog(
            this,
            listener,
            current[Calendar.YEAR],
            current[Calendar.MONTH],
            current[Calendar.DAY_OF_MONTH],
        )

        dialog.datePicker.minDate = dateConstraints.first
        dialog.datePicker.maxDate = dateConstraints.second

        dialog.show()
    }

    private fun onDateSelected(year: Int, month: Int, dayOfMonth: Int) {
        Calendar.getInstance()
            .apply { set(year, month, dayOfMonth) }
            .also(vm::setDate)
    }

    private fun CalendarView.init() {
        minDate = dateConstraints.first
        maxDate = dateConstraints.second

        setOnDateChangeListener(listener)

        vm.dataPresentation.launchWhenCreated(lifecycleScope) { (mills, _) ->
            setDate(mills, false, true)
            if (mills < 0) getTodayImage()
        }
    }

    private fun AppCompatEditText.init() {
        setOnClickListener { selectDate() }
        vm.dataPresentation.launchWhenCreated(lifecycleScope) { (_, dateString) ->
            setText(dateString)
            if (dateString.isBlank()) getTodayImage()
        }
    }

}



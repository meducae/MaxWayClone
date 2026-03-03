package uz.gita.maxwayclone.presentation.dialogs

import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.androidbroadcast.vbpd.viewBinding
import uz.gita.maxwayclone.R
import uz.gita.maxwayclone.databinding.DialogDataPickerBinding
import java.util.Calendar

class DialogDataPicker(
    private val onDateSelected: (day: Int, month: Int, year: Int) -> Unit
) : BottomSheetDialogFragment(R.layout.dialog_data_picker) {

    private val binding by viewBinding(DialogDataPickerBinding::bind)

    override fun getTheme(): Int = R.style.AppBottomSheetTheme

    private val months = arrayOf(
        "January","February","March","April","May","June",
        "July","August","September","October","November","December"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pickerMonth.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.pickerDay.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.pickerYear.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        val cal = Calendar.getInstance()
        val currentYear = cal.get(Calendar.YEAR)

        binding.pickerMonth.apply {
            minValue = 0
            maxValue = 11
            displayedValues = months
            wrapSelectorWheel = true
            value = cal.get(Calendar.MONTH)
        }

        binding.pickerYear.apply {
            minValue = 1950
            maxValue = currentYear
            wrapSelectorWheel = false
            value = cal.get(Calendar.YEAR)
        }

        fun updateDays(keepDay: Int) {
            val temp = Calendar.getInstance().apply {
                set(Calendar.YEAR, binding.pickerYear.value)
                set(Calendar.MONTH, binding.pickerMonth.value)
                set(Calendar.DAY_OF_MONTH, 1)
            }
            val maxDay = temp.getActualMaximum(Calendar.DAY_OF_MONTH)

            binding.pickerDay.minValue = 1
            binding.pickerDay.maxValue = maxDay
            binding.pickerDay.wrapSelectorWheel = true
            binding.pickerDay.value = keepDay.coerceIn(1, maxDay)
        }

        updateDays(cal.get(Calendar.DAY_OF_MONTH))

        binding.pickerMonth.setOnValueChangedListener { _, _, _ -> updateDays(binding.pickerDay.value) }
        binding.pickerYear.setOnValueChangedListener { _, _, _ -> updateDays(binding.pickerDay.value) }

        binding.btnSelect.setOnClickListener {
            val day = binding.pickerDay.value
            val month = binding.pickerMonth.value + 1
            val year = binding.pickerYear.value
            onDateSelected(day, month, year)

            dismiss()
        }
    }
}
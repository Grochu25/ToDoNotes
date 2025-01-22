package com.example.todonotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class AddNotes : Fragment() {

    private var selectedDate: String? = null
    private var selectedTime: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_notes, container, false)

        val cancelButton = view.findViewById<Button>(R.id.cancelButton)
        val saveButton = view.findViewById<Button>(R.id.saveButton)
        val selectDateButton = view.findViewById<Button>(R.id.selectDateButton)
        val selectTimeButton = view.findViewById<Button>(R.id.selectTimeButton)
        val selectedDateTimeText = view.findViewById<TextView>(R.id.selectedDateTimeText)

        cancelButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Potwierdzenie")
            builder.setMessage("Czy na pewno chcesz anulować dodawanie notatek?")

            builder.setNegativeButton("Nie"){ dialog, _ ->
                dialog.dismiss()
            }

            builder.setPositiveButton("Tak"){ _, _ ->
                Toast.makeText(requireContext(), "Dodano poprawnie nową notatkę!", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            }

            var dialog = builder.create()
            dialog.show()        }

        saveButton.setOnClickListener{
            Toast.makeText(requireContext(), "Dodano poprawnie nową notatkę!", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        }

        selectDateButton.setOnClickListener {
            showDatePicker(selectedDateTimeText)
        }

        selectTimeButton.setOnClickListener {
            showTimePicker(selectedDateTimeText)
        }

        return view
    }

    private fun showDatePicker(selectedDateTimeText: TextView) {
        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Wybierz datę")
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            selectedDate = datePicker.headerText
            updateDateTimeDisplay(selectedDateTimeText)
        }

        datePicker.addOnNegativeButtonClickListener {
            Toast.makeText(requireContext(), "Anulowano wybór daty", Toast.LENGTH_SHORT).show()
        }

        datePicker.show(parentFragmentManager, "DATE_PICKER")
    }

    private fun showTimePicker(selectedDateTimeText: TextView) {
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setTitleText("Wybierz godzinę")
            .build()

        timePicker.addOnPositiveButtonClickListener {
            val hour = timePicker.hour
            val minute = timePicker.minute
            selectedTime = String.format("%02d:%02d", hour, minute)
            updateDateTimeDisplay(selectedDateTimeText)
        }

        timePicker.addOnNegativeButtonClickListener {
            Toast.makeText(requireContext(), "Anulowano wybór godziny", Toast.LENGTH_SHORT).show()
        }

        timePicker.show(parentFragmentManager, "TIME_PICKER")
    }

    private fun updateDateTimeDisplay(selectedDateTimeText: TextView) {
        val date = selectedDate ?: "Brak daty"
        val time = selectedTime ?: "Brak godziny"
        selectedDateTimeText.text = "Wybrana data i godzina: $date $time"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).updateToolbarTitle("Dodaj notatkę")

        (requireActivity() as MainActivity).setBackArrowVisibility(true)
    }
}
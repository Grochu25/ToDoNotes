package com.example.todonotes

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RemoteViews
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import com.example.todonotes.databinding.FragmentAddNotesBinding
import com.example.todonotes.viewModel.AddNotesViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Notification
import android.content.Context.ALARM_SERVICE
import android.icu.util.Calendar
import androidx.core.content.ContextCompat
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
class AddNotes : Fragment() {
    private lateinit var addNotesViewModel: AddNotesViewModel
    private lateinit var binding: FragmentAddNotesBinding

    private var selectedDate: String? = null
    private var selectedTime: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addNotesViewModel = ViewModelProvider(this, AddNotesViewModel.Factory())[AddNotesViewModel::class.java]

        binding = FragmentAddNotesBinding.inflate(inflater, container, false)
        binding.addNotesViewModel = addNotesViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cancelButton = requireActivity().findViewById<Button>(R.id.cancelButton)
        val saveButton = requireActivity().findViewById<Button>(R.id.saveButton)
        val selectDateButton = requireActivity().findViewById<Button>(R.id.selectDateButton)
        val selectTimeButton = requireActivity().findViewById<Button>(R.id.selectTimeButton)
        val selectedDateTimeText = requireActivity().findViewById<TextView>(R.id.selectedDateTimeText)

        cancelButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Potwierdzenie")
            builder.setMessage("Czy na pewno chcesz anulować dodawanie notatek?")

            builder.setNegativeButton("Nie"){ dialog, _ ->
                dialog.dismiss()
            }

            builder.setPositiveButton("Tak"){ _, _ ->
                Toast.makeText(requireContext(), "Kategoria została anulowana!", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            }

            var dialog = builder.create()
            dialog.show()        }

        saveButton.setOnClickListener{
            if(addNotesViewModel.categories.isEmpty())
            {
                Toast.makeText(requireContext(), "Najpierw stwórz kategorię!", Toast.LENGTH_LONG).show()
            }
            else if(selectedDate == null || selectedTime == null)
            {
                Toast.makeText(requireContext(), "Nie ustalono daty i godziny!", Toast.LENGTH_LONG).show()
            }
            else if(addNotesViewModel.title.value!! == "")
            {
                Toast.makeText(requireContext(), "Tytuł nie może być pusty", Toast.LENGTH_LONG).show()
            }
            else {
                addNotesViewModel.saveNoteToDatabase()
                addNotification()
                Toast.makeText(requireContext(), "Dodano poprawnie nową notatkę!", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        selectDateButton.setOnClickListener {
            showDatePicker(selectedDateTimeText)
        }

        selectTimeButton.setOnClickListener {
            showTimePicker(selectedDateTimeText)
        }

        (requireActivity() as MainActivity).updateToolbarTitle("Dodaj notatkę")

        (requireActivity() as MainActivity).setBackArrowVisibility(true)
    }


    @RequiresApi(Build.VERSION_CODES.O)
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
            addNotesViewModel.date.value = selectedDate
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
            Log.v("mango", selectedTime.toString())
            addNotesViewModel.time.value = selectedTime
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

    private fun addNotification()
    {
        (requireActivity() as MainActivity).addNotification(addNotesViewModel.title.value!!,addNotesViewModel.getInstant())
    }
}
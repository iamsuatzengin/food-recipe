package com.example.foodyrecipes.ui.add_shopping_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.foodyrecipes.R
import com.example.foodyrecipes.databinding.FragmentAddShoppingItemBinding
import com.example.foodyrecipes.reminder.AlarmScheduler
import com.example.foodyrecipes.ui.shopping_list.ShoppingViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class AddShoppingItemFragment : Fragment() {
    private var _binding: FragmentAddShoppingItemBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShoppingViewModel by viewModels()
    private val args: AddShoppingItemFragmentArgs by navArgs()

    private lateinit var calendar: Calendar
    private var selectedDateTimeInMillis: Long = 0

    @Inject
    lateinit var scheduler: AlarmScheduler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddShoppingItemBinding.inflate(inflater, container, false)

        binding.addShoppingContainer.transitionName = args.shoppingEntity?.id.toString()

        val animation =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        postponeEnterTransition(200, TimeUnit.MILLISECONDS)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleOnBackPressed()

        binding.topAppBar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        onMenuClickListener()

        val item = args.shoppingEntity
        item?.let { entity ->
            binding.editTextTitle.setText(entity.title)
            binding.editTextList.setText(entity.shoppingList)
        }

        calendar = Calendar.getInstance()

        datePicker.addOnPositiveButtonClickListener {
            calendar.timeInMillis = datePicker.selection!!
            timePicker.show(childFragmentManager, TIME_PICKER)
        }

        timePicker.addOnPositiveButtonClickListener {
            calendar[Calendar.HOUR] = timePicker.hour
            calendar[Calendar.MINUTE] = timePicker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            calendar.timeInMillis += timePicker.hour
            calendar.timeInMillis += timePicker.minute

            selectedDateTimeInMillis = calendar.timeInMillis

            scheduler.schedule(
                message = "Time to go to shopping 😊",
                timeInMillis = selectedDateTimeInMillis
            )
        }

    }

    private val datePicker = MaterialDatePicker.Builder
        .datePicker()
        .setTitleText("Select date")
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .build()

    private val timePicker = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_24H)
        .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
        .setHour(0)
        .setMinute(0)
        .setTitleText("Select time")
        .build()


    private fun onMenuClickListener(){
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.delete_from_list -> {
                    delete()
                    true
                }
                R.id.reminder -> {
                    datePicker.show(childFragmentManager, DATE_PICKER)
                    true
                }
                else -> false
            }

        }
    }

    private fun insert() {
        val editTextTitle = binding.editTextTitle.text
        val editTextMyShoppingList = binding.editTextList.text

        if(editTextTitle.isNullOrEmpty() && editTextMyShoppingList.isNullOrEmpty()) return

        viewModel.insert(
            title = editTextTitle.toString(),
            myShoppingList = editTextMyShoppingList.toString()
        )
    }

    private fun update(){
        val editTextTitle = binding.editTextTitle.text
        val editTextMyShoppingList = binding.editTextList.text

        if(editTextTitle.isNullOrEmpty() && editTextMyShoppingList.isNullOrEmpty()) return

        viewModel.update(
            title = editTextTitle.toString(),
            myShoppingList = editTextMyShoppingList.toString()
        )
    }

    private fun delete(){
        args.shoppingEntity?.let { entity ->
            viewModel.delete(entity = entity)
            findNavController().popBackStack()
        }
    }


    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if(args.shoppingEntity == null) insert()
                    else update()

                    isEnabled = false
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            })
    }


    companion object{
        const val TIME_PICKER = "TimePicker"
        const val DATE_PICKER = "DatePicker"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.foodyrecipes.ui.recipes.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.foodyrecipes.R
import com.example.foodyrecipes.databinding.FragmentFilterBottomSheetBinding
import com.example.foodyrecipes.ui.recipes.FoodRecipesViewModel
import com.example.foodyrecipes.util.Constants.DEFAULT_DIET_TYPE
import com.example.foodyrecipes.util.Constants.DEFAULT_MEAL_TYPE
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class FilterBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentFilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0

    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    private val foodRecipesViewModel: FoodRecipesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                foodRecipesViewModel.mealAndDietTypeState.collect{ mealAndDietType ->

                    mealTypeChip = mealAndDietType.mealType
                    dietTypeChip = mealAndDietType.dietType

                    setChipChecked(mealAndDietType.mealTypeId, binding.mealTypeChipGroup)
                    setChipChecked(mealAndDietType.dietTypeId, binding.dietTypeChipGroup)
                }
            }
        }

        binding.mealTypeChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip = group.findViewById<Chip>(checkedIds[0])
            val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
            mealTypeChip = selectedMealType
            mealTypeChipId = checkedIds[0]
            println(mealTypeChip)
        }

        binding.dietTypeChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip = group.findViewById<Chip>(checkedIds[0])
            val selectedDietType = chip.text.toString().lowercase(Locale.ROOT)
            dietTypeChip = selectedDietType
            dietTypeChipId = checkedIds[0]
        }

        binding.buttonApply.setOnClickListener {
            foodRecipesViewModel.saveMealAndDietType(
                mealType = mealTypeChip,
                mealTypeId = mealTypeChipId,
                dietType = dietTypeChip,
                dietTypeId = dietTypeChipId
            )
            findNavController().navigate(R.id.action_filterBottomSheet_to_foodRecipesFragment)
        }
    }

    private fun setChipChecked(chipId: Int, chipGroup: ChipGroup){
        if(chipId != 0){
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            }catch (e: Exception){
                println("error bottm-sheet ${e.message}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
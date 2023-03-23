package com.example.foodyrecipes.ui.detail.overview

import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.foodyrecipes.R
import com.example.foodyrecipes.data.network.dto.RecipeResult
import com.example.foodyrecipes.databinding.FragmentOverviewBinding
import com.example.foodyrecipes.util.Constants.FOOD_PAGER_KEY
import org.jsoup.Jsoup


class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(FOOD_PAGER_KEY) }?.apply {
            val food = if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getParcelable(FOOD_PAGER_KEY, RecipeResult::class.java)
            } else {
                getParcelable(FOOD_PAGER_KEY)
            }
            setUi(foodRecipe = food)

        }
    }

    private fun setUi(foodRecipe: RecipeResult?) {
        foodRecipe?.let { food ->
            val summary = Jsoup.parse(food.summary).text()
            binding.tvDescripiton.text = summary
            checkFeature(food = food)
        }
    }

    private fun checkFeature(food: RecipeResult) {

        if (food.vegan) setViewColor(binding.iconCheckVegan, binding.tvVegan)

        if (food.veryHealthy) setViewColor(binding.iconCheckHealthy, binding.tvHealthy)

        if (food.cheap) setViewColor(binding.iconCheckCheap, binding.tvCheap)

        if (food.dairyFree) setViewColor(binding.iconCheckDairyFree, binding.tvDairyFree)

        if (food.vegetarian) setViewColor(binding.iconCheckVegetarian, binding.tvVegetarian)

        if (food.glutenFree) setViewColor(binding.iconCheckGlutenFree, binding.tvGlutenFree)

    }

    private fun setViewColor(imageView: ImageView, textView: TextView) {
        imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
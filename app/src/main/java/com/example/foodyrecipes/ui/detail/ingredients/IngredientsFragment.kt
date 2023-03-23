package com.example.foodyrecipes.ui.detail.ingredients

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodyrecipes.data.network.dto.RecipeResult
import com.example.foodyrecipes.databinding.FragmentIngredientsBinding
import com.example.foodyrecipes.util.Constants

class IngredientsFragment : Fragment() {
    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: IngredientAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.takeIf { it.containsKey(Constants.FOOD_PAGER_KEY) }?.apply {
            val food = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getParcelable(Constants.FOOD_PAGER_KEY, RecipeResult::class.java)
            } else {
                getParcelable(Constants.FOOD_PAGER_KEY)
            }
            if (food == null) return
            else adapter = IngredientAdapter(list = food.extendedIngredients)
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.example.foodyrecipes.ui.recipes

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodyrecipes.R
import com.example.foodyrecipes.databinding.FragmentFoodRecipesBinding
import com.example.foodyrecipes.ui.recipes.adapter.FoodAdapter
import com.example.foodyrecipes.util.NetworkListener
import com.example.foodyrecipes.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodRecipesFragment : Fragment() {

    private var _binding: FragmentFoodRecipesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: FoodRecipesViewModel by viewModels()

    private lateinit var networkListener: NetworkListener
    private val adapter: FoodAdapter by lazy { FoodAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFoodRecipesBinding.inflate(inflater, container, false)

        networkListener = NetworkListener()

        lifecycleScope.launch {
            networkListener.checkNetworkAvailability(requireContext()).collect { status ->
                Log.d("NetworkListener", status.toString())
                viewModel.networkStatus.value = status
            }
        }

        val animation = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        observeFoodRecipes()

        viewModel.networkStatus.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.getRecipes()
                Toast.makeText(requireContext(), "Bağlandı", Toast.LENGTH_SHORT).show()
                binding.noInternetConnectionLayout.root.visibility = View.INVISIBLE
            } else {
                Toast.makeText(requireContext(), "no internet", Toast.LENGTH_SHORT).show()
                binding.noInternetConnectionLayout.root.visibility = View.VISIBLE
                stopShimmer()
            }
        }

        binding.filterFab.setOnClickListener {
            findNavController().navigate(R.id.action_foodRecipesFragment_to_filterBottomSheet)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchFoodRecipe(query = query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        adapter.setItemOnClickListener { recipeResult, imageView ->
            val action =
                FoodRecipesFragmentDirections.actionFoodRecipesFragmentToDetailFragment(foodRecipe = recipeResult)
            val extras = FragmentNavigatorExtras(
                imageView to recipeResult.image
            )
            findNavController().navigate(action, extras)
        }
    }

    private fun observeFoodRecipes() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> {
                            binding.shimmerLayout.startShimmer()
                        }
                        is NetworkResult.Success -> {
                            if (result.data?.results.isNullOrEmpty()) println("liste boş")
                            else {
                                result.data?.results!!.forEach {
                                    println("name: ${it.title}")
                                    adapter.submitList(result.data.results)
                                }
                            }
                            stopShimmer()
                        }
                        is NetworkResult.Error -> {
                            Toast.makeText(
                                requireContext(),
                                result.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                            stopShimmer()
                        }
                    }

                }
            }
        }
    }

    private fun searchFoodRecipe(query: String) {
        viewModel.searchRecipes(query = query)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchedRecipeState.collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> {
                            binding.shimmerLayout.startShimmer()
                        }
                        is NetworkResult.Success -> {
                            if (result.data?.results.isNullOrEmpty()) println("liste boş")
                            else {
                                adapter.submitList(result.data?.results!!)
                            }
                            stopShimmer()
                        }
                        is NetworkResult.Error -> {}
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        postponeEnterTransition()
        recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun stopShimmer() {
        binding.shimmerLayout.stopShimmer()
        binding.shimmerLayout.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
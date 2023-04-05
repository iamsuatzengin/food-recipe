package com.example.foodyrecipes.ui.shopping_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.TransitionInflater
import com.example.foodyrecipes.databinding.FragmentShoppingListBinding
import com.example.foodyrecipes.ui.shopping_list.adapter.ShoppingListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShoppingListFragment : Fragment() {
    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShoppingViewModel by viewModels()
    private val adapter: ShoppingListAdapter by lazy { ShoppingListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)

        val animation = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.insertFab.setOnClickListener {
            navigateToAdd()
        }

        adapter.setOnClickListener { entity, container ->
            val action = ShoppingListFragmentDirections.actionShoppingListFragmentToAddShoppingItemFragment(entity)
            val extras = FragmentNavigatorExtras(
                container to entity.id.toString()
            )
            findNavController().navigate(action, extras)
        }

        lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{list ->
                    if(list.isEmpty()){
                        binding.recyclerView.visibility = View.INVISIBLE
                        return@collect
                    }
                    binding.recyclerView.visibility = View.VISIBLE
                    adapter.submitList(list)
                }
            }
        }
    }

    private fun setupRecyclerView(){
        val recyclerView = binding.recyclerView
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.adapter = adapter

        postponeEnterTransition()
        recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun navigateToAdd(){
        val action = ShoppingListFragmentDirections.actionShoppingListFragmentToAddShoppingItemFragment(null)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
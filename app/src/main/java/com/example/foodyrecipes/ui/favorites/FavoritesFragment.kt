package com.example.foodyrecipes.ui.favorites

import android.os.Bundle
import android.transition.TransitionInflater
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
import com.example.foodyrecipes.databinding.FragmentFavoritesBinding
import com.example.foodyrecipes.ui.favorites.adapter.FavoritesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()
    private val adapter: FavoritesAdapter by lazy { FavoritesAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        val animation =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()



        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.favorites.collect { list ->
                    if(list.isEmpty()){
                        println("liste boş")
                        return@collect
                    }
                    adapter.submitList(list)
                }
            }
        }
    }

    private fun setupRecyclerView(){
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        postponeEnterTransition()
        recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }

        adapter.setOnItemClickListener { favoriteEntity, imageView ->
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(favoriteEntity.recipeResult)
            val extras = FragmentNavigatorExtras(
                imageView to favoriteEntity.recipeResult.image
            )
            findNavController().navigate(action, extras)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.foodyrecipes.ui.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.foodyrecipes.R
import com.example.foodyrecipes.data.local.entity.FavoriteEntity
import com.example.foodyrecipes.databinding.FragmentDetailBinding
import com.example.foodyrecipes.ui.detail.view_pager.DetailViewPagerAdapter
import com.example.foodyrecipes.util.Constants.FOOD_PAGER_KEY
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var viewPagerAdapter: DetailViewPagerAdapter
    private lateinit var viewPager: ViewPager2

    private val viewModel: DetailViewModel by viewModels()

    private var isSaved: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        val animation =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        postponeEnterTransition(200, TimeUnit.MICROSECONDS)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.foodRecipe = args.foodRecipe

        val bundle = Bundle()
        bundle.putParcelable(FOOD_PAGER_KEY, args.foodRecipe)
        viewPagerAdapter = DetailViewPagerAdapter(this, bundle)
        viewPager = binding.viewPager
        viewPager.adapter = viewPagerAdapter

        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Overview"
                1 -> tab.text = "Ingredients"
            }
        }.attach()

        observeEvent()
        isSaved()

        with(binding) {
            favoriteButton.setOnClickListener {
                if(isSaved) deleteFavoriteRecipe()
                else addFavoriteRecipe()
            }
        }
        viewModel.foodIsSaved(args.foodRecipe.id)
    }

    private fun addFavoriteRecipe() {
        val entity = FavoriteEntity(
            id = 0, args.foodRecipe
        )
        viewModel.addFavoriteRecipe(entity = entity)
        binding.favoriteButton.setImageResource(R.drawable.ic_bookmark_24)
    }

    private fun deleteFavoriteRecipe(){
        viewModel.deleteFavoriteRecipe(args.foodRecipe)
        binding.favoriteButton.setImageResource(R.drawable.ic_bookmark_border_24)
    }

    private fun observeEvent() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collectLatest { event ->
                    when (event) {
                        is UiEvent.ShowMessage -> {
                            showSnackbar(message = event.message)
                        }
                    }
                }
            }
        }
    }

    private fun isSaved() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isSaved.collectLatest { isFoodSaved ->
                    isSaved = if (isFoodSaved) {
                        binding.favoriteButton.setImageResource(R.drawable.ic_bookmark_24)
                        true
                    } else {
                        binding.favoriteButton.setImageResource(R.drawable.ic_bookmark_border_24)
                        false
                    }
                }
            }
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setAction("Okay", null)
            .show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
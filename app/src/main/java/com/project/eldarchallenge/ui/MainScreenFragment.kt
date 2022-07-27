package com.project.eldarchallenge.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.project.eldarchallenge.R
import com.project.eldarchallenge.databinding.FragmentMainScreenBinding
import com.project.eldarchallenge.db.CardsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreenFragment : Fragment() {
    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var cardsAdapter: CardsAdapter
    private val cardsViewModel by viewModels<CardsViewModel>()
    private var userMatches = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cardsAdapter = CardsAdapter()
        binding.rvCards.adapter = cardsAdapter
        cardsViewModel.readCardsData.observe(viewLifecycleOwner) { cards ->
            if (cards.isEmpty() || cards.none { userValidation(it.name, it.lastName) }) {
                binding.tvEmptyState.text = getString(R.string.main_screen_empty_state)
                binding.tvEmptyState.visibility = View.VISIBLE
            } else {
                val data = cards.filter {
                    userValidation(it.name, it.lastName)
                }
                cardsAdapter.setData(data)
                binding.tvEmptyState.visibility = View.GONE
            }
        }

        binding.btAddCard.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.AddCardFragment)
        }
        binding.btPayQr.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.QrFragment)
        }
    }

    private fun userValidation(userName:String, userLastName:String): Boolean{
        userMatches = false
        val sharedPref = activity?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val name = sharedPref?.getString("name", "")
        val lastName = sharedPref?.getString("lastName", "")
        userMatches = userName == name && userLastName == lastName
        return userMatches
    }
}
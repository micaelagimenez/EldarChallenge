package com.project.eldarchallenge.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.project.eldarchallenge.R
import com.project.eldarchallenge.databinding.FragmentAddCardBinding
import com.project.eldarchallenge.db.Cards
import com.project.eldarchallenge.db.CardsViewModel
import com.project.eldarchallenge.utils.encrypt
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddCardFragment : Fragment() {
    private lateinit var binding: FragmentAddCardBinding
    private val cardsViewModel by viewModels<CardsViewModel>()
    private var userMatches = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var brandName = ""

        val watcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // enable "add" button if fields are not empty
                val securityCodeNotEmpty: Boolean =
                    binding.etSecurityCode.text?.isNotEmpty() == true
                val expirationDateNotEmpty: Boolean = binding.etExpDate.text?.isNotEmpty() == true
                val nameNotEmpty: Boolean = binding.etName.text?.isNotEmpty() == true
                val lastNameNotEmpty: Boolean = binding.etLastName.text?.isNotEmpty() == true
                binding.btnAdd.isEnabled = securityCodeNotEmpty && expirationDateNotEmpty &&
                            nameNotEmpty && lastNameNotEmpty
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit
        }

        val watcherBrand: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Detect card brand name
                if (s.isNotEmpty()) {
                    if (s[0].toString() == "3") {
                        binding.tvCardType.text = getString(R.string.add_card_fragment_brand_american)
                        brandName = getString(R.string.add_card_fragment_brand_american)
                    } else if (s[0].toString() == "4") {
                        binding.tvCardType.text = getString(R.string.add_card_fragment_brand_visa)
                        brandName = getString(R.string.add_card_fragment_brand_visa)
                    } else {
                        binding.tvCardType.text = getString(R.string.add_card_fragment_brand_mastercard)
                        brandName = getString(R.string.add_card_fragment_brand_mastercard)
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit
        }

        binding.tfNumber.editText?.addTextChangedListener(watcherBrand)
        binding.tfName.editText?.addTextChangedListener(watcher)
        binding.tfLastName.editText?.addTextChangedListener(watcher)
        binding.tfSecurityCode.editText?.addTextChangedListener(watcher)
        binding.tfExpDate.editText?.addTextChangedListener(watcher)

        binding.btnAdd.setOnClickListener {
            if(userValidation(binding.etName.text.toString(), binding.etLastName.text.toString())){
                // if user matches card's info, encrypt important data and save card in db
                val card = encrypt(binding.etNumber.text.toString())?.let { number ->
                    encrypt(binding.etSecurityCode.text.toString())?.let { secCode ->
                        encrypt(binding.etExpDate.text.toString())?.let { expDate ->
                            Cards(0,
                                binding.etName.text.toString(),
                                binding.etLastName.text.toString(),
                                brandName,
                                number,
                                secCode,
                                expDate
                            )
                        }
                    }
                }
                if (card != null) {
                    cardsViewModel.addCard(card)
                }
                NavHostFragment.findNavController(this).navigate(R.id.MainScreenFragment)
            } else {
                // if user data does not match card's, show error
                binding.tfName.isErrorEnabled
                binding.etName.error = getString(R.string.add_card_fragment_error_id_add_card)
                binding.tfLastName.isErrorEnabled
                binding.etLastName.error = getString(R.string.add_card_fragment_error_id_add_card)
            }
        }
    }

    private fun userValidation(userName: String, userLastName: String): Boolean {
        userMatches = false
        val sharedPref = activity?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val name = sharedPref?.getString("name", "")
        val lastName = sharedPref?.getString("lastName", "")
        userMatches = userName == name && userLastName == lastName
        return userMatches
    }
}

package com.example.dataentryapp.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.dataentryapp.UserApplication
import com.example.dataentryapp.adapter.UserInfoAdapter
import com.example.dataentryapp.databinding.FragmentMainBinding
import com.example.dataentryapp.room.UserInfoEntity
import com.example.dataentryapp.viewmodel.UserViewModel
import com.example.dataentryapp.viewmodel.UserViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.util.*
import java.util.regex.Pattern


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    private val wordViewModel: UserViewModel by viewModels {
        UserViewModelFactory((activity?.application as UserApplication).repository)
    }

    val userAdapter = UserInfoAdapter()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSave.setOnClickListener {

            wordViewModel.insert(UserInfoEntity(name =binding.userName.text.toString(), email =  binding.email.text.toString()))
        }

        //To check if the email is valid or not
        binding.email.addTextChangedListener {
            validateField()
        }
        binding.userName.addTextChangedListener {
            validateField()
        }

        userAdapter.itemClickListener = {
            wordViewModel.delete(it.id)
            Snackbar.make(view,
                it.email + " is removed", Snackbar.LENGTH_LONG).show();
        }

        binding.recyclerview.adapter = userAdapter
        wordViewModel.allWords.observe(viewLifecycleOwner, Observer { userInfo ->
            Collections.reverse(userInfo)
            userAdapter.submitList(userInfo)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun validateField(){
        binding.buttonSave.isEnabled = isValidEmail(binding.email.text.toString()) && binding.userName.text.isNotEmpty()

    }
}
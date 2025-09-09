package com.capstone.surfingthegangwon.presentation.sessionReading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.surfingthegangwon.presentation.sessionReading.databinding.FragmentSessionReadingBinding

class SessionReadingFragment : Fragment() {
    private lateinit var binding: FragmentSessionReadingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSessionReadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        setTopToolBar()
    }

    private fun setTopToolBar() {
        binding.topAppBar.setTitle(getString(R.string.together))
        binding.topAppBar.setOnBackClick {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
    }
}
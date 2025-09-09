package com.capstone.surfingthegangwon.presentation.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.capstone.surfingthegangwon.presentation.mypage.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        setToolBar()
        setOnClickListeners()
    }

    private fun setToolBar() {
        binding.topAppBar.setTitle(getString(R.string.title_my))
        binding.topAppBar.hideBackClick()
    }

    private fun setOnClickListeners() {
        binding.createdStatus.setOnClickListener {
            findNavController().navigate(
                MyPageFragmentDirections.actionMyPageToSessionStatus(MOVE_CREATED)
            )
        }
        binding.reservedStatus.setOnClickListener {
            findNavController().navigate(
                MyPageFragmentDirections.actionMyPageToSessionStatus(MOVE_RESERVED)
            )
        }
    }

    companion object {
        private const val TAG = "MyPageFragment"
        private const val MOVE_CREATED = "CREATED"
        private const val MOVE_RESERVED = "RESERVED"
    }
}
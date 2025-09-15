package com.capstone.surfingthegangwon.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.capstone.surfingthegangwon.core.auth.TokenStore
import com.capstone.surfingthegangwon.presentation.mypage.databinding.FragmentMyPageBinding
import com.capstone.surfingthegangwon.presentation.sessionstatus.SessionStatusFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding

    @Inject
    lateinit var tokenStore: TokenStore

    private val myPageViewModel: MyPageViewModel by viewModels()

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
        setUserProfile()
    }

    private fun setUserProfile() {
        myPageViewModel.userName.observe(viewLifecycleOwner) { name ->
            binding.nickname.text = name
        }
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
        binding.logoutBtn.setOnClickListener {
            tokenStore.clear()
            switchLoginActivity()
        }
    }

    private fun switchLoginActivity() {
        val intent = Intent("com.capstone.surfingthegangwon.action.LOGIN").apply {
            // 같은 앱 내에서만 처리되도록 안전장치
            `package` = requireContext().packageName
            // 스택 제거
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        requireActivity().finish()
    }

    companion object {
        private const val TAG = "MyPageFragment"
        private const val MOVE_CREATED = SessionStatusFragment.MODE_CREATED
        private const val MOVE_RESERVED = SessionStatusFragment.MODE_RESERVED
    }
}
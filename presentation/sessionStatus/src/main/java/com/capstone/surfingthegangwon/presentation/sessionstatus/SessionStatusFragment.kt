package com.capstone.surfingthegangwon.presentation.sessionstatus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.surfingthegangwon.presentation.sessionstatus.databinding.FragmentSessionStatusBinding
import com.capstone.surfingthegangwon.presentation.together.TogetherFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SessionStatusFragment : Fragment() {
    private lateinit var binding: FragmentSessionStatusBinding
    private val args: SessionStatusFragmentArgs by navArgs()

    private lateinit var sessionAdapter: SessionAdapter
    private var sessionState = ""
    private val sessionViewModel: SessionStatusViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSessionStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val status = args.status
        sessionState = status
        initUI(status)
    }

    override fun onResume() {
        super.onResume()
        initUI(sessionState)
    }

    private fun initUI(status: String) {
        var titleName = ""

        if (status == MODE_CREATED) {
            titleName = getString(R.string.recruitment_status)
            initCreatedModeUi()
        } else if (status == MODE_RESERVED) {
            titleName = getString(R.string.reservation_status)
            initReservedModeUi()
        }
        setToolBar(titleName)
        setupSessionRecyclerView()
        observeSessions()
    }

    private fun initCreatedModeUi() {
        sessionViewModel.loadWrittenPosts()
    }

    private fun initReservedModeUi() {
        sessionViewModel.loadReservedPosts()
    }

    private fun observeSessions() {
        sessionViewModel.sessions.observe(viewLifecycleOwner) { items ->
            sessionAdapter.submitList(items)
        }
    }

    private fun setToolBar(titleName: String = "error") {
        binding.topAppBar.setTitle(titleName)
        binding.topAppBar.setOnBackClick {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    /** 세션 RecyclerView 설정 */
    private fun setupSessionRecyclerView() {
        sessionAdapter = SessionAdapter { session ->
            val action =
                SessionStatusFragmentDirections.actionSessionStatuseToSessionReading(session)
            findNavController().navigate(action)
        }
        binding.sessionRcv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = sessionAdapter
        }
    }

    companion object {
        private const val TAG = "SessionStatusFragment"
        const val MODE_CREATED = "CREATED"
        const val MODE_RESERVED = "RESERVED"
    }
}
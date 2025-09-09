package com.capstone.surfingthegangwon.presentation.sessionstatus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.surfingthegangwon.presentation.sessionstatus.databinding.FragmentSessionStatusBinding

class SessionStatusFragment : Fragment() {
    private lateinit var binding: FragmentSessionStatusBinding
    private val args: SessionStatusFragmentArgs by navArgs()

    private lateinit var sessionAdapter: SessionAdapter

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
        initUI(status)
    }

    private fun initUI(status: String) {
        var titleName = ""

        if (status == MOVE_CREATED) {
            titleName = getString(R.string.recruitment_status)
        } else if (status == MOVE_RESERVED) {
            titleName = getString(R.string.reservation_status)
        }
        setToolBar(titleName)
        setupSessionRecyclerView()
    }

    private fun setToolBar(titleName: String = "error") {
        binding.topAppBar.setTitle(titleName)
        binding.topAppBar.setOnBackClick {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    /** 세션 RecyclerView 설정 */
    private fun setupSessionRecyclerView() {
        sessionAdapter = SessionAdapter()
        binding.sessionRcv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = sessionAdapter
        }
    }

    companion object {
        private const val TAG = "SessionStatusFragment"
        private const val MOVE_CREATED = "CREATED"
        private const val MOVE_RESERVED = "RESERVED"
    }
}
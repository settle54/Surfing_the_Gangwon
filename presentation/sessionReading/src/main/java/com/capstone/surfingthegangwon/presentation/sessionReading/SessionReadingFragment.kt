package com.capstone.surfingthegangwon.presentation.sessionReading

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.capstone.surfingthegangwon.core.model.PostAction
import com.capstone.surfingthegangwon.core.model.SessionItem
import com.capstone.surfingthegangwon.core.model.SessionState
import com.capstone.surfingthegangwon.presentation.sessionReading.databinding.FragmentSessionReadingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SessionReadingFragment : Fragment() {
    private lateinit var binding: FragmentSessionReadingBinding
    private val args: SessionReadingFragmentArgs by navArgs()

    private val readingViewModel: SessionReadingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSessionReadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val session = args.session
        readingViewModel.setPost(session)
        Log.d(TAG, "세션 불러오기 성공: $session")
        initUI()
    }

    private fun initUI() {
        setTopToolBar()
        observeSession()
    }

    private fun setOnClickCompleteWindow() {
        binding.cancelInviteButton.setOnClickListener {
            readingViewModel.deletePost()
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.completeInviteButton.setOnClickListener {
            readingViewModel.closePost()
            binding.invitedWindow.isVisible = false
            binding.completedWindow.isVisible = true
        }
    }

    private fun setOnClickCancelWindow() {
        binding.cancelJoinButton.setOnClickListener {
            readingViewModel.cancelPost()
            binding.joinedWindow.isVisible = false
            binding.unjoinedWindow.isVisible = true
        }
    }

    private fun setOnClickJoinWindow() {
        binding.joinButton.setOnClickListener {
            readingViewModel.joinPost()
            binding.unjoinedWindow.isVisible = false
            binding.joinedWindow.isVisible = true
        }
    }

    private fun observeSession() {
        readingViewModel.session.observe(viewLifecycleOwner) { session ->
            setContents(session)
        }
    }

    private fun setContents(session: SessionItem) {
        binding.area.text = session.city
        binding.beach.text = session.seashore
        binding.sessionDate.text = session.sessionDate
        binding.sessionName.text = session.title
        binding.sessionTimeDetail.text = session.sessionTime
        binding.numberParticipantsDetail.text = session.participants
        binding.description.text = session.contents
        binding.phoneNumber.text = session.phoneNumber
        binding.grade.setGrade(session.grade)
        when (session.state) {
            SessionState.CLOSE -> setCompletedSessionWindow(session.action)
            SessionState.OPEN -> selectPostAction(session.action)
        }
    }

    private fun selectPostAction(action: PostAction) {
        when (action) {
            PostAction.Cancel -> {
                binding.joinedWindow.isVisible = true
                setOnClickCancelWindow()
            }

            PostAction.Complete -> {
                binding.invitedWindow.isVisible = true
                setOnClickCompleteWindow()
            }

            PostAction.Join -> {
                binding.unjoinedWindow.isVisible = true
                setOnClickJoinWindow()
            }
        }
    }

    private fun setCompletedSessionWindow(action: PostAction) {
        binding.completedWindow.isVisible = true
        if (action != PostAction.Complete) {
            binding.completedJoinInstruction.isVisible = false
        }
    }

    private fun setTopToolBar() {
        binding.topAppBar.setTitle(getString(R.string.together))
        binding.topAppBar.setOnBackClick {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        private const val TAG = "SessionReadingFragment"
    }
}
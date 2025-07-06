package com.taskmaster.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.taskmaster.databinding.FragmentProfileBinding
import com.taskmaster.ui.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        profileViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let { updateUI(it) }
        }
    }

    private fun updateUI(user: com.taskmaster.data.entity.User) {
        binding.apply {
            textName.text = user.name
            textUsername.text = "@${user.username}"
            textCity.text = user.city
            textLevel.text = "Уровень ${user.level}"
            textXp.text = "${user.totalXp} XP"
            textStreak.text = "${user.currentStreak} дней"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.githubuser.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.Adapter.UserAdapter
import com.example.githubuser.DetailActivity
import com.example.githubuser.R
import com.example.githubuser.ViewModel.ViewFollowing
import com.example.githubuser.databinding.FragmentFollowingBinding

@Suppress("DEPRECATION")
class FollowingFragment : Fragment(R.layout.fragment_following) {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingViewModel: ViewFollowing
    private lateinit var userListAdapter: UserAdapter
    private lateinit var githubUsername: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        githubUsername = arguments?.getString(DetailActivity.USERNAME_PACKAGE).toString()

        _binding = FragmentFollowingBinding.bind(view)

        userListAdapter = UserAdapter()
        userListAdapter.notifyDataSetChanged()

        binding.rvFollowing.setHasFixedSize(true)
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.adapter = userListAdapter

        showBuffer(true)

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(ViewFollowing::class.java)
        followingViewModel.setListFollowing(githubUsername)
        followingViewModel.getListFollowing().observe(viewLifecycleOwner, {
            if (it != null) {
                userListAdapter.setterList(it)
                showBuffer(false)
            }
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun showBuffer(state: Boolean) {
        if (state) {
            binding.barProgress.visibility = View.VISIBLE
        } else {
            binding.barProgress.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

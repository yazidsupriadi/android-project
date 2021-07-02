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
import com.example.githubuser.ViewModel.ViewFollowers
import com.example.githubuser.databinding.FragmentFollowersBinding

@Suppress("DEPRECATION")
class FollowersFragment : Fragment(R.layout.fragment_followers) {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var userListAdapter: UserAdapter
    private lateinit var username: String
    private lateinit var followersViewModel: ViewFollowers

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = arguments?.getString(DetailActivity.USERNAME_PACKAGE).toString()
        _binding = FragmentFollowersBinding.bind(view)

        userListAdapter = UserAdapter()
        userListAdapter.notifyDataSetChanged()

        binding.rvFollowers.setHasFixedSize(true)
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowers.adapter = userListAdapter

        showData(true)

        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(ViewFollowers::class.java)

        followersViewModel.setListFollowers(username)
        followersViewModel.getListFollowers().observe(viewLifecycleOwner, {
            if (it != null) {
                userListAdapter.setterList(it)
                showData(false)
            }
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun showData(state: Boolean) {
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
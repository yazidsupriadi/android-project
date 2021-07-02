package com.example.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.Adapter.UserAdapter
import com.example.githubuser.ViewModel.UserView
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.retrofit.UserModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userListAdapter: UserAdapter
    private lateinit var userViewModel: UserView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nameSearch = resources.getString(R.string.user)
        binding.search.hint = nameSearch

        userListAdapter = UserAdapter()
        userListAdapter.notifyDataSetChanged()

        binding.apply {
            rvGithub.setHasFixedSize(true)
            rvGithub.layoutManager = LinearLayoutManager(this@MainActivity)
            rvGithub.adapter = userListAdapter

            search.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    userSearch()
                    showBuffer(true)
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

            btnSearch.setOnClickListener {
                userSearch()
            }
        }

        userListAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserModel) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.USERNAME_PACKAGE, data.login)
                startActivity(intent)
            }
        })

        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(UserView::class.java)
        userViewModel.getSearchUser().observe(this, {
            if (it != null) {
                userListAdapter.setterList(it)
                showBuffer(false)
            }
        })
    }

    private fun userSearch() {
        binding.apply {
            val query = search.text.toString()

            if (query.isEmpty())
                showBuffer(false)
            userViewModel.setUserSearch(query)
        }
    }

    private fun showBuffer(state: Boolean) {
        if (state) {
            binding.apply {
                barProgress.visibility = View.VISIBLE
                btnSearch.visibility = View.VISIBLE
                rvGithub.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                barProgress.visibility = View.INVISIBLE
                btnSearch.visibility = View.INVISIBLE
                rvGithub.visibility = View.VISIBLE
            }
        }
    }

}
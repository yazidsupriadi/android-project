package com.example.githubuser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.ViewModel.UserDetailView
import com.example.githubuser.Adapter.PagerAdapter
import com.example.githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object {
        const val USERNAME_PACKAGE = "username"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: UserDetailView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val followers = resources.getString(R.string.Followers)
        val following = resources.getString(R.string.Following)
        val repository = resources.getString(R.string.Repository)
        binding.apply {
            followersDetail.text = followers
            detailFollowing.text = following
            detailRepository.text = repository
        }

        val gitUsername = intent.getStringExtra(USERNAME_PACKAGE)
        val saveData = Bundle()
        saveData.putString(USERNAME_PACKAGE, gitUsername)

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
                .get(UserDetailView::class.java)
        detailViewModel.setUserDetail(gitUsername.toString())
        detailViewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    name.text = it.name
                    username.text = it.login
                    followersDetail.text = it.followers.toString()
                    detailFollowing.text = it.following.toString()
                    detailRepository.text = it.public_repos.toString()
                    company.text = it.company ?: "-"
                    location.text = it.location ?: "-"
                    Glide.with(this@DetailActivity)
                            .load(it.avatar_url)
                            .into(imageUser)
                }
            }
        })

        val pagerAdapter = PagerAdapter(this, supportFragmentManager, saveData)

        binding.viewPager.adapter = pagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
    }
}
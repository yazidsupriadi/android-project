package com.example.githubuser.Adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.githubuser.Fragment.FollowersFragment
import com.example.githubuser.Fragment.FollowingFragment
import com.example.githubuser.R

class PagerAdapter(private val context: Context, fragManager: FragmentManager, data: Bundle)
    : FragmentPagerAdapter(fragManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentBundle: Bundle = data

    @StringRes
    private val tabName = intArrayOf(R.string.tab1, R.string.tab2)

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }
    override fun getPageTitle(position: Int): CharSequence = context.resources.getString(tabName[position])
}
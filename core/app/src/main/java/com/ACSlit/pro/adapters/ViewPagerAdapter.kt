package com.ACSlit.pro.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ACSlit.pro.artificial.agents.AIAgentManager
import com.ACSlit.pro.fragments.ChatFragment
import com.ACSlit.pro.fragments.AIHistoryFragment

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val aiAgent: AIAgentManager
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ChatFragment(aiAgent)
            1 -> AIHistoryFragment(aiAgent)
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}
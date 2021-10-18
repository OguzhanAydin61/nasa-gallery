package com.avatarosko.nasagallery.ui.photo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.avatarosko.nasagallery.common.model.RoverTypes


class PhotoViewPagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = RoverTypes.values().size

    override fun createFragment(position: Int): Fragment =
        PhotoFragment.newInstance(RoverTypes.values()[position])


}
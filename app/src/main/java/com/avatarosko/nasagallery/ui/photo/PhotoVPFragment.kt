package com.avatarosko.nasagallery.ui.photo

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avatarosko.nasagallery.R
import com.avatarosko.nasagallery.common.model.RoverTypes
import com.avatarosko.nasagallery.databinding.PhotoVpFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoVPFragment : Fragment(R.layout.photo_vp_fragment) {
    private val binding: PhotoVpFragmentBinding by viewBinding(PhotoVpFragmentBinding::bind)
    private val roverTypes by lazy { RoverTypes.values() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            roverTypes.forEach { roverType ->
                bottom.menu.add(Menu.NONE, roverType.idRes, Menu.NONE, roverType.name)
                    .setIcon(roverType.icon)
            }
            pager.adapter = PhotoViewPagerAdapter(this@PhotoVPFragment)
            pager.isUserInputEnabled = false

            bottom.setOnItemSelectedListener { menu ->
                val index = roverTypes.indexOfFirst {
                    it.idRes == menu.itemId
                }
                pager.setCurrentItem(index, false)
                true
            }
        }
    }
}

package com.avatarosko.nasagallery.ui.photo

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avatarosko.nasagallery.R
import com.avatarosko.nasagallery.common.EndlessScrollListener
import com.avatarosko.nasagallery.common.model.RoverTypes
import com.avatarosko.nasagallery.databinding.PhotoFragmentBinding
import com.avatarosko.nasagallery.ui.filter.FilterFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PhotoFragment : Fragment(R.layout.photo_fragment) {

    companion object {
        private const val ARG_TYPE = "Arg.Type"
        fun newInstance(roverType: RoverTypes) = PhotoFragment().apply {
            arguments = bundleOf(ARG_TYPE to roverType)
        }
    }

    private val gridLayoutManager by lazy { GridLayoutManager(requireContext(), 4) }
    private val endlessScrollListener by lazy {
        object : EndlessScrollListener(gridLayoutManager) {
            override fun onLoadMore(page: Int) {
                vm.fetchRoversPhoto(arg, page)
            }

        }
    }
    private val adapter by lazy {
        PhotosAdapter {
            findNavController().navigate(
                PhotoVPFragmentDirections.actionGlobalDetailPhotoDialog(
                    vehicleName = it.mRover?.mName.orEmpty(),
                    imgSrc = it.mImgSrc.orEmpty(),
                    pictureDate = it.mEarthDate.orEmpty(),
                    cameraName = it.mCamera?.mFullName.orEmpty(),
                    vehicleStatus = it.mRover?.mStatus.orEmpty(),
                    landingDate = it.mRover?.mLandingDate.orEmpty(),
                    launchDate = it.mRover?.mLaunchDate.orEmpty()
                )
            )
        }
    }
    private val binding: PhotoFragmentBinding by viewBinding(
        PhotoFragmentBinding::bind,
        // For New version
        onViewDestroyed = {
            it.photoList.adapter = null
            it.photoList.layoutManager = null
            it.photoList.removeOnScrollListener(endlessScrollListener)
        })
    private val vm: PhotoViewModel by viewModels()
    private val arg: RoverTypes by lazy {
        requireArguments().getParcelable<RoverTypes>(ARG_TYPE)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        vm.fetchRoversPhoto(arg, 1)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_filter, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter -> {
                findNavController()
                    .navigate(
                        PhotoVPFragmentDirections
                            .actionPhotoVPFragmentToFilterFragment(
                                currentRovert = arg,
                                selectedFilter = vm.selectedFilter
                            )
                    )
                true
            }
            else ->
                super.onOptionsItemSelected(item);
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            photoList.adapter = adapter
            photoList.itemAnimator = null
            photoList.layoutManager = gridLayoutManager
            photoList.addOnScrollListener(endlessScrollListener)
        }
        with(vm) {
            errorState.onEach {
                with(binding) {
                    photoList.visibility = it.getVisibilityErrorContainerNot()
                    loading.visibility = it.getLoadingVisibility()
                    errorMessage.visibility = it.errorMessageVisibility()
                    errorMessage.text = it.errorMessage
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
            photos.onEach {
                adapter.submitList(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
        parentFragment?.setFragmentResultListener(FilterFragment.RESULT) { _, bundle ->
            vm.selectedFilter = bundle.getParcelable(arg.name)
            endlessScrollListener.reset()
            vm.fetchRoversPhoto(arg, 1)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}

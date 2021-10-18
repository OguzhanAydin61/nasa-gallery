package com.avatarosko.nasagallery.ui.filter

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avatarosko.nasagallery.R
import com.avatarosko.nasagallery.common.model.CameraTypes
import com.avatarosko.nasagallery.common.model.FilterAdapterModel
import com.avatarosko.nasagallery.databinding.FilterFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilterFragment : Fragment(R.layout.filter_fragment) {

    companion object {
        const val RESULT = "Result.Selected"
    }

    private val binding by viewBinding(FilterFragmentBinding::bind)
    private val adapter by lazy { FilterAdapter() }
    private val arg by navArgs<FilterFragmentArgs>()
    private val layoutManager by lazy {
        LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            filter.adapter = adapter
            filter.layoutManager = layoutManager
            save.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    val selectedCamera = getSelectedCameraTypes()
                    setFragmentResult(RESULT, bundleOf(arg.currentRovert.name to selectedCamera))
                    findNavController().popBackStack()
                }

            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            val adapterModel = prepareAdapterModel()
            adapter.submitList(adapterModel)
        }
    }

    private suspend fun getSelectedCameraTypes() = withContext(Dispatchers.Default) {
        adapter.currentList.filter { it.isChecked }.map { it.cameraTypes }.firstOrNull()
    }

    private suspend fun prepareAdapterModel() =
        withContext(Dispatchers.Default) {
            CameraTypes.values()
                .filter {
                    it.roverTypes.contains(arg.currentRovert)
                }
                .map {
                    FilterAdapterModel(
                        it,
                        arg.selectedFilter == it
                    )
                }
        }

    override fun onDestroyView() {
        binding.filter.adapter = null
        binding.filter.layoutManager = null
        super.onDestroyView()
    }
}
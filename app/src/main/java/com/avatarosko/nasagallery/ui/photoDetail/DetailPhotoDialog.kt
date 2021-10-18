package com.avatarosko.nasagallery.ui.photoDetail

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import by.kirich1409.viewbindingdelegate.viewBinding
import com.avatarosko.nasagallery.R
import com.avatarosko.nasagallery.databinding.DialogPhotoDetailBinding
import com.avatarosko.nasagallery.di.GlideApp
import com.bumptech.glide.request.RequestOptions


class DetailPhotoDialog : DialogFragment(R.layout.dialog_photo_detail) {
    private val binding by viewBinding(DialogPhotoDetailBinding::bind)
    private val arg by navArgs<DetailPhotoDialogArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            GlideApp.with(this.root)
                .applyDefaultRequestOptions(
                    RequestOptions()
                        .override(400, 400)
                        .encodeQuality(80)
                )
                .load(arg.imgSrc)
                .placeholder(CircularProgressDrawable(requireContext()).apply {
                    setTint(ContextCompat.getColor(requireContext(), R.color.black))
                    strokeWidth = 5f
                    centerRadius = 30f
                    start()
                })
                .error(R.drawable.img_error).into(contentPhoto)
            pictureDate.text = arg.pictureDate
            pictureVehicleName.text = arg.vehicleName
            cameraName.text = arg.cameraName
            vehicleStatus.text = arg.vehicleName
            landsDate.text = arg.landingDate
            launchDate.text = arg.launchDate
        }
    }
}
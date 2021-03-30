package net.svishch.android.pictureoftheday.ui.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_mars.*
import kotlinx.android.synthetic.main.main_fragment.*
import net.svishch.android.outerspace.ui.adapter.MarsPhotosRVAdapter
import net.svishch.android.pictureoftheday.R
import net.svishch.android.pictureoftheday.apiNasa.mars.MarsPhotoData
import net.svishch.android.pictureoftheday.apiNasa.mars.MarsViewModel


class MarsFragment : Fragment() {

    private val viewModel: MarsViewModel by lazy {
        ViewModelProviders.of(this).get(MarsViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getMarsData()
                .observe(this@MarsFragment, { renderData(it) })

        rv_mars_photos.layoutManager = GridLayoutManager(context, 2);

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_mars, container, false)
    }

    private fun renderData(data: MarsPhotoData) {
        when (data) {
            is MarsPhotoData.Success -> {
                val serverResponseData = data.serverResponseData
                rv_mars_photos.adapter = serverResponseData.getPhotos()?.let { MarsPhotosRVAdapter(it) }
            }

        }
    }
}

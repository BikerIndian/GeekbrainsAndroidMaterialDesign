package net.svishch.android.pictureoftheday.ui.viewPager

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_main.*
import net.svishch.android.pictureoftheday.R
import net.svishch.android.pictureoftheday.apiNasa.apod.DayPhotoData
import net.svishch.android.pictureoftheday.ui.picture.AppTheme
import net.svishch.android.pictureoftheday.apiNasa.apod.DayViewModel

class DayFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val viewModel: DayViewModel by lazy {
        ViewModelProviders.of(this).get(DayViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData()
            .observe(this@DayFragment, Observer<DayPhotoData> { renderData(it) })

        // Анимация поля ввода wiki
        wiki_button.setOnClickListener {
            TransitionManager.beginDelayedTransition(main, Slide(Gravity.END).setDuration(600L))
            input_layout.visibility = if (!input_layout.isVisible) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }
        setBottomAppBar(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_navigation, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        activity?.let { AppTheme.selectThemeOnMenu(item.itemId, it) }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(photoData: DayPhotoData) {
        when (photoData) {
            is DayPhotoData.Success -> {
                val serverResponseData = photoData.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    //showError("Сообщение, что ссылка пустая")
                    toast("Link is empty")
                } else {
                    //showSuccess()
                    bottom_sheet_description_header.text = serverResponseData.title
                    bottom_sheet_description.text = serverResponseData.explanation
                    text_date.text = serverResponseData.date

                    image_view.load(url) {
                        lifecycle(this@DayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is DayPhotoData.Loading -> {
                //showLoading()
            }
            is DayPhotoData.Error -> {
                //showError(data.error.message)
                toast(photoData.error.message)
            }
        }
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as ApiActivity
        context.setSupportActionBar(view.findViewById(R.id.topAppBar))
        setHasOptionsMenu(true)
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    companion object {
        fun newInstance() = DayFragment()
        private var isMain = true
    }
}

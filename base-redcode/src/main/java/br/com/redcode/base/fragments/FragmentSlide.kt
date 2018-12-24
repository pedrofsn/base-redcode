package br.com.redcode.base.fragments

import android.content.Context
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import br.com.redcode.base.R

/**
 * Created by pedrofsn on 27/11/2017.
 */
abstract class FragmentSlide : BaseFragment() {

    override val layout = R.layout.fragment_slide
    private val frameLayout by lazy { view!!.findViewById<FrameLayout>(R.id.frameLayout) }
    private val imageView by lazy { view!!.findViewById<ImageView>(R.id.imageView) }
    private val url by lazy { arguments?.getString("url") ?: "" }
    private val colorBlack by lazy { ContextCompat.getColor(activity as Context, android.R.color.black) }

    override fun afterOnCreate() {
        super.afterOnCreate()
        frameLayout.setBackgroundColor(colorBlack)

        if (url.isNotBlank()) {
            load(imageView, url)
            imageView.setOnClickListener { click(url) }
        }
    }

    abstract fun load(imageView: ImageView?, url: String)
    open fun click(url: String) {

    }
}
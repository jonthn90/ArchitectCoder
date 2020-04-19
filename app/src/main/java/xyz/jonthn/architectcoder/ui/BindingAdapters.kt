package xyz.jonthn.architectcoder.ui

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import xyz.jonthn.architectcoder.ui.common.loadUrl

@BindingAdapter("urlCover")
fun ImageView.bindUrlCover(url: String?) {
    if (url != null) loadUrl("https://image.tmdb.org/t/p/w185$url")
}

@BindingAdapter("urlDetail")
fun ImageView.bindUrlDetail(url: String?) {
    if (url != null) loadUrl("https://image.tmdb.org/t/p/w780$url")
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = visible?.let {
        if (visible) View.VISIBLE else View.GONE
    } ?: View.GONE
}
package id.calocallo.loanapp.presentation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import id.calocallo.loanapp.databinding.FragmentLoanDocumentBinding
import id.calocallo.loanapp.domain.Documents

class LoanDocumentFragment : Fragment() {
    private lateinit var binding: FragmentLoanDocumentBinding

    companion object {
        const val IMAGE_URL = "https://andreascandle.github.io"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoanDocumentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        val documents = arguments?.getParcelable<Documents>("LOAN_DOCUMENTS")
        val urlDoc = documents?.url.orEmpty()

        with(binding) {
            wvLoanDocument.apply {
                webViewClient = WebViewClient()
                settings.setSupportZoom(true)
                settings.javaScriptEnabled = true

                val setting =
                    settings.apply {
                        builtInZoomControls = true
                    }

                loadUrl("$IMAGE_URL/$urlDoc")
                setInitialScale(1)
                setting.loadWithOverviewMode = true
                setting.useWideViewPort = true
                webViewClient =
                    object : WebViewClient() {
                        override fun onPageStarted(
                            view: WebView?,
                            url: String?,
                            favicon: Bitmap?,
                        ) {
                            super.onPageStarted(view, url, favicon)
                            progressBarLoanDocument.isVisible = true
                        }

                        override fun onPageFinished(
                            view: WebView?,
                            url: String?,
                        ) {
                            super.onPageFinished(view, url)
                            progressBarLoanDocument.isVisible = false
                        }
                    }
            }
        }
    }
}

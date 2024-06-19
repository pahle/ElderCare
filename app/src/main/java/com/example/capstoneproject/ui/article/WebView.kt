package com.example.capstoneproject.ui.article

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.databinding.ActivityWebViewBinding

class WebView : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var binding: ActivityWebViewBinding
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        supportActionBar?.hide()

        webView = binding.webview
        setContentView(binding.root)
        webView.webViewClient = WebViewClient()

        val dataURL = intent.getStringExtra("urlid")
        if (dataURL != null) {
            webView.loadUrl(dataURL)
        }
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String?) {
                binding.progressBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        }
        binding.text.text = dataURL.toString()
        binding.back.setOnClickListener {
            webView.goBack()
            finish()
        }

        binding.openChrome.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dataURL))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.android.chrome")
            try {
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                intent.setPackage(null)
                startActivity(Intent.createChooser(intent, "Select Browser"))
            }
        }
        binding.share.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, dataURL)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            finish()
        }
        super.onBackPressed()
    }
}
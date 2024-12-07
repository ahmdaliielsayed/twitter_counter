package com.example.halantwittercounter.twittercounter.presentation.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.halantwittercounter.R
import com.example.halantwittercounter.databinding.FragmentTwitterCounterBinding
import com.example.halantwittercounter.twittercounter.presentation.viewmodel.TwitterCounterViewModel
import com.example.halantwittercounter.utils.Constants.EMPTY
import com.example.halantwittercounter.utils.Constants.MAX_TWEET_LENGTH
import com.example.halantwittercounter.utils.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TwitterCounterFragment : Fragment() {

    private var _binding: FragmentTwitterCounterBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding

    private val viewModel: TwitterCounterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTwitterCounterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTweetTextChangeListener()
        setupObservers()
        handleButtonsClick()
    }

    private fun setTweetTextChangeListener() {
        binding?.editTextTweet?.doAfterTextChanged {
            viewModel.validateTweetLength(it.toString())
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect(viewModel.tweetLength, ::updateTweetLengthCards)
                collect(viewModel.isValidTweetLength, ::updatePostTweetButton)
                collect(viewModel.isLoading, ::modifyLoadingState)
                collect(viewModel.errorMessage, ::showError)
            }
        }
    }

    private fun updateTweetLengthCards(tweetLength: Int) {
        binding?.apply {
            textViewCharactersTyped.text = tweetLength.toString().plus("/$MAX_TWEET_LENGTH")
            textViewCharactersRemaining.text = (MAX_TWEET_LENGTH - tweetLength).toString()
        }
    }

    private fun updatePostTweetButton(isValidLength: Boolean) {
        binding?.btnPostTweet?.isEnabled = isValidLength
    }

    private fun handleButtonsClick() {
        binding?.apply {
            btnCopyText.setOnClickListener {
                copyTweet()
            }

            btnClearText.setOnClickListener {
                if (editTextTweet.text.toString().isEmpty()) {
                    Toast.makeText(context, getString(R.string.empty_string), Toast.LENGTH_SHORT).show()
                } else {
                    AlertDialog
                        .Builder(requireContext())
                        .setTitle(getString(R.string.confirmation_title))
                        .setMessage(getString(R.string.confirmation_body))
                        .setNegativeButton(getString(R.string.no)) { _, _ -> }
                        .setPositiveButton(getString(R.string.yes)) { _, _ ->
                            editTextTweet.setText(EMPTY)
                        }
                        .show()
                }
            }

            btnPostTweet.setOnClickListener {
                viewModel.postTweet(binding?.editTextTweet?.text.toString())
            }
        }
    }

    private fun copyTweet() {
        val tweetText = binding?.editTextTweet?.text.toString()
        if (tweetText.isNotBlank()) {
            val clipboard: ClipboardManager =
                context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(EMPTY, tweetText)
            clipboard.setPrimaryClip(clip)
        }
    }

    private fun modifyLoadingState(isLoading: Boolean) {
        binding?.progressBar?.isVisible = isLoading
    }

    private fun showError(errorMsg: String) {
        if (errorMsg.isNotBlank() && errorMsg.isNotEmpty()) {
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
        }
    }
}
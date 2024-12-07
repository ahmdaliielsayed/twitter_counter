package com.example.halantwittercounter.twittercounter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.halantwittercounter.data.remote.onError
import com.example.halantwittercounter.data.remote.onSuccess
import com.example.halantwittercounter.domain.usecase.PostTweetUseCase
import com.example.halantwittercounter.utils.Constants.ALCHEMICAL_SYMBOLS_RANGE
import com.example.halantwittercounter.utils.Constants.CHESS_SYMBOL_RANGE
import com.example.halantwittercounter.utils.Constants.CONTROL_CHARACTER_FIRST_RANGE
import com.example.halantwittercounter.utils.Constants.CONTROL_CHARACTER_SECOND_RANGE
import com.example.halantwittercounter.utils.Constants.DINGBATS_RANGE
import com.example.halantwittercounter.utils.Constants.EMOTICONS_RANGE
import com.example.halantwittercounter.utils.Constants.EMPTY
import com.example.halantwittercounter.utils.Constants.GEOMETRIC_SHAPES_RANGE
import com.example.halantwittercounter.utils.Constants.MAX_TWEET_LENGTH
import com.example.halantwittercounter.utils.Constants.MISC_SYMBOLS_AND_PICTOGRAPHS_RANGE
import com.example.halantwittercounter.utils.Constants.MISC_SYMBOLS_RANGE
import com.example.halantwittercounter.utils.Constants.MISC_TECHNICAL_RANGE
import com.example.halantwittercounter.utils.Constants.ONE
import com.example.halantwittercounter.utils.Constants.SUPPLEMENTAL_ARROWS_C_RANGE
import com.example.halantwittercounter.utils.Constants.SUPPLEMENTAL_SYMBOLS_AND_PICTOGRAPHS_RANGE
import com.example.halantwittercounter.utils.Constants.SYMBOLS_AND_PICTOGRAPHS_EXTENDED_A_RANGE
import com.example.halantwittercounter.utils.Constants.TRANSPORT_AND_MAP_SYMBOL_RANGE
import com.example.halantwittercounter.utils.Constants.TWO
import com.example.halantwittercounter.utils.Constants.URL_LENGTH
import com.example.halantwittercounter.utils.Constants.URL_REGEX
import com.example.halantwittercounter.utils.Constants.VARIATION_SELECTORS_RANGE
import com.example.halantwittercounter.utils.Constants.ZERO
import com.example.halantwittercounter.utils.Constants.ZERO_WIDTH_JOINER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class TwitterCounterViewModel(
    private val postTweetUseCase: PostTweetUseCase
) : ViewModel() {

    private var _tweetLength = MutableStateFlow(ZERO)
    val tweetLength = _tweetLength.asStateFlow()

    private var _isValidTweetLength = MutableStateFlow(false)
    val isValidTweetLength = _isValidTweetLength.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isPostTweetSuccess = MutableStateFlow(false)
    val isPostTweetSuccess = _isPostTweetSuccess.asStateFlow()

    private val _errorMessage = MutableStateFlow(EMPTY)
    val errorMessage = _errorMessage.asStateFlow()

    fun validateTweetLength(tweet: String) {
        val tweetLength = countTweetCharacters(tweet)
        _tweetLength.value = tweetLength

        checkTweetLengthValidation(tweetLength)
    }

    private fun countTweetCharacters(tweet: String): Int {
        var counter = ZERO
        var index = ZERO

        while (index < tweet.length) {
            val codePoint = tweet.codePointAt(index)
            val charCount = Character.charCount(codePoint)

            counter += when {
                isUrl(tweet.substring(index, index + charCount)) -> URL_LENGTH
                isEmoji(codePoint) -> TWO
                isControlCharacter(codePoint) -> ONE
                isZeroWidthJoinerOrVariationSelector(codePoint) -> ZERO
                else -> ONE
            }

            index += charCount
        }

        return counter
    }

    private fun isUrl(word: String): Boolean {
        val urlPattern = Pattern.compile(URL_REGEX)
        return urlPattern.matcher(word).matches()
    }

    private fun isEmoji(codePoint: Int): Boolean {
        return codePoint in ALCHEMICAL_SYMBOLS_RANGE
                || codePoint in CHESS_SYMBOL_RANGE
                || codePoint in DINGBATS_RANGE
                || codePoint in EMOTICONS_RANGE
                || codePoint in GEOMETRIC_SHAPES_RANGE
                || codePoint in MISC_SYMBOLS_AND_PICTOGRAPHS_RANGE
                || codePoint in MISC_SYMBOLS_RANGE
                || codePoint in MISC_TECHNICAL_RANGE
                || codePoint in SUPPLEMENTAL_ARROWS_C_RANGE
                || codePoint in SUPPLEMENTAL_SYMBOLS_AND_PICTOGRAPHS_RANGE
                || codePoint in SYMBOLS_AND_PICTOGRAPHS_EXTENDED_A_RANGE
                || codePoint in TRANSPORT_AND_MAP_SYMBOL_RANGE
    }

    private fun isControlCharacter(codePoint: Int): Boolean {
        return codePoint in CONTROL_CHARACTER_FIRST_RANGE || codePoint in CONTROL_CHARACTER_SECOND_RANGE
    }

    private fun isZeroWidthJoinerOrVariationSelector(codePoint: Int): Boolean {
        return codePoint == ZERO_WIDTH_JOINER || codePoint in VARIATION_SELECTORS_RANGE
    }

    private fun checkTweetLengthValidation(tweetLength: Int) {
        _isValidTweetLength.value = tweetLength in ONE..MAX_TWEET_LENGTH
    }

    fun postTweet(tweet: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            postTweetUseCase.postTweet(tweet)
                .onSuccess {
                    _isLoading.value = false
                    _isPostTweetSuccess.value = true
                    _errorMessage.value = EMPTY
                }
                .onError {
                    _isLoading.value = false
                    _isPostTweetSuccess.value = false
                    _errorMessage.value = it.name
                }
        }
    }
}
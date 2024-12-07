package com.example.halantwittercounter.utils

object Constants {

    lateinit var BASE_URL: String
    lateinit var API_KEY: String
    lateinit var API_SECRET: String
    lateinit var ACCESS_TOKEN: String
    lateinit var ACCESS_TOKEN_SECRET: String

    const val ZERO = 0
    const val ONE = 1
    const val TWO = 2

    const val HTTP_REQUEST_TIMEOUT = 408
    const val HTTP_TOO_MANY_REQUESTS = 429
    val HTTP_SERVER_ERROR_RANGE = 500..599

    const val MAX_TWEET_LENGTH = 280
    const val URL_LENGTH = 23

    const val EMPTY = ""
    const val URL_REGEX = "(https?://\\S+|www\\.\\S+)"

    val ALCHEMICAL_SYMBOLS_RANGE = 0x1F700..0x1F77F
    val CHESS_SYMBOL_RANGE = 0x1FA00..0x1FA6F
    val CONTROL_CHARACTER_FIRST_RANGE = 0x0000..0x001F
    val CONTROL_CHARACTER_SECOND_RANGE = 0x007F..0x009F
    val DINGBATS_RANGE = 0x2700..0x27BF
    val EMOTICONS_RANGE = 0x1F600..0x1F64F
    val GEOMETRIC_SHAPES_RANGE = 0x1F780..0x1F7FF
    val MISC_SYMBOLS_AND_PICTOGRAPHS_RANGE = 0x1F300..0x1F5FF
    val MISC_SYMBOLS_RANGE = 0x2600..0x26FF
    val MISC_TECHNICAL_RANGE = 0x2300..0x23FF
    val SUPPLEMENTAL_ARROWS_C_RANGE = 0x1F800..0x1F8FF
    val SUPPLEMENTAL_SYMBOLS_AND_PICTOGRAPHS_RANGE = 0x1F900..0x1F9FF
    val SYMBOLS_AND_PICTOGRAPHS_EXTENDED_A_RANGE = 0x1FA70..0x1FAFF
    val TRANSPORT_AND_MAP_SYMBOL_RANGE = 0x1F680..0x1F6FF
    val VARIATION_SELECTORS_RANGE = 0xFE00..0xFE0F
    const val ZERO_WIDTH_JOINER = 0x200D
}
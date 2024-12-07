package com.example.halantwittercounter.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.net.URLEncoder
import java.util.UUID

/**
 * An OkHttp interceptor for adding OAuth 1.0 authentication headers to API requests.
 *
 * @property apiKey The consumer key for the OAuth API.
 * @property apiSecret The consumer secret associated with the API key.
 * @property accessToken The access token representing the user's authentication.
 * @property accessTokenSecret The secret token for the user's access token.
 */
class OAuthInterceptor(
    private val apiKey: String,
    private val apiSecret: String,
    private val accessToken: String,
    private val accessTokenSecret: String
) : Interceptor {

    /**
     * Intercepts outgoing requests and adds an OAuth 1.0 header with necessary authorization details.
     *
     * @param chain The OkHttp request chain to proceed with.
     * @return A [Response] with the added OAuth authorization header.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val method = originalRequest.method
        val url = originalRequest.url.toString()

        // Generate the OAuth header for authorization
        val oauthHeader = generateOAuthHeader(
            method = method,
            url = url,
            apiKey = apiKey,
            apiSecret = apiSecret,
            accessToken = accessToken,
            accessTokenSecret = accessTokenSecret
        )

        // Create a new request with the OAuth header added
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", oauthHeader)
            .build()

        return chain.proceed(newRequest)
    }

    /**
     * Generates the OAuth 1.0 Authorization header required for authenticated requests.
     *
     * @param method The HTTP method (e.g., GET, POST) for the request.
     * @param url The URL to which the request is being sent.
     * @param apiKey The consumer key for OAuth.
     * @param apiSecret The consumer secret associated with the key.
     * @param accessToken The access token for the user.
     * @param accessTokenSecret The secret for the access token.
     * @return A formatted OAuth 1.0 Authorization header as a [String].
     */
    private fun generateOAuthHeader(
        method: String,
        url: String,
        apiKey: String,
        apiSecret: String,
        accessToken: String,
        accessTokenSecret: String
    ): String {
        val nonce = UUID.randomUUID().toString().replace("-", "")
        val timestamp = (System.currentTimeMillis() / 1000).toString()

        // OAuth parameters required by the 1.0 protocol
        val oauthParams = mutableMapOf(
            "oauth_consumer_key" to apiKey,
            "oauth_token" to accessToken,
            "oauth_signature_method" to "HMAC-SHA1",
            "oauth_timestamp" to timestamp,
            "oauth_nonce" to nonce,
            "oauth_version" to "1.0"
        )

        // Encode parameters for the signature base string
        val paramString = oauthParams.toSortedMap().map {
            "${URLEncoder.encode(it.key, "UTF-8")}=${URLEncoder.encode(it.value, "UTF-8")}"
        }.joinToString("&")

        val signatureBase =
            "$method&${URLEncoder.encode(url, "UTF-8")}&${URLEncoder.encode(paramString, "UTF-8")}"
        val signingKey = "${URLEncoder.encode(apiSecret, "UTF-8")}&${
            URLEncoder.encode(
                accessTokenSecret,
                "UTF-8"
            )
        }"

        // Generate the OAuth signature and add it to the parameters
        val signature = createHmacSHA1Signature(signatureBase, signingKey)
        oauthParams["oauth_signature"] = signature

        // Format the OAuth header by encoding each key-value pair
        return "OAuth " + oauthParams.map {
            "${URLEncoder.encode(it.key, "UTF-8")}=\"${URLEncoder.encode(it.value, "UTF-8")}\""
        }.joinToString(", ")
    }

    /**
     * Generates an HMAC-SHA1 signature for the given data and signing key.
     *
     * @param data The data to sign, typically the signature base string.
     * @param key The signing key, composed of the consumer secret and access token secret.
     * @return The HMAC-SHA1 signature, base64 encoded.
     */
    private fun createHmacSHA1Signature(data: String, key: String): String {
        val mac = javax.crypto.Mac.getInstance("HmacSHA1")
        val secretKeySpec = javax.crypto.spec.SecretKeySpec(key.toByteArray(), "HmacSHA1")
        mac.init(secretKeySpec)
        return android.util.Base64.encodeToString(
            mac.doFinal(data.toByteArray()),
            android.util.Base64.NO_WRAP
        )
    }
}

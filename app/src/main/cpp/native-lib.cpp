#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_halantwittercounter_BaseApplication_getBaseUrl(
        JNIEnv* env,
        jobject /* this */) {
    std::string baseUrl = "https://api.x.com/2/";
    return env->NewStringUTF(baseUrl.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_halantwittercounter_BaseApplication_getApiKey(
        JNIEnv* env,
        jobject /* this */) {
    std::string apiKey = "YOUR_GENERATED_API_KEY";
    return env->NewStringUTF(apiKey.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_halantwittercounter_BaseApplication_getApiSecret(
        JNIEnv* env,
        jobject /* this */) {
    std::string apiSecret = "YOUR_GENERATED_API_SECRET";
    return env->NewStringUTF(apiSecret.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_halantwittercounter_BaseApplication_getAccessToken(
        JNIEnv* env,
        jobject /* this */) {
    std::string accessToken = "YOUR_GENERATED_ACCESS_TOKEN";
    return env->NewStringUTF(accessToken.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_halantwittercounter_BaseApplication_getAccessTokenSecret(
        JNIEnv* env,
        jobject /* this */) {
    std::string accessTokenSecret = "YOUR_GENERATED_ACCESS_TOKEN_SECRET";
    return env->NewStringUTF(accessTokenSecret.c_str());
}
NDK=/usr/android/android-ndk-r14b
export NDK
# 我们的ANDROID_NDK和ANDROID_SDK 路径
ANDROID_NDK=/usr/android/android-ndk-r14b
export ANDROID_NDK
ANDROID_SDK=/usr/android/android-sdk-linux
export ANDROID_SDK 
# 加入到PATH路径
PATH=${PATH}:${NDK}:${ANDROID_NDK}:&{ANDROID_SDK}

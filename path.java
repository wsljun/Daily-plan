NDK=/usr/android/android-ndk-r14b
export NDK
# ���ǵ�ANDROID_NDK��ANDROID_SDK ·��
ANDROID_NDK=/usr/android/android-ndk-r14b
export ANDROID_NDK
ANDROID_SDK=/usr/android/android-sdk-linux
export ANDROID_SDK 
# ���뵽PATH·��
PATH=${PATH}:${NDK}:${ANDROID_NDK}:&{ANDROID_SDK}

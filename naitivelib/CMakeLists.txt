cmake_minimum_required(VERSION 3.20)
project(BTWinNative)

set(CMAKE_CXX_STANDARD 14)

add_library(BTWinNative SHARED net_morimori0317_bettertaskbar_jni_win_WindowsNative.cpp)

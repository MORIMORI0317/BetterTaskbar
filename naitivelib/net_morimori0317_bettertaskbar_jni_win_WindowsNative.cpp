#include "net_morimori0317_bettertaskbar_jni_win_WindowsNative.h"
#include <windef.h>
#include <shobjidl.h>

JNIEXPORT jboolean JNICALL Java_net_morimori0317_bettertaskbar_jni_win_WindowsNative_setTaskbarProgressValue
        (JNIEnv *, jclass, jlong id, jint comp, jint total) {
    HWND hWnd = reinterpret_cast<HWND>(id);
    ITaskbarList3 *taskbar;
    if (S_OK != CoCreateInstance(CLSID_TaskbarList, 0, CLSCTX_INPROC_SERVER, IID_ITaskbarList3, (void **) &taskbar)) {
        return false;
    }
    taskbar->HrInit();
    taskbar->SetProgressValue(hWnd, comp, total);
    taskbar->Release();
    return true;
}

JNIEXPORT jboolean JNICALL Java_net_morimori0317_bettertaskbar_jni_win_WindowsNative_setTaskbarProgressState
        (JNIEnv *, jclass, jlong id, jint flg) {
    HWND hWnd = reinterpret_cast<HWND>(id);
    ITaskbarList3 *taskbar;
    if (S_OK != CoCreateInstance(CLSID_TaskbarList, 0, CLSCTX_INPROC_SERVER, IID_ITaskbarList3, (void **) &taskbar)) {
        return false;
    }
    taskbar->HrInit();
    taskbar->SetProgressState(hWnd, TBPFLAG(flg));
    taskbar->Release();
    return true;
}
package net.morimori0317.bettertaskbar.taskbar.windows.jna;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.COMInvoker;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;

public class TaskbarList3 extends COMInvoker implements ITaskbarList3 {
    public TaskbarList3(Pointer pointer) {
        setPointer(pointer);
    }

    @Override
    public WinNT.HRESULT HrInit() {
        return (WinNT.HRESULT) this._invokeNativeObject(3, new Object[]{this.getPointer()}, WinNT.HRESULT.class);
    }

    @Override
    public WinNT.HRESULT SetProgressValue(WinDef.HWND hwnd, long completed, long total) {
        return (WinNT.HRESULT) this._invokeNativeObject(9, new Object[]{this.getPointer(), hwnd, completed, total}, WinNT.HRESULT.class);
    }

    @Override
    public WinNT.HRESULT SetProgressState(WinDef.HWND hwnd, TbpFlag tbpFlag) {
        return (WinNT.HRESULT) this._invokeNativeObject(10, new Object[]{this.getPointer(), hwnd, tbpFlag.getNum()}, WinNT.HRESULT.class);
    }

    @Override
    public WinNT.HRESULT Release() {
        return (WinNT.HRESULT) this._invokeNativeObject(2, new Object[]{this.getPointer()}, WinNT.HRESULT.class);
    }
}

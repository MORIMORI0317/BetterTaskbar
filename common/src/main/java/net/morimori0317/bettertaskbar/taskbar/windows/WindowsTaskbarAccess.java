package net.morimori0317.bettertaskbar.taskbar.windows;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.PointerByReference;
import net.minecraft.client.Minecraft;
import net.morimori0317.bettertaskbar.api.BetterTaskbarAPI;
import net.morimori0317.bettertaskbar.taskbar.ITaskbarAccess;
import org.lwjgl.glfw.GLFWNativeWin32;

import java.util.function.BiConsumer;

public class WindowsTaskbarAccess implements ITaskbarAccess {

    @Override
    public void setProgress(int comp, int total) {
        taskbarList3((itaskbarList3, hwnd) -> {
            var ret = itaskbarList3.SetProgressValue(hwnd, comp, total);
            if (W32Errors.FAILED(ret))
                throw new RuntimeException("ITaskbarList3 SetProgressValue failed");
        });
    }

    @Override
    public void setState(BetterTaskbarAPI.State state) {
        taskbarList3((itaskbarList3, hwnd) -> {
            var ret = itaskbarList3.SetProgressState(hwnd, ITaskbarList3.TbpFlag.getByState(state));
            if (W32Errors.FAILED(ret))
                throw new RuntimeException("ITaskbarList3 SetProgressState failed");
        });
    }

    private void taskbarList3(BiConsumer<ITaskbarList3, WinDef.HWND> consumer) {
        var window = Minecraft.getInstance().getWindow();
        if (window == null) return;
        long id = GLFWNativeWin32.glfwGetWin32Window(window.getWindow());
        var hwnd = new WinDef.HWND(new Pointer(id));
        var clsid = new Guid.CLSID("56FDF344-FD6D-11d0-958A-006097C9A090");
        var ref = new PointerByReference();
        var hr = Ole32.INSTANCE.CoCreateInstance(clsid, null, WTypes.CLSCTX_SERVER, TaskbarList3.IID_ITaskbarList3, ref);

        if (W32Errors.FAILED(hr))
            throw new RuntimeException("Failed to create instance of ITaskbarList3");

        var tbl3 = new TaskbarList3(ref.getValue());
        var hret = tbl3.HrInit();
        if (W32Errors.FAILED(hret))
            throw new RuntimeException("ITaskbarList3 HrInit failed");

        consumer.accept(tbl3, hwnd);

        var rret = tbl3.Release();
        if (W32Errors.FAILED(rret))
            throw new RuntimeException("ITaskbarList3 release failed");
    }

}

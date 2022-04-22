package net.morimori0317.bettertaskbar.taskbar.windows;

import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import net.morimori0317.bettertaskbar.api.BetterTaskbarAPI;

//https://code-examples.net/ja/q/2110fd
public interface ITaskbarList3 {
    Guid.IID IID_ITaskbarList3 = new Guid.IID("ea1afb91-9e28-4b86-90e9-9e9f8a5eefaf");

    WinNT.HRESULT HrInit();

    WinNT.HRESULT SetProgressValue(WinDef.HWND hwnd, long completed, long total);

    WinNT.HRESULT SetProgressState(WinDef.HWND hwnd, TbpFlag tbpFlag);

    WinNT.HRESULT Release();

    public enum TbpFlag {
        TBPF_NOPROGRESS(0x0),
        TBPF_INDETERMINATE(0x1),
        TBPF_NORMAL(0x2),
        TBPF_ERROR(0x4),
        TBPF_PAUSED(0x8);
        private final int num;

        TbpFlag(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }

        public static TbpFlag getByState(BetterTaskbarAPI.State state) {
            return switch (state) {
                case WAIT -> TBPF_INDETERMINATE;
                case ERROR -> TBPF_ERROR;
                case NO_PROGRESS -> TBPF_NOPROGRESS;
                case NORMAL -> TBPF_NORMAL;
                case PAUSE -> TBPF_PAUSED;
            };
        }
    }
}

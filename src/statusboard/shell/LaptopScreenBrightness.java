package statusboard.shell;

import java.io.IOException;

public class LaptopScreenBrightness {

    private static String OS = System.getProperty("os.name").toLowerCase();

    public LaptopScreenBrightness() {
    }

    public void setBrightness(int percent) {
        int val = (((percent + 5) / 10) * 10);
        float linuxVal = ((float) val / 100);
        if (OS.contains("linux")) {
            linux(Float.toString(linuxVal));
        } else if (OS.contains("windows")) {
            windows(val);
        }
    }

    private void linux(String arg) {
        //protect against any illegitimate values
        if (arg.matches("0.1|0.2|0.3|0.4|0.5|0.6|0.7|0.8|0.9|1.0")) {
            try {
                String[] command = {"./Linux_Brightness.sh", arg};
                Process process = Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }

    private void windows(int brightness) {
        //protect against any illegitimate values
        if (brightness > 10 && brightness <= 100) {
            try {
                String s = String.format("$brightness = %d;", brightness)
                        + "$delay = 0;"
                        + "$myMonitor = Get-WmiObject -Namespace root\\wmi -Class WmiMonitorBrightnessMethods;"
                        + "$myMonitor.wmisetbrightness($delay, $brightness)";
                String command = "powershell.exe  " + s;
                Process powerShellProcess = Runtime.getRuntime().exec(command);
                powerShellProcess.getOutputStream().close();
            } catch (IOException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }

}

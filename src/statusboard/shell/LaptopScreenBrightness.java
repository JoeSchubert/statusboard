package statusboard.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
                String line;
                String[] command = new String[]{"/bin/bash", "-c", "xrandr -q | grep \" connected\" | cut -f 1 -d \" \""};
                Process process = Runtime.getRuntime().exec(command);
                try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    while ((line = in.readLine()) != null) {
                        String nextCommand = "xrandr --output " + line + " --brightness " + arg;
                        Runtime.getRuntime().exec(nextCommand);
                    }
                }
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

package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RemoteGridUtils {
    public String os="";
    public RemoteGridUtils(){
       this.os=System.getProperty("os.name");
    }

    public static void main(String[] args) throws IOException {
       RemoteGridUtils remoteGridUtils= new RemoteGridUtils();
        remoteGridUtils.os=System.getProperty("os.name");
       //remoteGridUtils.startGrid();
        remoteGridUtils.stopGrid();
    }

    public  void startGrid()  {
        try {
            if (os.toLowerCase().contains("mac")) {
                System.out.println("Starting Grid....");
                String dockerComposeFileName=System.getProperty("user.dir")+"/RemoteGridFiles/docker-compose.yaml";
                Process p = Runtime.getRuntime().exec("docker-compose -f "+dockerComposeFileName+" up -d");
                p.waitFor();
                Thread.sleep(30000);
                System.out.println("Grid started....");
            }
            if (os.toLowerCase().contains("windows")) {
                Runtime.getRuntime().exec("cmd /c start RemoteGridFiles/startGrid.bat");
                Thread.sleep(15000);


            }


    } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }}

        public  void stopGrid(){
        try {
            if (os.toLowerCase().contains("mac")) {
                System.out.println("Stopping  Grid....");
                String dockerComposeFileName=System.getProperty("user.dir")+"/RemoteGridFiles/docker-compose.yaml";
                Process p = Runtime.getRuntime().exec("docker-compose -f "+dockerComposeFileName+" down");
                p.waitFor();
                System.out.println("Grid stopped...");
            }
            if (os.toLowerCase().contains("windows")) {
                Process p =Runtime.getRuntime().exec("cmd /c start RemoteGridFiles/stopGrid.bat");
                p.waitFor();
                Thread.sleep(5000);
                Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

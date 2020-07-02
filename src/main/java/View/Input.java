package View;

import Model.Account.Manager;


import java.util.Scanner;

public class Input {

    private static boolean isStarting=true;
    private static boolean show=true;

    public static void setIsStarting(boolean isStarting) {
        Input.isStarting = isStarting;
    }

    public void start(Output output){
        Scanner scanner= MenuManage.getScanner();
        while (isStarting){
            if(show) {
                MenuManage.getLatestMenu().show();
            }
            String command=scanner.nextLine();
            if (command.matches("\\s^help")){
                show=false;
            }else
                show=true;
            output.manageCommand(command);
        }

    }
}

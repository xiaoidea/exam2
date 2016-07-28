package com.dhy.study;

/**
 * Created by Dai on 2016/7/28.
 */
public class Main {
    public static void main(String[] args) {
        String command = "cat sql.txt| grep CREATE | wc -l";
        //command = "wc -l sql.txt";
        //command = "grep CREATE sql.txt";
        command = "cat sql.txt| wc -l | grep CREATE";
        CommandExecutor.executeCommand(command);
    }
}

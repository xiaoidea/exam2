package com.dhy.study;

import com.google.common.base.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Dai on 2016/7/28.
 */
public class CommandExecutor {
    private static final Logger logger = LoggerFactory.getLogger(CommandExecutor.class);

    public static void executeCommand(String command) {
        List<String> commandsList = Splitter.on("|").trimResults().omitEmptyStrings().splitToList(command);

        Object currentResult = null;
        boolean firstCommand = true;
        for (String str : commandsList) {
            if (firstCommand) {
                Command currentCommand = CommandManager.createCommand(str);
                currentResult = currentCommand.execute();
                firstCommand = false;
            }
            else {
                Command currentCommand = CommandManager.createCommand(str, currentResult);
                currentResult = currentCommand.execute();
            }
        }

        //System.out.println(currentResult);
        logger.info(currentResult.toString());
    }
}

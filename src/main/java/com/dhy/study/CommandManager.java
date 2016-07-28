package com.dhy.study;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;

import java.util.List;

/**
 * Created by hengyudai on 16-7-28.
 */
public class CommandManager {
    public static Command createCommand(String commandString) {
        Preconditions.checkNotNull(commandString);
        List<String> commandSplit = Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings().trimResults().splitToList(commandString);

        String label = commandSplit.get(0);
        if (label.equals("cat")) {
            return new CatCommand(commandSplit);
        }
        else if (label.equals("grep")) {
            return new GrepCommand(commandSplit);
        }
        else if (label.equals("wc")) {
            return new WcCommand(commandSplit);
        }
        else {
            throw new IllegalStateException("command is illegal!");
        }
    }

    public static Command createCommand(String commandString, Object source) {
        Preconditions.checkNotNull(commandString);
        Preconditions.checkNotNull(source);
        List<String> commandSplit = Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings().trimResults().splitToList(commandString);

        String label = commandSplit.get(0);
        if (label.equals("grep")) {
            if (source instanceof List)
                return new GrepCommand(commandSplit, (List<String>)source);
            //else
                //throw IllegalStateException("");
        }
        else if (label.equals("wc")) {
            return new WcCommand(commandSplit, source);
        }
        else {
            throw new IllegalStateException("command is illegal!");
        }
    }
}

package com.dhy.study;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by hengyudai on 16-7-28.
 */
public class CommandManager {
    public static Command createCommand(String commandString) {
        Preconditions.checkNotNull(commandString);
        List<String> commandSplit = Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings().trimResults().splitToList(commandString);

        String label = commandSplit.get(0);

        String clazzName = "com.dhy.study."+label.substring(0,1).toUpperCase() + label.substring(1) + "Command";
        //String clazzName = CommandManager.class.getResource("/com/dhy/study/").getPath()+label.substring(0,1).toUpperCase() + label.substring(1) + "Command";
        try {
            Class clazz = Class.forName(clazzName);
            Constructor constructor = clazz.getConstructor(List.class);
            return (Command) constructor.newInstance(commandSplit);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Command createCommand(String commandString, Object source) {
        Preconditions.checkNotNull(commandString);
        Preconditions.checkNotNull(source);
        List<String> commandSplit = Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings().trimResults().splitToList(commandString);

        String label = commandSplit.get(0);
        String clazzName = "com.dhy.study."+label.substring(0,1).toUpperCase() + label.substring(1) + "Command";
        //String clazzName = label.substring(0,1).toUpperCase() + label.substring(1) + "Command";

        try {
            Class clazz = Class.forName(clazzName);
            Constructor constructor = clazz.getConstructor(List.class, List.class);
            return (Command) constructor.newInstance(commandSplit, source);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
        /*if (label.equals("grep")) {
            if (source instanceof List)
                return new GrepCommand(commandSplit, (List<String>)source);
            else if (source instanceof Integer)
                return new GrepCommand(commandSplit, Lists.newArrayList(String.valueOf(source)));
            else
                throw new IllegalStateException("error!");
        }
        else if (label.equals("wc")) {
            if (source instanceof List)
                return new WcCommand(commandSplit, (List<String>)source);
            else if (source instanceof Integer)
                return new WcCommand(commandSplit, Lists.newArrayList(String.valueOf(source)));
            else
                throw new IllegalStateException("error!");
            //return new WcCommand(commandSplit, source);
        }
        else {
            throw new IllegalStateException("command is illegal!");
        }*/
    }
}

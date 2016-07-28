package com.dhy.study;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by hengyudai on 16-7-28.
 */
public class WcCommand extends Command {
    private static final Logger logger = LoggerFactory.getLogger(WcCommand.class);

    private List<String> sourceList;

    public WcCommand(List<String> commandSplit) {
        this.commandSplit = commandSplit;
        this.sourceList = Lists.newArrayList();
        Preconditions.checkState(isValidCommand(commandSplit), "invalid command!");
    }

    public WcCommand(List<String> commandSplit, List<String> sourceList) {
        this.commandSplit = commandSplit;
        this.sourceList = sourceList;
        Preconditions.checkState(isValidCommand(commandSplit), "invalid command!");
    }

    protected boolean isValidCommand(List commandSplit) {
        if (sourceList.isEmpty()) {
            if (commandSplit.size() != 3) {
                return false;
            }
        }
        else { //从管道读数据
            if (commandSplit.size() != 2)
                return false;
        }
        return true;
    }

    public Integer execute() {
        if (sourceList.isEmpty()) {
            String sourceFile = commandSplit.get(2);
            return execute(sourceFile);
        }
        else {
            return execute(sourceList);
        }
    }

    private Integer execute(List<String> sourceList) {
        return sourceList.size();
    }

    private Integer execute(String sourceFile) {
        try {
            List<String> fileList = Files.readLines(new File(sourceFile), Charsets.UTF_8);
            return fileList.size();
        } catch (IOException e) {
            logger.error("read file error", e);
        }
        return null;
    }


}

package com.dhy.study;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
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
public class GrepCommand extends Command {
    private static final Logger logger = LoggerFactory.getLogger(GrepCommand.class);

    private List<String> sourceList;

    public GrepCommand(List<String> commandSplit) {
        this.commandSplit = commandSplit;
        this.sourceList = Lists.newArrayList();
        Preconditions.checkState(isValidCommand(commandSplit), "invalid command grammar!");
    }

    public GrepCommand(List<String> commandSplit, List<String> sourceList) {
        this.commandSplit = commandSplit;
        this.sourceList = sourceList;
        Preconditions.checkState(isValidCommand(commandSplit), "invalid command grammar!");
    }

    protected boolean isValidCommand(List commandSplit) {
        if (sourceList.isEmpty()) {
            if (commandSplit.size() != 3)
                return false;
        }
        else {
            if (commandSplit.size() != 2)
                return false;
        }
        return true;
    }

    public List<String> execute() {
        if (sourceList.isEmpty()) {
            String sourceFile = commandSplit.get(2);
            return execute(sourceFile);
        }
        else {
            return execute(sourceList);
        }
    }

    private List<String> execute(List<String> sourceList) {
        List<String> list = Lists.newArrayList();
        String target = commandSplit.get(1);
        for (String line : sourceList) {
            if (line.contains(target)) {
                list.add(line);
            }
        }
        return list;
    }

    private List<String> execute(String sourceFile) {
        try {
            List<String> fileList = Files.readLines(new File(sourceFile), Charsets.UTF_8);
            return execute(fileList);
        } catch (IOException e) {
            logger.error("read file error", e);
        }
        return null;
    }
}

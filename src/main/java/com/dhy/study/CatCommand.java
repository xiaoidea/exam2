package com.dhy.study;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by hengyudai on 16-7-28.
 */
public class CatCommand extends Command {
    private static final Logger logger = LoggerFactory.getLogger(CatCommand.class);
    private String sourceFile;

    public CatCommand(List<String> commandSplit) {
        Preconditions.checkState(isValidCommand(commandSplit), "invalid command!");
        this.commandSplit = commandSplit;
        this.sourceFile = commandSplit.get(1);
    }

    protected boolean isValidCommand(List commandSplit) {
        if (!commandSplit.get(0).equals("cat")) {
            return false;
        }
        if (commandSplit.size() != 2) {
            return false;
        }
        return true;
    }

    public List<String> execute() {
        try {
            List<String> list = Files.readLines(new File(sourceFile), Charsets.UTF_8);
            return list;
        } catch (IOException e) {
            logger.error("read file error", e);
        }
        return null;
    }
}

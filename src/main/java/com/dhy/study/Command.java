package com.dhy.study;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;

import java.util.List;

/**
 * Created by hengyudai on 16-7-28.
 */
public abstract class Command {

    protected List<String> commandSplit;

    protected abstract boolean isValidCommand(List commandSplit);

    public abstract <E> E execute();

    @Override
    public String toString() {
        return "";
    }
}

package com.github.fullduplex;

import java.util.NoSuchElementException;

/**
 * Author: Rob Martin
 * Created: 2/21/13 9:52 PM
 */
public class CharacterStream {

    private final String string;
    private int currIndex;
    private final int length;

    public CharacterStream(String s) {
        this.string = s;
        this.currIndex = 0;
        this.length = s.length();
    }

    public boolean hasNext() {
        return currIndex <= length - 1;
    }

    public char next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return string.charAt(currIndex++);
    }

    public int currIndex() {
        return currIndex;
    }

}

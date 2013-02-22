package com.github.fullduplex;

import org.junit.Test;

import java.util.NoSuchElementException;

/**
 * Author: Rob Martin
 * Created: 2/21/13 10:08 PM
 */
public class CharacterStreamTest {

    @Test()
    public void testNextToEnd() {
        CharacterStream cs = new CharacterStream("123");
        cs.next(); // 1
        cs.next(); // 2
        cs.next(); // 3
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextPastEnd() {
        CharacterStream cs = new CharacterStream("12");
        cs.next(); // 1
        cs.next(); // 2
        cs.next(); // boom!
    }

}

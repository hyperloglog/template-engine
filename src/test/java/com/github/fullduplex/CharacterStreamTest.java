package com.github.fullduplex;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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

    @Test(expected = NoSuchElementException.class)
    public void testPeek() {
        CharacterStream cs = new CharacterStream("abc");
        assertEquals('a', cs.peek());
        assertEquals('a', cs.next());
        assertEquals('b', cs.peek());
        assertEquals('b', cs.next());
        assertEquals('c', cs.next());
        cs.peek();
    }

    @Test
    public void testSimpleUsage() {
        int count = 0;
        String s = "12345";
        CharacterStream cs = new CharacterStream(s);
        while (cs.hasNext()) {
            cs.next();
            count++;
        }
        assertEquals(s.length(), count);
        assertFalse(cs.hasNext());
    }

}

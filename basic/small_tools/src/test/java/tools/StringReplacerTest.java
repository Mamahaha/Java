package tools;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringReplacerTest {

    @Test
    public void testReplace1() {
        String oldContent = "aaaa===1111==3333=ccc";
        String newString = "2222";
        String regx = "\\d+";
        String newContent = "aaaa===2222==3333=ccc";
        assertEquals(StringReplacer.replace(oldContent, newString, regx,
                StringReplacer.ReplaceType.FIRST), newContent);
    }

    @Test
    public void testReplace2() {
        String oldContent = "aaaa===1111==3333=ccc";
        String newString = "2222";
        String regx = "\\d+";
        String newContent = "aaaa===2222==2222=ccc";
        assertEquals(StringReplacer.replace(oldContent, newString, regx,
                StringReplacer.ReplaceType.ALL), newContent);
    }
}

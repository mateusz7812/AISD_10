package tests;

import com.company.Comparator.StringComparator;
import org.junit.Assert;
import org.junit.Test;

public class TestStringComparator {
    @Test
    public void Test1() {
        var comparator = new StringComparator();
        Assert.assertEquals(0, comparator.Compare("a", "a"));
    }

    @Test
    public void Test2() {
        var comparator = new StringComparator();
        Assert.assertEquals(-1, comparator.Compare("a", "z"));
    }

    @Test
    public void Test3() {
        var comparator = new StringComparator();
        Assert.assertEquals(1, comparator.Compare("b", "a"));
    }

    @Test
    public void Test4() {
        var comparator = new StringComparator();
        Assert.assertEquals(-1, comparator.Compare("aab", "aac"));
    }

    @Test
    public void Test5() {
        var comparator = new StringComparator();
        Assert.assertEquals(1, comparator.Compare("aab", "aa"));
    }

    @Test
    public void Test6() {
        var comparator = new StringComparator();
        Assert.assertEquals(1, comparator.Compare("aabasfefc", "--#55#"));
    }
}

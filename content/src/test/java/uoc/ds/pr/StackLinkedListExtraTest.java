package uoc.ds.pr.util;

import edu.uoc.ds.traversal.Iterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StackLinkedListExtraTest {

    private StackLinkedList<Integer> stack;

    @Before
    public void setUp() {
        stack = new StackLinkedList<>();
    }

    @Test
    public void newStackShouldBeEmptyTest() {
        Assert.assertTrue(stack.isEmpty());
        Assert.assertEquals(0, stack.size());
    }

    @Test
    public void pushAndPopShouldLeaveStackEmptyTest() {
        stack.push(10);
        stack.push(20);

        Assert.assertEquals(2, stack.size());
        Assert.assertEquals(Integer.valueOf(20), stack.pop());
        Assert.assertEquals(Integer.valueOf(10), stack.pop());

        Assert.assertTrue(stack.isEmpty());
        Assert.assertEquals(0, stack.size());
    }

    @Test
    public void valuesIteratorShouldTraverseFromTopToBottomTest() {
        stack.push(1);
        stack.push(2);
        stack.push(3);

        Iterator<Integer> it = stack.values();

        Assert.assertTrue(it.hasNext());
        Assert.assertEquals(Integer.valueOf(3), it.next());

        Assert.assertTrue(it.hasNext());
        Assert.assertEquals(Integer.valueOf(2), it.next());

        Assert.assertTrue(it.hasNext());
        Assert.assertEquals(Integer.valueOf(1), it.next());

        Assert.assertFalse(it.hasNext());
    }
}
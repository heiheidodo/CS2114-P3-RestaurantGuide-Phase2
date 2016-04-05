package cs2114.restaurant;

import java.util.Iterator;
import java.util.NoSuchElementException;
import student.TestCase;

// -------------------------------------------------------------------------
/**
 * This is a test class of CicularLinkedList class.
 *
 * @author Sheng Zhou
 * @version Apr 3, 2013
 */
public class CircularLinkedListTest
    extends TestCase
{
    // ~ Instance/static variables .............................................

    private CircularLinkedList<String> testStr;


    // ~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Create a new test class.
     */
    public CircularLinkedListTest()
    {
        // The constructor is usually empty in unit tests, since it runs
        // once for the whole class, not once for each test method.
        // Per-test initialization should be placed in setUp() instead.
    }


    // ~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Set up the initial.
     */
    public void setUp()
    {
        testStr = new CircularLinkedList<String>();
    }


    // ----------------------------------------------------------
    /**
     * The method is to test next().
     */
    public void testNext()
    {
        testStr.next();
        testStr.add("one");
        testStr.next();
        assertEquals(testStr.getCurrent(), "one");
        testStr.add("two");
        testStr.next();
        assertEquals(testStr.getCurrent(), "one");
        testStr.add("three");
        testStr.next();
        testStr.add("th");
        assertEquals(testStr.getCurrent(), "th");
        testStr.next();
        testStr.next();
        assertEquals(testStr.getCurrent(), "two");

    }


    // ----------------------------------------------------------
    /**
     * The method is to test previous().
     */
    public void testPrevious()
    {
        testStr.previous();
        testStr.add("one");
        testStr.add("two");
        testStr.add("three");
        testStr.previous();
        testStr.add("thr");
        testStr.previous();
        assertEquals(testStr.getCurrent(), "two");
    }


    // ----------------------------------------------------------
    /**
     * The method is to test add().
     */
    public void testAdd()
    {
        assertEquals(testStr.size(), 0);
        testStr.add("one");
        testStr.add("two");
        testStr.add("three");
        assertEquals(testStr.size(), 3);
        assertEquals(testStr.getCurrent(), "three");
        testStr.add(null);
        assertEquals(testStr.size(), 4);
        assertNull(testStr.getCurrent());
    }


    // ----------------------------------------------------------
    /**
     * The method is to test getCurrent().
     */
    public void testGetCurrent()
    {
        Exception thrown = null;
        try
        {
            testStr.getCurrent();
        }
        catch (Exception e)
        {
            thrown = e;
        }

        assertTrue(thrown instanceof NoSuchElementException);
        assertEquals("There is no element in the list.", thrown.getMessage());

        testStr.add("one");
        testStr.add("two");
        testStr.add("three");
        assertEquals(testStr.getCurrent(), "three");
    }


    // ----------------------------------------------------------
    /**
     * The method is to test clear().
     */
    public void testClear()
    {
        testStr.add("one");
        testStr.add("two");
        testStr.add("three");
        testStr.clear();
        assertEquals(0, testStr.size());
    }

    // ----------------------------------------------------------
    /**
     * The method is to test size().
     */
    public void testSize()
    {
        assertEquals(0, testStr.size());
        testStr.add("one");
        testStr.add("two");
        testStr.add("three");
        assertEquals(3, testStr.size());
    }
    // ----------------------------------------------------------
    /**
     * The method is to test toString().
     */
    public void testToString()
    {
        assertEquals("[]", testStr.toString());
        testStr.add("one");
        assertEquals("[one]", testStr.toString());
        testStr.add("two");
        testStr.add("three");
        assertEquals("[three, two, one]", testStr.toString());
        testStr.next();
        assertEquals("[two, one, three]", testStr.toString());
        testStr.next();
        assertEquals("[one, three, two]", testStr.toString());
    }


    // ----------------------------------------------------------
    /**
     * The method is to test removeCurrent().
     */
    public void testRemoveCurrent()
    {
        Exception thrown = null;
        try
        {
            testStr.removeCurrent();
        }
        catch (Exception e)
        {
            thrown = e;
        }

        assertTrue(thrown instanceof NoSuchElementException);
        assertEquals("There is no element in the list.", thrown.getMessage());

        testStr.add("one");
        testStr.add("two");
        testStr.add("three");
        assertEquals(testStr.removeCurrent(), "three");
    }


    // ----------------------------------------------------------
    /**
     * The method is to test iterator class's hasNext().
     */
    public void testHasNext()
    {
        assertEquals(0, testStr.size());
        Iterator<String> testI = testStr.iterator();

        assertFalse(testI.hasNext());

        testStr.add("one");
        testI = testStr.iterator();
        assertTrue(testI.hasNext());

        testI.next();
        assertFalse(testI.hasNext());
    }


    // ----------------------------------------------------------
    /**
     * The method is to test iterator class's next().
     */
    public void testINext()
    {
        assertNull(testStr.iterator().next());

        testStr.add("one");
        Iterator<String> test = testStr.iterator();
        assertTrue(test.hasNext());
        assertEquals(testStr.getCurrent(), test.next());
        testStr.add("two");
        testStr.add("three");
        assertEquals("one", test.next());

        Exception thrown = null;
        try
        {
            test.next();
        }
        catch (Exception e)
        {
            thrown = e;
        }

        assertTrue(thrown instanceof NoSuchElementException);
        assertEquals("No next element.", thrown.getMessage());
    }


    // ----------------------------------------------------------
    /**
     * The method is to test iterator class's remove() method.
     */
    public void testRemove()
    {
        Iterator<String> test = testStr.iterator();

        Exception thrown = null;
        try
        {
            test.remove();
        }
        catch (Exception e)
        {
            thrown = e;
        }

        assertTrue(thrown instanceof UnsupportedOperationException);
        assertEquals("Don't support.", thrown.getMessage());
    }


    /**
     * The method is to test with the for loop
     */
    public void testfor()
    {
        testStr.add("one");
        testStr.add("two");

        for (int i = 0; i < testStr.size(); i++)
        {
            if (i == 0)
            {
                assertEquals("two", testStr.getCurrent());
                testStr.next();
            }
            else
            {
                assertEquals("one", testStr.getCurrent());
            }
        }
    }

}

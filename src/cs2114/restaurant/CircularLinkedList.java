package cs2114.restaurant;

import java.util.Iterator;
import java.util.NoSuchElementException;

// -------------------------------------------------------------------------
/**
 * The class is to create a circular linked list.
 *
 * @param <E>
 *            E
 * @author Sheng Zhou
 * @version Apr 1, 2013
 */
public class CircularLinkedList<E>
    implements CircularList<E>
{
    private Node<E> current;
    private int     size;


    // ----------------------------------------------------------
    /**
     * Construct the List.
     */
    public CircularLinkedList()
    {
        size = 0;
        current = null;
    }


    // ----------------------------------------------------------
    /**
     * The method is to update the current location to next node.
     */
    public void next()
    {
        if (size > 0)
        {
            current = current.next();
        }
    }


    /**
     * The method is to update the current location to previous node.
     */
    public void previous()
    {
        if (size > 0)
        {
            current = current.previous();
        }
    }


    /**
     * The method is to get the current node.
     *
     * @return the current node
     */
    public E getCurrent()
    {
        if (size == 0)
        {
            throw new
            NoSuchElementException("There is no element in the list.");
        }
        else
        {
            return current.data();
        }
    }


    /**
     * The method is to return the size of the list.
     *
     * @return the size of the list
     */
    public int size()
    {
        return size;
    }


    /**
     * The method is to add a new item in the list.
     *
     * @param newItem
     *            the new one should be added into the list
     */
    public void add(E newItem)
    {
        Node<E> temp = new Node<E>(newItem);
        if (size == 0)
        {
            current = temp;
            temp.join(current);
        }
        else
        {
            Node<E> data = current.previous();
            data.split();
            data.join(temp);
            temp.join(current);
            current = temp;
        }

        size++;
    }


    /**
     * The method is to return the removed current node.
     *
     * @return the node was eliminated
     */
    public E removeCurrent()
    {
        if (size == 0)
        {
            throw new
            NoSuchElementException("There is no element in the list.");
        }
        else
        {
            E removeLink = current.data();
            Node<E> previousLink = current.previous();
            previousLink.split();
            current = current.split();
            previousLink.join(current);
            size--;
            return removeLink;
        }
    }


    /**
     * The method is to clear all nodes in the list.
     */
    public void clear()
    {
        current = null;
        size = 0;
    }


    /**
     * The method is to print the msg.
     *
     * @return the output
     */
    public String toString()
    {
        String result = "";
        Node<E> temp = current;
        if (size == 0)
        {
            return "[]";
        }
        else
        {
            for (int i = 0; i < size - 1; i++)
            {
                result += temp.data() + ", ";
                temp = temp.next();
            }
            result += temp.data();
        }
        return "[" + result + "]";

    }


    /**
     * The method is to return the iterator.
     *
     * @return the iterator.
     */
    @Override
    public Iterator<E> iterator()
    {
        return new CircularLinkedIterator();
    }


    /**
     * // ----------------------------------------------------------------------
     * The class is to create an iterator to attach all the elements in the
     * list.
     *
     * @author Sheng Zhou
     * @version Apr 3, 2013
     */
    private class CircularLinkedIterator
        implements Iterator<E>
    {
        private Node<E> iCurrent;
        private boolean aCircle;


        /**
         * The method is to initialize the StackIterator.
         */
        public CircularLinkedIterator()
        {
            iCurrent = current;
            aCircle = true;
        }


        /**
         * The method is to check if there is a next element in the stack.
         *
         * @return true if has next, otherwise false;
         */
        public boolean hasNext()
        {
            if (iCurrent != null)
            {
                if (aCircle)
                {
                    return true;
                }

                return iCurrent != current;
            }

            return false;
        }


        /**
         * The method is to return the next Item object in the stack.
         *
         * @return the element
         */
        public E next()
        {
            if (hasNext())
            {
                E result = iCurrent.data();
                iCurrent = iCurrent.next();
                aCircle = false;
                return result;
            }

            if (size > 0)
            {
                throw new NoSuchElementException("No next element.");
            }

            return null;
        }


        /**
         * The method is to remove the element and shrink the size of the stack
         */
        public void remove()
        {
            throw new UnsupportedOperationException("Don't support.");
        }

    }
}

package com.google.gson.internal;

import java.lang.reflect.InvocationTargetException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.gson.internal.LinkedHashTreeMap.Node;
import junit.framework.TestCase;

/** Internal test cases for LinkedHashTreeMap -> doubleCapacity(), using boundary value analysis **/
public class BoundaryValueTest extends TestCase {
    public void testLowerBound() {
        @SuppressWarnings("unchecked") // arrays and generics don't get along.
        Node<String, String>[] oldTable = new Node[0];

        Node<String, String>[] newTable = LinkedHashTreeMap.doubleCapacity(oldTable);
        assertEquals(0, newTable.length);
    }
    
    public void testUpperBound() {
        @SuppressWarnings("unchecked") // arrays and generics don't get along.
        Node<String, String>[] oldTable = new Node[Integer.MAX_VALUE/32];
        Node<String, String>[] newTable = LinkedHashTreeMap.doubleCapacity(oldTable);
        assertEquals(Integer.MAX_VALUE/16-1, newTable.length);
    }
    
    public void testUpperBoundFails() {
        @SuppressWarnings("unchecked") // arrays and generics don't get along.
        Node<String, String>[] oldTable = new Node[Integer.MAX_VALUE/16];
        
        try {
            LinkedHashTreeMap.doubleCapacity(oldTable);
            fail();
        } catch (OutOfMemoryError e) {
            /* size not supported */
        }
    }
    
    /** Private helpers for helping with test validation **/
    private static final Node<String, String> head = new Node<String, String>();
    
    private Node<String, String> node(String value) {
        return new Node<String, String>(null, value, value.hashCode(), head, head);
    }
    
    private Node<String, String> node(Node<String, String> left, String value,
            Node<String, String> right) {
        Node<String, String> result = node(value);
        if (left != null) {
            result.left = left;
            left.parent = result;
        }
        if (right != null) {
            result.right = right;
            right.parent = result;
        }
        return result;
    }
    
    private void assertTree(String expected, Node<?, ?> root) {
        assertEquals(expected, toString(root));
        assertConsistent(root);
    }
    
    private void assertConsistent(Node<?, ?> node) {
        int leftHeight = 0;
        if (node.left != null) {
            assertConsistent(node.left);
            assertSame(node, node.left.parent);
            leftHeight = node.left.height;
        }
        int rightHeight = 0;
        if (node.right != null) {
            assertConsistent(node.right);
            assertSame(node, node.right.parent);
            rightHeight = node.right.height;
        }
        if (node.parent != null) {
            assertTrue(node.parent.left == node || node.parent.right == node);
        }
        if (Math.max(leftHeight, rightHeight) + 1 != node.height) {
            fail();
        }
    }
    
    private String toString(Node<?, ?> root) {
        if (root == null) {
            return ".";
        } else if (root.left == null && root.right == null) {
            return String.valueOf(root.key);
        } else {
            return String.format("(%s %s %s)", toString(root.left), root.key, toString(root.right));
        }
    }
}

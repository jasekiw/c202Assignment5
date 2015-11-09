package trees;

import java.io.*;
import java.util.*;

public class BinarySearchTree<E extends Comparable<E>> extends AbstractTree<E> {
  /**
   * @see
   */
  public int comparisons = 0;
  protected TreeNode<E> root;
  protected int size = 0;

  /** Create a default binary tree */
  public BinarySearchTree() { }

  /** Create a binary tree from an array of objects */
  public BinarySearchTree(E[] objects) {
    for (int i = 0; i < objects.length; i++)
      insert(objects[i]);
  }

  /** Returns true if the element is in the tree */
  public boolean search(E e) {
    TreeNode<E> current = root; // Start from the root
    while (current != null) {
      if (e.compareTo(current.element) < 0) {
        comparisons++;
        current = current.left;
      }
      else if (e.compareTo(current.element) > 0) {
        comparisons++;
        current = current.right;
      }
      else // element matches current.element
      {
        comparisons++;
        return true; // Element is found
      }

    }
    return false;
  }

    private TreeNode<E> find(E e) {
    TreeNode<E> current = root; // Start from the root
    while (current != null) {
      if (e.compareTo(current.element) < 0) {
        current = current.left;
      }
      else if (e.compareTo(current.element) > 0) {
        current = current.right;
      }
      else // element matches current.element
        return current; // Element is found
    }
    return null;
  }

  /** Insert element o into the binary tree
   * Return true if the element is inserted successfully. 
   *  Uses an iterative algorithm
   */
  public boolean insert(E e) {
    if (root == null)
      root = createNewNode(e); // Create a new root
    else {
      // Locate the parent node
      TreeNode<E> parent = null;
      TreeNode<E> current = root;
      while (current != null)
        if (e.compareTo(current.element) < 0) {
          parent = current;
          current = current.left;
        }
        else if (e.compareTo(current.element) > 0) {
          parent = current;
          current = current.right;
        }
        else
          return false; // Duplicate node not inserted
          
      // Create the new node and attach it to the parent node
      if (e.compareTo(parent.element) < 0)
        parent.left = createNewNode(e);
      else
        parent.right = createNewNode(e);
    }
    size++;
    return true; // Element inserted
  }

  protected TreeNode<E> createNewNode(E e) {
    return new TreeNode<E>(e);
  }

  /** Inorder traversal from the root*/
  public void inorder() {
    inorder(root);
  }

  /** Inorder traversal from a subtree */
  protected void inorder(TreeNode<E> root) {
    if (root == null) return;
    inorder(root.left);
    System.out.print(root.element + " ");
    inorder(root.right);
  }

   
  /** Postorder traversal from the root */
  public void postorder() {
    postorder(root);
  }

  /** Postorder traversal from a subtree */
  protected void postorder(TreeNode<E> root) {
    if (root == null) return;
    postorder(root.left);
    postorder(root.right);
    System.out.print(root.element + " ");
  }

  /** Preorder traversal from the root */
  public void preorder() {
    preorder(root);
  }

  /** Preorder traversal from a subtree */
  protected void preorder(TreeNode<E> root) {
    if (root == null) return;
    System.out.print(root.element + " ");
    preorder(root.left);
    preorder(root.right);
  }

  /** Inner class tree node */
  public static class TreeNode<E extends Comparable<E>> {
    E element;
    TreeNode<E> left;
    TreeNode<E> right;

    public TreeNode(E e) {
      element = e;
    }
  }

  /** Get the number of nodes in the tree */
  public int getSize() {
    return size;
  }

  /** Returns the root of the tree */
  public TreeNode getRoot() {
    return root;
  }

    /** Returns an ArrayList containing elements in the path from the root leading to the specified element, returns an empty ArrayList if no  such element exists. */
    public ArrayList<E> path(E e){
        ArrayList<E> list = new ArrayList<>();
        TreeNode<E> current = root; // Start from the root
        while (current != null) {
          list.add(current.element);
          if (e.compareTo(current.element) < 0) {
            current = current.left;
          }
          else if (e.compareTo(current.element) > 0) {
            current = current.right;
          }
          else // element matches current.element
            break;
        }
        if(current == null)
          list.clear();// element not found so clear the history
        return list;
  }


  /**
   * Gets the number of leaves of the tree
   * @return The number of leaves
   */
    public int  getNumberOfLeaves() { // big O(n)
      if(root == null)
        return 0;
      return getNumberOfLeaves(root);
    }
    private int getNumberOfLeaves(TreeNode<E> node)
    {
        int total = 0;
        if(node.left != null)
        {
          total+= getNumberOfLeaves(node.left);
        }
        if(node.right != null)
        {
          total+= getNumberOfLeaves(node.right);
        }
        if(node.left == null && node.right == null)
          total++;
        return total;

    }


  /**
   * Gets the left subtree of the element
   * @requires e to be in the list
   * @ensures the ArrayList to have 0 or more elements in it
   * @param e The element to get the left subtree of
   * @return An ArrayList of the left subtree
   */
    public ArrayList<E> leftSubTree(E e) {
      if(e == null)
        return new ArrayList<>();
       TreeNode<E> current = find(e);
      if(current == null)
        return new ArrayList<>();
      if(current.left == null)
        return new ArrayList<>();
      ArrayList<E> list = new ArrayList<>();
      subTreePreorder(current.left, list);
      return list;
    }

  /**
   * Gets the subtree and adds the current element to the given list in preorder
   * @requires node to not be null
   * @ensures the element will be added to the list
   * @param node The element to add to the list and the node to check the left and right children
   * @param list The list to be added to
   */
    private void subTreePreorder(TreeNode<E> node, ArrayList<E> list)
    {
      if(node == null)
        return;
      list.add(node.element); 
      subTreePreorder(node.left, list);
      subTreePreorder(node.right, list);
    }

  /**
   * Gets the right sub tree of the given element
   * @requires e has to be in the BST
   * @ensures An ArrayList will be returned with 0 or more elements
   * @param e The element to get the right sub tree of
   * @return An ArrayList of the right sub tree
   */
    public ArrayList<E> rightSubTree(E e){
      if(e == null)
        return new ArrayList<>();
       TreeNode<E> current = find(e); // we search for the element's location
       if(current == null)
        return new ArrayList<>();
      if(current.right == null)
        return new ArrayList<>();
      ArrayList<E> list = new ArrayList<>();
      subTreePreorder(current.right, list);
      return list;
    }

  /**
   * Gets the inorder Predecessor of the element specified
   * @requires e is in the list. and e has a predecessor
   * @ensures the result is not null
   * @param e The element to search for
   * @return The predecessor of the element
   */
    public E inorderPredecessor(E e){
      TreeNode<E> current =  find(e);
      if(current == null) return null;
      if(current.left != null) // case 1: has left subtree
      {
        current = current.left; //left subtree
        while(current.right != null) // find right most
          current = current.right;
          return current.element;
      }
      else // case 2: no left subtree traverse until we hit the element
      {
        TreeNode<E> parent = root; 
        TreeNode<E> predecessor = null;
        while(parent != null)
        {
          if(current.element.compareTo(parent.element) > 0)
          {
              predecessor = parent;
              parent = parent.right;
          }
          else
          {
            parent = parent.left;
          }
        }
        if(predecessor == null)
          return null;
        else
          return predecessor.element;
      }

    }

    
    
  /** Delete an element from the binary tree.
   * Return true if the element is deleted successfully
   * Return false if the element is not in the tree */
  public boolean delete(E e) {
    // Locate the node to be deleted and also locate its parent node
    TreeNode<E> parent = null;
    TreeNode<E> current = root;
    while (current != null) {
      if (e.compareTo(current.element) < 0) {
        parent = current;
        current = current.left;
      }
      else if (e.compareTo(current.element) > 0) {
        parent = current;
        current = current.right;
      }
      else
        break; // Element is in the tree pointed by current
    }
    if (current == null)
      return false; // Element is not in the tree
    // Case 1: current has no left children
    if (current.left == null) {
      // Connect the parent with the right child of the current node
      if (parent == null) {
         root = current.right;
      }
      else {
        if (e.compareTo(parent.element) < 0)
          parent.left = current.right;
        else
          parent.right = current.right;
      }
    }
    else {
      // Case 2 & 3: The current node has a left child
      // Locate the rightmost node in the left subtree of
      // the current node and also its parent
      TreeNode<E> parentOfRightMost = current;
      TreeNode<E> rightMost = current.left;

      while (rightMost.right != null) {
        parentOfRightMost = rightMost;
        rightMost = rightMost.right; // Keep going to the right
      }
      // Replace the element in current by the element in rightMost
      current.element = rightMost.element;
      
      // Eliminate rightmost node
      if (parentOfRightMost.right == rightMost)
        parentOfRightMost.right = rightMost.left;
      else
        // Special case: parentOfRightMost == current
        parentOfRightMost.left = rightMost.left;
    }
    size--;
    return true; // Element inserted
  }
  
  /** Obtain an iterator. Use inorder. */
  public Iterator iterator() {
    return inorderIterator();
  }

  /** Obtain an inorder iterator */
  public Iterator inorderIterator() {
    return new InorderIterator();
  }

  // Inner class InorderIterator
  class InorderIterator implements Iterator {
    // Store the elements in a list
    private ArrayList<E> list = new ArrayList<E>();
    private int current = 0; // Point to the current element in list

    public InorderIterator() {
      inorder(); // Traverse binary tree and store elements in list
    }

    /** Inorder traversal from the root*/
    private void inorder() {
      inorder(root);
    }

    /** Inorder traversal from a subtree */
    private void inorder(TreeNode<E> root) {
      if (root == null)return;
      inorder(root.left);
      list.add(root.element);
      inorder(root.right);
    }

    /** Next element for traversing? */
    public boolean hasNext() {
      if (current < list.size())
        return true;
      return false;
    }

    /** Get the current element and move cursor to the next */
    public Object next() {
      return list.get(current++);
    }

    /** Remove the current element and refresh the list */
    public void remove() {
      delete(list.get(current)); // Delete the current element
      list.clear(); // Clear the list
      inorder(); // Rebuild the list
    }
  }

  /** Remove all elements from the tree */
  public void clear() {
    root = null;
    size = 0;
  }
}

package org.example.binary_tree;

import lombok.Getter;
import lombok.Setter;
import org.example.lab0.Product;

import java.util.*;

/* binary search method and method to make a tree from array */
@Getter
@Setter
public class BalancedBinaryTree {

    BinaryTree root;

    public BinaryTree makeBinaryTree(List<Product> list, int start, int end) {

            if (start > end) {
                return null;
            }

            int mid = (start + end) / 2;
            BinaryTree node = new BinaryTree(list.get(mid));


            node.setLeftChild(makeBinaryTree(list, start, mid - 1));
            node.setRightChild(makeBinaryTree(list, mid + 1, end));

            return node;

    }


//обход в прямом порядке
    public static void preorderTraversal(BinaryTree node) {
        if (node == null) {
            return;
        }

        System.out.print(node + " ");
        preorderTraversal(node.getLeftChild());
        preorderTraversal(node.getRightChild());
    }


    //обход в обратном порядке
    public static void postorderTraversal(BinaryTree node) {
        if (node == null) {
            return;
        }

        postorderTraversal(node.getLeftChild());
        postorderTraversal(node.getRightChild());
        System.out.print(node + " ");
    }

//центрированный/симметричный обход
    public static void inorderTraversal(BinaryTree node) {
        if (node == null) {
            return;
        }

        inorderTraversal(node.getLeftChild());
        System.out.print(node + " ");
        inorderTraversal(node.getRightChild());
    }


    //вставка элемента
    public void insert(Product key) {
        root = insertNode(root, key);
    }

    private BinaryTree insertNode(BinaryTree root, Product key) {
        if (root == null) {
            root = new BinaryTree(key);
            return root;
        }

        if (key.getId() < root.getNode().getId()) {
            root.setLeftChild(insertNode(root.getLeftChild(), key));
        } else if (key.getId() > root.getNode().getId()) {
            root.setRightChild(insertNode(root.getRightChild(), key));
        }

        return root;
    }



    //удаление элемента
    public void delete(int key) {
        root = deleteNode(root, key);
    }

    private BinaryTree deleteNode(BinaryTree root, int key) {
        if (root == null) {
            return root;
        }

        if (key < root.getNode().getId()) {
            root.setLeftChild(deleteNode(root.getLeftChild(), key));
        } else if (key > root.getNode().getId()) {
            root.setRightChild(deleteNode(root.getRightChild(), key));
        } else {
            // Узел для удаления найден

            // Узел без потомков или с одним потомком
            if (root.getLeftChild() == null) {
                return root.getRightChild();
            } else if (root.getRightChild() == null) {
                return root.getLeftChild();
            }

            // Узел с двумя потомками
            root.setNode(minValue(root.getRightChild()));

            // Удаление наименьшего значения из правого поддерева
            root.setRightChild(deleteNode(root.getRightChild(), root.getNode().getId()));
        }

        return root;
    }



    private Product minValue(BinaryTree root) {
        Product minValue = root.getNode();
        while (root.getLeftChild() != null) {
            minValue = root.getLeftChild().getNode();
            root = root.getLeftChild();
        }
        return minValue;
    }




    //поиск элемента
    public int binaryTreeSearch(int id){

        if (this.root == null){
            return -1;
        }
        int stepsToFind = 0;

        BinaryTree current = root;
        while (true){
            stepsToFind++;

            if (current.getNode().getId() < id) {
                if (!current.hasRight()){
                    break;
                }
                current = current.getRightChild();
            }
            if (current.getNode().getId() > id){
                if (!current.hasLeft()){
                    break;
                }
                current = current.getLeftChild();
            }
            if (current.getNode().getId() == id){
                System.out.println("<Search in Binary Tree>\t" +
                        "Product with ID [" + id +"]  ====> " + current.getNode().getName() +
                        " | " + current.getNode().getDescription() + " | " + current.getNode().getAmount());
                return stepsToFind;
            }
        }
        System.out.println("Product with id=" +
                id + " ----> is not found!");
        return -1;
    }



}

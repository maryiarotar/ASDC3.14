package org.example.binary_tree;

import lombok.Getter;
import org.example.lab0.Product;

/* binary tree structure for unsorted array */
@Getter
public class BinaryTree {

    private Product node;
    private BinaryTree leftChild;
    private BinaryTree rightChild;

    public BinaryTree(){
        this.node = null;
        leftChild = null;
        rightChild = null;
    }

    public BinaryTree(Product prod){
        this.node = prod;
        leftChild = null;
        rightChild = null;
    }

    public void setLeftChild(BinaryTree tree){
        this.leftChild = tree;
    }
    public void setRightChild(BinaryTree tree){
        this.rightChild = tree;
    }
    public void setNode(Product prod){
        this.node = prod;
    }

    public boolean hasLeft(){
        if (this.leftChild != null ) return true;
        return false;
    }

    public boolean hasRight(){
        if (this.rightChild != null ) return true;
        return false;
    }

    @Override
    public String toString() {
        if (node!= null) {
            return "Node---{" +
                    "id=" + node.getId() +
                    ",\n  left_for_" +node.getId() +" [" + leftChild +
                    ",\n \t\tright_for_" + node.getId() +" [" + rightChild +
                    "]--- }";
        }
        return null;
    }



}

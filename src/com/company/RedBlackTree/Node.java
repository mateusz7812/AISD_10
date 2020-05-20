package com.company.RedBlackTree;

public class Node<T> implements NodeInterface<T> {
    private final T key;
    private T[] value;
    private NodeInterface<T> parent;
    private NodeInterface<T> leftChild;
    private NodeInterface<T> rightChild;
    private int color;

    public Node(T key,T[] value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public T getKey() {
        return key;
    }

    @Override
    public T[] getValue() {
        return value;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public NodeInterface<T> getParent() {
        return parent;
    }

    @Override
    public NodeInterface<T> getLeftChild() {
        return leftChild;
    }

    @Override
    public NodeInterface<T> getRightChild() {
        return rightChild;
    }

    @Override
    public void setLeftChild(NodeInterface<T> leftChild) {
        this.leftChild = leftChild;
        //if(leftChild != null) this.leftChild.setParent(this);
    }

    @Override
    public void setRightChild(NodeInterface<T> rightChild) {
        this.rightChild = rightChild;
        //if(rightChild != null) this.rightChild.setParent(this);
    }

    @Override
    public void setParent(NodeInterface<T> parent) {
        this.parent = parent;
    }
}

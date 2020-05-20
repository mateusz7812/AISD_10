package com.company.RedBlackTree;

public interface NodeInterface<T> {
    T getKey();
    T[] getValue();

    int getColor();
    void setColor(int color);

    NodeInterface<T> getParent();
    NodeInterface<T> getLeftChild();
    NodeInterface<T> getRightChild();

    void setLeftChild(NodeInterface<T> leftChild);
    void setRightChild(NodeInterface<T> rightChild);
    void setParent(NodeInterface<T> parent);
}

package com.company.RedBlackTree;

public interface RedBlackTreeInterface<T> {
    void Add(T key, T[] value);
    boolean Remove(T key);
    T[] Get(T key);
    float getPartOfBlackLeafs();

    int getHeight();
}

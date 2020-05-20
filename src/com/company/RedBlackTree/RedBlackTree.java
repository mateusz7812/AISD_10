package com.company.RedBlackTree;

import com.company.Comparator.ComparatorInterface;
import com.company.RedBlackTree.Color.Colors;
import com.company.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class RedBlackTree<T> implements RedBlackTreeInterface<T> {
    protected NodeInterface<T> root;
    protected final NodeInterface<T> nil;
    private final ComparatorInterface<T> comparator;

    public RedBlackTree(ComparatorInterface<T> comparator) {
        this.comparator = comparator;
        nil = new Node<>(null, null);
        root = nil;
    }

    protected void setRoot(NodeInterface<T> node) {
        node.setParent(nil);
        root = node;
    }

    @Override
    public void Add(T key, T[] value) {
        var node = new Node<T>(key, value);
        node.setLeftChild(nil);
        node.setRightChild(nil);

        if (root == nil) {
            node.setColor(Colors.BLACK);
            root = node;
            node.setParent(nil);
        } else {
            node.setColor(Colors.RED);
            _add(root, node);
            fixTree(node);
        }
    }

    private void _add(NodeInterface<T> parent, NodeInterface<T> nodeToAdd) {
        var comparing = comparator.Compare(nodeToAdd.getKey(), parent.getKey());
        if (comparing <= 0) {
            addAsLeftChild(parent, nodeToAdd);
        } else {
            addAsRightChild(parent, nodeToAdd);
        }
    }

    private void addAsRightChild(NodeInterface<T> parent, NodeInterface<T> nodeToAdd) {
        var rightChild = parent.getRightChild();
        if (rightChild == nil) {
            parent.setRightChild(nodeToAdd);
            nodeToAdd.setParent(parent);
        } else {
            _add(rightChild, nodeToAdd);
        }
    }

    private void addAsLeftChild(NodeInterface<T> parent, NodeInterface<T> nodeToAdd) {
        var leftChild = parent.getLeftChild();
        if (leftChild == nil) {
            parent.setLeftChild(nodeToAdd);
            nodeToAdd.setParent(parent);
        } else {
            _add(leftChild, nodeToAdd);
        }
    }

    private void fixTree(NodeInterface<T> node) {
        while (isRed(node.getParent())) {
            if (isLeftChild(node.getParent())) {
                node = fixTreeForLeftChild(node);
            } else {
                node = fixTreeForRightChild(node);
            }
        }
        root.setColor(Colors.BLACK);
    }

    private NodeInterface<T> fixTreeForRightChild(NodeInterface<T> node) {
        NodeInterface<T> uncle;
        uncle = node.getParent().getParent().getLeftChild();
        if (uncle != nil && isRed(uncle)) {
            node = fixColorsForRedUncle(node, uncle);
        } else {
            node = fixColorsWithRightLeftRotations(node);
        }
        return node;
    }

    private NodeInterface<T> fixTreeForLeftChild(NodeInterface<T> node) {
        NodeInterface<T> uncle;
        uncle = node.getParent().getParent().getRightChild();
        if (uncle != nil && isRed(uncle)) {
            node = fixColorsForRedUncle(node, uncle);
        } else {
            node = fixColorWithLeftRightRotations(node);
        }
        return node;
    }

    private NodeInterface<T> fixColorsForRedUncle(NodeInterface<T> node, NodeInterface<T> uncle) {
        node.getParent().setColor(Colors.BLACK);
        uncle.setColor(Colors.BLACK);
        node.getParent().getParent().setColor(Colors.RED);
        node = node.getParent().getParent();
        return node;
    }

    private NodeInterface<T> fixColorsWithRightLeftRotations(NodeInterface<T> node) {
        if (isLeftChild(node)) {
            node = node.getParent();
            rotationRight(node);
        }
        node.getParent().setColor(Colors.BLACK);
        node.getParent().getParent().setColor(Colors.RED);
        rotationLeft(node.getParent().getParent());
        return node;
    }

    private NodeInterface<T> fixColorWithLeftRightRotations(NodeInterface<T> node) {
        if (isRightChild(node)) {
            node = node.getParent();
            rotationLeft(node);
        }
        node.getParent().setColor(Colors.BLACK);
        node.getParent().getParent().setColor(Colors.RED);
        rotationRight(node.getParent().getParent());
        return node;
    }

    private boolean isLeftChild(NodeInterface<T> node) {
        return node == node.getParent().getLeftChild();
    }

    private boolean isRightChild(NodeInterface<T> node) {
        return node == node.getParent().getRightChild();
    }

    protected void rotationRight(NodeInterface<T> node) {
        if (node.getParent() != nil) {
            if (isLeftChild(node)) {
                node.getParent().setLeftChild(node.getLeftChild());
            } else {
                node.getParent().setRightChild(node.getLeftChild());
            }

            node.getLeftChild().setParent(node.getParent());
            node.setParent(node.getLeftChild());
            if (node.getLeftChild().getRightChild() != nil) {
                node.getLeftChild().getRightChild().setParent(node);
            }
            node.setLeftChild(node.getLeftChild().getRightChild());
            node.getParent().setRightChild(node);
        } else {
            NodeInterface<T> left = root.getLeftChild();
            root.setLeftChild(root.getLeftChild().getRightChild());
            left.getRightChild().setParent(root);
            root.setParent(left);
            left.setRightChild(root);
            left.setParent(nil);
            root = left;
        }

    }

    protected void rotationLeft(NodeInterface<T> node) {
        if (node.getParent() != nil) {
            if (isLeftChild(node)) {
                node.getParent().setLeftChild(node.getRightChild());
            } else {
                node.getParent().setRightChild(node.getRightChild());
            }
            node.getRightChild().setParent(node.getParent());
            node.setParent(node.getRightChild());
            if (node.getRightChild().getLeftChild() != nil) {
                node.getRightChild().getLeftChild().setParent(node);
            }
            node.setRightChild(node.getRightChild().getLeftChild());
            node.getParent().setLeftChild(node);
        } else {
            NodeInterface<T> right = root.getRightChild();
            root.setRightChild(right.getLeftChild());
            right.getLeftChild().setParent(root);
            root.setParent(right);
            right.setLeftChild(root);
            right.setParent(nil);
            root = right;
        }
    }

    @Override
    public T[] Get(T key) {
        if (root == nil) {
            return null;
        } else {
            NodeInterface<T> node = _get(root, key);
            if (node == null) return null;
            return node.getValue();
        }
    }

    private NodeInterface<T> _get(NodeInterface<T> node, T key) {
        if (node == nil) return null;

        var comparing = comparator.Compare(key, node.getKey());

        if (comparing == 0) {
            return node;
        } else if (comparing < 0) {
            return _get(node.getLeftChild(), key);
        } else {
            return _get(node.getRightChild(), key);
        }
    }

    private int getHeightOfSubtree(NodeInterface<T> node) {
        if (node == nil) return 0;
        return Math.max(getHeightOfSubtree(node.getLeftChild()) + 1, getHeightOfSubtree(node.getRightChild()) + 1);
    }

    @Override
    public boolean Remove(T key) {
        var nodeToRemove = _get(root, key);
        return remove(nodeToRemove);
    }

    void transplant(NodeInterface<T> target, NodeInterface<T> with) {
        if (target.getParent() == nil) {
            root = with;
        } else if (isLeftChild(target)) {
            target.getParent().setLeftChild(with);
        } else
            target.getParent().setRightChild(with);
        with.setParent(target.getParent());
    }

    boolean remove(NodeInterface<T> z) {
        if ((z = _get(root, z.getKey())) == null) return false;
        NodeInterface<T> x;
        NodeInterface<T> y = z;
        int y_original_color = y.getColor();

        if (z.getLeftChild() == nil) {
            x = z.getRightChild();
            transplant(z, z.getRightChild());
        } else if (z.getRightChild() == nil) {
            x = z.getLeftChild();
            transplant(z, z.getLeftChild());
        } else {
            y = treeMinimum(z.getRightChild());
            y_original_color = y.getColor();
            x = y.getRightChild();
            if (y.getParent() == z)
                x.setParent(y);
            else {
                transplant(y, y.getRightChild());
                y.setRightChild(z.getRightChild());
                y.getRightChild().setParent(y);
            }
            transplant(z, y);
            y.setLeftChild(z.getLeftChild());
            y.getLeftChild().setParent(y);
            y.setColor(z.getColor());
        }
        if (y_original_color == Colors.BLACK)
            deleteFixup(x);
        return true;
    }

    NodeInterface<T> treeMinimum(NodeInterface<T> subTreeRoot) {
        while (subTreeRoot.getLeftChild() != nil) {
            subTreeRoot = subTreeRoot.getLeftChild();
        }
        return subTreeRoot;
    }

    void deleteFixup(NodeInterface<T> x) {
        while (x != root && isBlack(x)) {
            if (isLeftChild(x)) {
                x = deleteFixUpForLeftChild(x);
            } else {
                x = deleteFixUpForRightChild(x);
            }
        }
        x.setColor(Colors.BLACK);
    }

    private NodeInterface<T> deleteFixUpForRightChild(NodeInterface<T> x) {
        NodeInterface<T> w = x.getParent().getLeftChild();
        if (isRed(w)) {
            w.setColor(Colors.BLACK);
            x.getParent().setColor(Colors.RED);
            rotationRight(x.getParent());
            w = x.getParent().getLeftChild();
        }

        if (bothChildrensBlack(w)) {
            w.setColor(Colors.RED);
            x = x.getParent();
            return x;

        } else if (isBlack(w.getLeftChild())) {
            w.getRightChild().setColor(Colors.BLACK);
            w.setColor(Colors.RED);
            rotationLeft(w);
            w = x.getParent().getLeftChild();
        }

        if (isRed(w.getLeftChild())) {
            w.setColor(x.getParent().getColor());
            x.getParent().setColor(Colors.BLACK);
            w.getLeftChild().setColor(Colors.BLACK);
            rotationRight(x.getParent());
            x = root;
        }
        return x;
    }

    private boolean bothChildrensBlack(NodeInterface<T> w) {
        return isBlack(w.getRightChild()) && isBlack(w.getLeftChild());
    }

    private NodeInterface<T> deleteFixUpForLeftChild(NodeInterface<T> x) {
        NodeInterface<T> w = x.getParent().getRightChild();
        if (isRed(w)) {
            w.setColor(Colors.BLACK);
            x.getParent().setColor(Colors.RED);
            rotationLeft(x.getParent());
            w = x.getParent().getRightChild();
        }

        if (bothChildrensBlack(w)) {
            w.setColor(Colors.RED);
            x = x.getParent();
            return x;

        } else if (isBlack(w.getRightChild())) {
            w.getLeftChild().setColor(Colors.BLACK);
            w.setColor(Colors.RED);
            rotationRight(w);
            w = x.getParent().getRightChild();
        }

        if (isRed(w.getRightChild())) {
            w.setColor(x.getParent().getColor());
            x.getParent().setColor(Colors.BLACK);
            w.getRightChild().setColor(Colors.BLACK);
            rotationLeft(x.getParent());
            x = root;
        }
        return x;
    }

    private boolean isBlack(NodeInterface<T> x) {
        return Colors.BLACK == x.getColor();
    }

    private boolean isRed(NodeInterface<T> w) {
        return Colors.RED == w.getColor();
    }

    @Override
    public String toString() {
        var height = getHeightOfSubtree(root);
        var length = (int) Math.pow(2, height) * 2;
        var nodesInOneLine = new ArrayList<>(List.of(root));

        var lines = buildLines(length, nodesInOneLine);
        return String.join("\n", lines) + "\n";
    }

    public ArrayList<String> buildLines(int length, ArrayList<NodeInterface<T>> nodesInOneLine){
        if(nodesInOneLine.stream().allMatch(n->n==nil)) return new ArrayList<>();

        var oneNodeLength = length / nodesInOneLine.size();
        StringBuilder line = new StringBuilder();
        var newNodes = new ArrayList<NodeInterface<T>>();
        for (var node : nodesInOneLine) {
            if (node != nil) {
                line.append(StringUtils.center(node.getKey().toString(), oneNodeLength));
                newNodes.add(node.getLeftChild());
                newNodes.add(node.getRightChild());
            } else {
                line.append(StringUtils.center("", oneNodeLength));
                newNodes.add(nil);
                newNodes.add(nil);
            }
        }
        var lines = new ArrayList<String>();
        lines.add(String.valueOf(line));
        lines.addAll(buildLines(length, newNodes));
        return lines;
    }

    @Override
    public float getPartOfBlackLeafs() {
        NodeInterface<T>[] allLeafs = getAllLeafs(root);
        return Stream.of(allLeafs).filter(this::isBlack).count() / (float) allLeafs.length;
    }

    @Override
    public int getHeight() {
        return getHeightOfSubtree(root);
    }

    @SuppressWarnings("unchecked")
    private NodeInterface<T>[] getAllLeafs(NodeInterface<T> node) {
        if (node == nil) return new NodeInterface[0];
        else if (node.getRightChild() == nil && node.getLeftChild() == nil) return new NodeInterface[]{node};
        else {
            var a = getAllLeafs(node.getLeftChild());
            var b = getAllLeafs(node.getRightChild());
            return Stream.concat(Arrays.stream(a), Arrays.stream(b))
                    .toArray(NodeInterface[]::new);
        }
    }
}

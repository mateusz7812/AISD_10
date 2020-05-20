package tests;

import com.company.Comparator.IntegerComparator;
import com.company.RedBlackTree.Color.Colors;
import com.company.RedBlackTree.Node;
import com.company.RedBlackTree.NodeInterface;
import com.company.RedBlackTree.RedBlackTree;
import org.junit.Assert;
import org.junit.Test;

public class TestRedBlackTree {
    @Test
    public void TestAddElement() {
        var bst = new TestIntegerTree();
        Assert.assertNull(bst.Get(2));

        bst.Add(2, new Integer[]{2});

        Integer[] node = bst.Get(2);
        Assert.assertNotNull(node);
        Assert.assertEquals(2, node[0].intValue());
        Assert.assertEquals(Colors.BLACK, bst.getRoot().getColor());
    }

    @Test
    public void TestAddMoreElements() {
        var bst = new TestIntegerTree();

        bst.Add(1, new Integer[]{1});
        bst.Add(2, new Integer[]{1});
        bst.Add(0, new Integer[]{1});

        NodeInterface<Integer> root = bst.getRoot();
        Assert.assertNotNull(root);
        Assert.assertEquals(1, root.getKey().intValue());
        Assert.assertEquals(Colors.BLACK, root.getColor());

        NodeInterface<Integer> rightChild = root.getRightChild();
        Assert.assertNotNull(rightChild);
        Assert.assertEquals(2, rightChild.getKey().intValue());
        Assert.assertEquals(root, rightChild.getParent());
        Assert.assertEquals(Colors.RED, rightChild.getColor());

        NodeInterface<Integer> leftChild = root.getLeftChild();
        Assert.assertNotNull(leftChild);
        Assert.assertEquals(0, leftChild.getKey().intValue());
        Assert.assertEquals(root, leftChild.getParent());
        Assert.assertEquals(Colors.RED, leftChild.getColor());
    }

    @Test
    public void TestRotateLeft() {
        var bst = new TestIntegerTree();
        var node0 = new Node<>(0, new Integer[]{0});
        var node1 = new Node<>(1, new Integer[]{1});
        var node2 = new Node<>(2, new Integer[]{2});
        var node3 = new Node<>(3, new Integer[]{3});
        var node4 = new Node<>(4, new Integer[]{4});

        node1.setLeftChild(node0);
        node1.setRightChild(node3);
        node3.setLeftChild(node2);
        node3.setRightChild(node4);

        node0.setParent(node1);
        node3.setParent(node1);
        node2.setParent(node3);
        node4.setParent(node3);

        bst.setRoot(node1);

        bst.rotateLeft(node1);

        NodeInterface<Integer> root = bst.getRoot();
        Assert.assertEquals(bst.getNil(), root.getParent());
        Assert.assertEquals(3, root.getKey().intValue());
        Assert.assertEquals(1, root.getLeftChild().getKey().intValue());
        Assert.assertEquals(0, root.getLeftChild().getLeftChild().getKey().intValue());
        Assert.assertEquals(2, root.getLeftChild().getRightChild().getKey().intValue());
        Assert.assertEquals(4, root.getRightChild().getKey().intValue());
    }

    @Test
    public void TestRotateRight() {
        var bst = new TestIntegerTree();
        var node0 = new Node<>(0, new Integer[]{0});
        var node1 = new Node<>(1, new Integer[]{1});
        var node2 = new Node<>(2, new Integer[]{2});
        var node3 = new Node<>(3, new Integer[]{3});
        var node4 = new Node<>(4, new Integer[]{4});

        node3.setLeftChild(node1);
        node1.setLeftChild(node0);
        node1.setRightChild(node2);
        node3.setRightChild(node4);

        node1.setParent(node3);
        node0.setParent(node1);
        node2.setParent(node1);
        node4.setParent(node3);

        bst.setRoot(node3);

        bst.rotateRight(node3);

        NodeInterface<Integer> root = bst.getRoot();
        Assert.assertEquals(bst.getNil(), root.getParent());
        Assert.assertEquals(1, root.getKey().intValue());
        Assert.assertEquals(0, root.getLeftChild().getKey().intValue());
        Assert.assertEquals(3, root.getRightChild().getKey().intValue());
        Assert.assertEquals(2, root.getRightChild().getLeftChild().getKey().intValue());
        Assert.assertEquals(4, root.getRightChild().getRightChild().getKey().intValue());
    }

    @Test
    public void TestAddWithRotationLeft() {
        var bst = new TestIntegerTree();

        bst.Add(0, new Integer[]{1});
        bst.Add(1, new Integer[]{1});
        bst.Add(2, new Integer[]{1});


        NodeInterface<Integer> root = bst.getRoot();
        doAssert(root, 1, Colors.BLACK);

        NodeInterface<Integer> rightChild = root.getRightChild();
        doAssert(rightChild, 2, Colors.RED);
        Assert.assertEquals(root, rightChild.getParent());

        NodeInterface<Integer> leftChild = root.getLeftChild();
        doAssert(leftChild, 0, Colors.RED);
        Assert.assertEquals(root, leftChild.getParent());
    }

    @Test
    public void TestAddWithRotationRight() {
        var bst = new TestIntegerTree();

        bst.Add(2, new Integer[]{1});
        bst.Add(1, new Integer[]{1});
        bst.Add(0, new Integer[]{1});

        NodeInterface<Integer> root = bst.getRoot();
        doAssert(root, 1, Colors.BLACK);

        NodeInterface<Integer> rightChild = root.getRightChild();
        doAssert(rightChild, 2, Colors.RED);
        Assert.assertEquals(root, rightChild.getParent());

        NodeInterface<Integer> leftChild = root.getLeftChild();
        doAssert(leftChild, 0, Colors.RED);
        Assert.assertEquals(root, leftChild.getParent());
    }

    @Test
    public void TestExtendedAdd(){
        var bst = new TestIntegerTree();

        bst.Add(6, new Integer[]{1});
        bst.Add(4, new Integer[]{1});
        bst.Add(7, new Integer[]{1});
        bst.Add(2, new Integer[]{1});
        bst.Add(5, new Integer[]{1});
        bst.Add(1, new Integer[]{1});
        bst.Add(3, new Integer[]{1});
        bst.Add(8, new Integer[]{1});

        var root = bst.getRoot();

        doAssert(root, 6, Colors.BLACK);
        doAssert(root.getLeftChild(), 4, Colors.RED);
        doAssert(root.getLeftChild().getLeftChild(), 2, Colors.BLACK);
        doAssert(root.getLeftChild().getLeftChild().getLeftChild(), 1, Colors.RED);
        doAssert(root.getLeftChild().getLeftChild().getRightChild(), 3, Colors.RED);
        doAssert(root.getLeftChild().getRightChild(), 5, Colors.BLACK);
        doAssert(root.getRightChild(), 7, Colors.BLACK);
        doAssert(root.getRightChild().getRightChild(), 8, Colors.RED);
    }

    @Test
    public void TestRemoving(){
        var bst = new TestIntegerTree();

        bst.Add(4, new Integer[]{1});
        bst.Add(3, new Integer[]{1});

        bst.Remove(3);

        var root = bst.getRoot();
        Assert.assertEquals(4, root.getKey().intValue());
        Assert.assertEquals(bst.getNil(), root.getLeftChild());
    }

    @Test
    public void TestRemovingExtended(){
        var bst = new TestIntegerTree();

        bst.Add(3, null);
        bst.Add(2, null);
        bst.Add(5, null);
        bst.Add(4, null);

        bst.Remove(3);

        var root = bst.getRoot();
        doAssert(root, 4, Colors.BLACK);
        doAssert(root.getRightChild(), 5, Colors.BLACK);
        doAssert(root.getLeftChild(), 2, Colors.BLACK);
    }

    @Test
    public void TestRemovingExtended2(){
        var bst = new TestIntegerTree();

        bst.Add(6, null);
        bst.Add(4, null);
        bst.Add(7, null);
        bst.Add(10, null);
        bst.Add(2, null);
        bst.Add(5, null);
        bst.Add(9, null);
        bst.Add(1, null);
        bst.Add(3, null);
        bst.Add(8, null);

        bst.Remove(4);
        bst.Remove(6);
        bst.Remove(5);

        var root = bst.getRoot();
        doAssert(root, 7, Colors.BLACK);
        doAssert(root.getLeftChild(), 2, Colors.RED);
        doAssert(root.getLeftChild().getLeftChild(), 1, Colors.BLACK);
        doAssert(root.getLeftChild().getRightChild(), 3, Colors.BLACK);
        doAssert(root.getRightChild(), 9, Colors.RED);
        doAssert(root.getRightChild().getLeftChild(), 8, Colors.BLACK);
        doAssert(root.getRightChild().getRightChild(), 10, Colors.BLACK);

    }

    void doAssert(NodeInterface<Integer> node, int key, int color){
        Assert.assertEquals(key, node.getKey().intValue());
        Assert.assertEquals(color, node.getColor());
    }

    @Test
    public void TestPrinting(){
        var bst = new TestIntegerTree();

        bst.Add(4, null);
        bst.Add(2, null);
        bst.Add(6, null);
        bst.Add(1, null);
        bst.Add(3, null);
        bst.Add(5, null);
        bst.Add(7, null);

        System.out.print(bst);

        var lines = bst.toString().split("\n");
        Assert.assertEquals(3, lines.length);
        Assert.assertTrue(lines[0].contains(String.valueOf(4)));
        Assert.assertTrue(lines[1].contains(String.valueOf(2)));
        Assert.assertTrue(lines[1].contains(String.valueOf(6)));
        Assert.assertTrue(lines[2].contains(String.valueOf(1)));
        Assert.assertTrue(lines[2].contains(String.valueOf(3)));
        Assert.assertTrue(lines[2].contains(String.valueOf(5)));
        Assert.assertTrue(lines[2].contains(String.valueOf(7)));

    }

    @Test
    public void TestManualOfPrinting(){
        var bst = new TestIntegerTree();
        bst.AddRange(new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20});
        System.out.print(bst);
    }

    @Test
    public void TestPercentageOfBlackLeafs(){
        var bst = new TestIntegerTree();
        bst.AddRange(new Integer[]{1,2,3,4,5,6,7,8,9,10});
        float percent = bst.getPartOfBlackLeafs();
        Assert.assertEquals(0.8, percent, 0.01);
    }

    class TestIntegerTree extends RedBlackTree<Integer> {
        public TestIntegerTree() {
            super(new IntegerComparator());
        }

        public NodeInterface<Integer> getRoot() {
            return this.root;
        }

        public void rotateRight(NodeInterface<Integer> node) {
            this.rotationRight(node);
        }

        public void rotateLeft(NodeInterface<Integer> node) {
            this.rotationLeft(node);
        }

        public void setRoot(Node<Integer> node) {
            super.setRoot(node);
        }

        public void AddRange(Integer[] integers) {
            for (var integer :
                    integers) {
                Add(integer, null);
            }
        }

        public NodeInterface<Integer> getNil() {
            return nil;
        }
    }
}

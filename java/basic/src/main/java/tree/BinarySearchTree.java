package tree;

/**
 * 实现二叉搜索树
 * @author dwx
 */
public class BinarySearchTree<E>  {

    BinaryTreeNode<E> root;

    /**
     * 空构造函数
     */
    public BinarySearchTree() {

    }


    public BinarySearchTree<E> insertTreeNode(E element) {

        BinaryTreeNode<E> newNode = new BinaryTreeNode<>(element);

        if (null == root) {
            root = newNode;
        }


//        if (element < root.element) {
//
//        }

        return this;
    }

    /**
     * 二叉搜索树的节点类
     * @param <E>
     */
    static class BinaryTreeNode<E> {
        BinaryTreeNode<E> left;
        BinaryTreeNode<E> right;
        BinaryTreeNode<E> parent;

        E element;

        public BinaryTreeNode(E element) {
            this.element = element;
            this.left = null;
            this.right = null;
            this.parent = null;
        }

    }

}

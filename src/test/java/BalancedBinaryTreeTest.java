import org.example.binary_tree.BalancedBinaryTree;
import org.example.binary_tree.BinaryTree;
import org.example.lab0.Measure;
import org.example.lab0.Product;
import org.junit.Assert;
import org.junit.Test;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.List;

public class BalancedBinaryTreeTest {

    @Test
    public void testGettersAndSetters() {
        // Создание экземпляра класса BalancedBinaryTree
        BalancedBinaryTree tree = new BalancedBinaryTree();

        // Создание тестовых данных
        Product product1 = new Product(1, "Product 1", "Description 1", 10.50f, 50, Measure.XS);
        Product product2 = new Product(2, "Product 2", "Description 2", 200f, 10, Measure.XL);

        // Проверка геттеров и сеттеров для поля root
        tree.setRoot(new BinaryTree(product1));
        Assert.assertEquals(product1, tree.getRoot().getNode());

        // Проверка геттеров и сеттеров для поля root (изменение значения)
        tree.getRoot().setNode(product2);
        Assert.assertEquals(product2, tree.getRoot().getNode());
    }

    @Test
    public void testMakeBinaryTree() {
        // Создание экземпляра класса BalancedBinaryTree
        BalancedBinaryTree tree = new BalancedBinaryTree();

        // Создание тестовых данных
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "Product 1", "Description 1", 10.50f, 50, Measure.XS));
        productList.add(new Product(2, "Product 2", "Description 2", 30.55f, 40, Measure.S));
        productList.add(new Product(3, "Product 3", "Description 3", 55.90f, 30, Measure.XL));

        // Построение балансированного двоичного дерева
        BinaryTree binaryTree = tree.makeBinaryTree(productList, 0, productList.size() - 1);

        // Проверка корректности построения дерева
        Assert.assertEquals(2, binaryTree.getNode().getId());
        Assert.assertEquals(1, binaryTree.getLeftChild().getNode().getId());
        Assert.assertEquals(3, binaryTree.getRightChild().getNode().getId());
    }


    @Test
    public void testPreorderTraversal() {
        // Создание экземпляра класса BalancedBinaryTree
        BalancedBinaryTree tree = new BalancedBinaryTree();

        // Создание тестовых данных
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "Product 1", "Description 1", 10.50f, 50, Measure.XS));
        productList.add(new Product(2, "Product 2", "Description 2", 30.55f, 40, Measure.S));
        productList.add(new Product(3, "Product 3", "Description 3", 55.90f, 30, Measure.XL));

        // Построение балансированного двоичного дерева
        BinaryTree binaryTree = tree.makeBinaryTree(productList, 0, productList.size() - 1);

        // Создание StringBuilder для записи вывода
        StringBuilder output = new StringBuilder();

        // Перенаправление вывода в StringBuilder
        PrintStream printStream = new PrintStream(new ByteArrayOutputStream()) {
            @Override
            public void print(String s) {
                output.append(s);
            }
        };

        // Сохранение стандартного потока вывода
        PrintStream oldOut = System.out;

        try {
            // Установка нового потока вывода
            System.setOut(printStream);

            // Вызов метода preorderTraversal с корневым узлом дерева
            tree.preorderTraversal(binaryTree);

        } finally {
            // Восстановление стандартного потока вывода
            System.setOut(oldOut);
        }

        // Создание ожидаемого результата
        StringBuilder expectedOutput = new StringBuilder();
        expectedOutput.append("Node---{id=2," +
                "\n  left_for_2 [Node---{id=1," +
                "\n  left_for_1 [null," +
                        "\n \t\tright_for_1 [null]--- },"+
                        "\n \t\tright_for_2 [Node---{id=3,"+
                        "\n  left_for_3 [null,"+
                        "\n \t\tright_for_3 [null]--- }]--- } Node---{id=1,"+
            "\n  left_for_1 [null,"+
            "\n \t\tright_for_1 [null]--- } Node---{id=3,"+
            "\n  left_for_3 [null,"+
            "\n \t\tright_for_3 [null]--- } ");

        // Проверка совпадения ожидаемого и фактического вывода
        Assert.assertEquals(expectedOutput.toString(), output.toString());
    }



    @Test
    public void testInsert() {
        // Создание экземпляра класса BalancedBinaryTree
        BalancedBinaryTree tree = new BalancedBinaryTree();

        // Создание тестовых данных
        Product product1 = new Product(1, "Product 1", "Description 1", 10.50f, 50, Measure.XS);
        Product product2 = new Product(2, "Product 2", "Description 2", 30.55f, 40, Measure.S);
        Product product3 = new Product(3, "Product 3", "Description 3", 55.90f, 30, Measure.XL);

        // Вставка узлов в дерево
        tree.insert(product2);
        tree.insert(product1);
        tree.insert(product3);

        // Получение корневого узла дерева
        BinaryTree root = tree.getRoot();

        // Проверка корректности вставки узлов
        Assert.assertEquals(product2, root.getNode());
        Assert.assertEquals(product1, root.getLeftChild().getNode());
        Assert.assertEquals(product3, root.getRightChild().getNode());
    }



    @Test
    public void testDelete() {
        // Создание экземпляра класса BalancedBinaryTree
        BalancedBinaryTree tree = new BalancedBinaryTree();

        // Создание тестовых данных
        Product product1 = new Product(1, "Product 1", "Description 1", 10.50f, 50, Measure.XS);
        Product product2 = new Product(2, "Product 2", "Description 2", 30.55f, 40, Measure.S);
        Product product3 = new Product(3, "Product 3", "Description 3", 55.90f, 30, Measure.XL);

        // Вставка узлов в дерево
        tree.insert(product2);
        tree.insert(product1);
        tree.insert(product3);

        // Удаление узла с ключом 2
        tree.delete(2);

        // Получение корневого узла дерева
        BinaryTree root = tree.getRoot();

        // Проверка корректности удаления узла
        Assert.assertEquals(product3, root.getNode());
        Assert.assertEquals(product1, root.getLeftChild().getNode());
        //Assert.assertEquals(null, root.getRightChild().getNode());
    }



}
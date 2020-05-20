package com.company;

import com.company.Comparator.StringComparator;
import com.company.RedBlackTree.RedBlackTree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static RedBlackTree<String> tree;
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        tree = new RedBlackTree<>(new StringComparator());
        int num;
        while (true) {
            System.out.println("\n\nWybierz opcję:");
            System.out.println("1. Wczytaj z pliku");
            System.out.println("2. Dodaj");
            System.out.println("3. Wyszukaj");
            System.out.println("4. Usuń");
            System.out.println("5. Sprawdź procent liści czarnych");
            System.out.println("6. Wyświetl");
            System.out.println("7. Koniec programu");
            num = Integer.parseInt(in.nextLine());
            if (num == 1) loadingFormFileMenu();
            else if (num == 2) addingMenu();
            else if (num == 3) searchingMenu();
            else if (num == 4) removingMenu();
            else if (num == 5) checkingMenu();
            else if (num == 6) printingMenu();
            else if (num == 7) break;
            else System.out.println("Błędny wybór");
        }
    }

    private static void loadingFormFileMenu() throws IOException {
        var reader = new Reader();
        Map<String, String[]> stringMap = reader.ReadAllLines();
        for (String key : stringMap.keySet()) {
            var value = stringMap.get(key);
            tree.Add(key, value);
        }
        System.out.println("Drzewo zostało wczytane");
    }

    private static void printingMenu() {
        if(tree.getHeight()>5) System.out.println("Drzewo zbyt wysokie, nie zostanie wyświetlone");
        else System.out.println(tree);
    }

    private static void checkingMenu() {
        System.out.printf("Procent czarnych liści: %.0f%%", tree.getPartOfBlackLeafs() * 100);
    }

    private static void removingMenu() {
        System.out.println("Podaj klucz:");
        var key = in.nextLine();
        tree.Remove(key);
    }

    private static void searchingMenu() {
        System.out.println("Podaj klucz:");
        var key = in.nextLine();
        String[] strings = tree.Get(key);
        if(strings == null) System.out.println("Nie znaleziono elementu o takim kluczu");
        else System.out.println(Arrays.toString(strings));
    }

    private static void addingMenu() {
        System.out.println("Podaj klucz:");
        var key = in.nextLine();
        var elements = new ArrayList<String>();
        String element = "";
        while (true) {
            System.out.println("Podaj odmianę:");
            element = in.nextLine();
            if (element.equals("")) break;
            elements.add(element);
        }
        String[] myArray = new String[elements.size()];
        tree.Add(key, elements.toArray(myArray));
    }
}

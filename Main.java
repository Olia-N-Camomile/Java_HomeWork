package com.company;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(final String[] args) {

        Map<String, Product> hasMap = new HashMap<>();
        hasMap.put("Миха",
                new Product("Медведь плюшевый","M1-001", .35));
        hasMap.put("Мишутка",
                new Product("Медведь меховой малый","M1-002", .4));
        hasMap.put("Михайло",
                new Product("Медведь меховой большой","M1-003", 1.05));
        hasMap.put("Грузовичек",
                new Product("Машина грузовая пластиковая","M2-001", .2));
        hasMap.put("Кран",
                new Product("Машина кран","M2-002", .5));
        hasMap.put("Мальвина",
                new Product("Кукла \"Мальвина\"","K1-001", .36));
        hasMap.put("Гульнара",
                new Product("Кукла \"Гульнара\"","K2-002", .37));
        printSets(hasMap);
        printNames(hasMap);
        printProducts(hasMap);

    }

    /**
     * Выводит ключи и значения коллекции-отображения.
     * @param hasMap содержит коллекцию
     */
    static void printSets(final Map<String, Product> hasMap) {
        System.out.println("Имена и значения:");
        for (Map.Entry<String, Product> kV : hasMap.entrySet()) {
            String pr = "Имя: %s, значение: %s \n";
            System.out.printf(pr, kV.getKey(), kV.getValue());
        }
    }

    /**
     * Выводит ключи коллекции-отображения.
     * @param hasMap содержит коллекцию
     */
    static void printNames(final Map<String, Product> hasMap) {
        System.out.println("Имена:");
        for (String name : hasMap.keySet()) {
            System.out.print(name + "; ");
        }
        System.out.println();
    }

    /**
     * Выводит значения коллекции-отображения.
     * @param hasMap содержит коллекцию
     */
    static void printProducts(final Map<String, Product> hasMap) {
        System.out.println("Значения:");
        for (Product product : hasMap.values()) {
            System.out.println(product);
        }
    }
}

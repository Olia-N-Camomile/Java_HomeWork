package com.company;

/**
 *Класс Product предназначен для описания игрушек.
 * Поле fullName хранит полное наименование игрушки
 */
public class Product {
        /**Поле fullName хранит полное наименование игрушки.*/
        private final String fullName;
        /**Поле kode содержит артикул игрушки.*/
        private final String kode;
        /**Поле weight содержит вес игрушки.*/
        private final double weight;
        /**
         *К класса Product существует единстенный конструктор.
         * @param n соответствует полному наименованию,
         * @param k соответствует артикулу,
         * @param w соответствует весу
         */
        Product(final String n, final String k, final double w) {
                fullName = n;
                kode = k;
                weight = w;
        }
        /**
         * Переопределенная процедура toString.
         * Возвращает полное наименование и
         * все характеристики игрушки: артикул и вес.
         */
        @Override
        final public String toString() {
                return fullName + " артикул = " + kode + " вес = " + weight;
        }
}

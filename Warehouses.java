import java.util.List;

public class Warehouses {
    String name;
    int id, capacity, busy;

    public Warehouses(String name, int capacity) {
        this.name=name; this.capacity=capacity;}
    public Warehouses(int id, String name, int capacity) {
        this.id=id; this.name=name; this.capacity=capacity;}
    public Warehouses(int id, String name, int capacity, int busy) {
        this.id=id; this.name=name; this.capacity=capacity; this.busy=busy;}

        public static void printList(List<Warehouses> list) {
            for (Warehouses warehouse : list) {
                System.out.println(warehouse.id + " "+warehouse.name+" (вместимость = "+
                        warehouse.capacity+" пар"+ (warehouse.busy==0 ? ")" : " заполненность = "+warehouse.busy+")"));
            }
        }
}

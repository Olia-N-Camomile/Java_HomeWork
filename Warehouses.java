
import java.util.List;

public class Warehouses {
    private String name;
    private int id, capacity;
    int busy;
    public Warehouses() {}
    public Warehouses(String name, int capacity) {
        this.name=name; this.capacity=capacity;}
    public Warehouses(int id, String name, int capacity) {
        this.id=id; this.name=name; this.capacity=capacity;}
    public Warehouses(int id, String name, int capacity, int busy) {
        this.id=id; this.name=name; this.capacity=capacity; this.busy=busy;}
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public static void printList(List<Warehouses> list) {
        for (Warehouses warehouse : list) {
            System.out.println(warehouse.id + " "+warehouse.name+" (вместимость = "+
                    warehouse.capacity+" пар"+ (warehouse.busy==0 ? ")" : " заполненность = "+warehouse.busy+")"));
        }
    }

}

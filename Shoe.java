import java.util.List;

public class Shoe {
    String code;
    String name, color, material;
    float size;
    public Shoe(String code, String name, String color, String material, float size)
    { this.code=code; this.name=name; this.color=color;
        this.material=material; this.size=size;
    }
    public Shoe(String code) { this.code=code;}

    public static void printList(List<Shoe> list) {
        for (Shoe shoe : list) {
            System.out.println(shoe.name+" - "+shoe.code+" (размер: "+shoe.size+", "+shoe.color+", "+shoe.material+")");
        }
    }

    @Override
    public String toString() {
        return this.code.toString();
    }

}

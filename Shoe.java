import java.util.List;

public class Shoe {
    private String code, name, color, material;
    private float size;
    public Shoe(String code, String name, String color, String material, float size)
    { this.code=code; this.name=name; this.color=color;
        this.material=material; this.size=size;
    }
    public Shoe(String code) { this.code=code;}
    public Shoe() {}
    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    public String getColor() {
        return color;
    }
    public float getSize() {
        return size;
    }
    public String getMaterial() {
        return material;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setMaterial(String material) {
        this.material = material;
    }
    public void setSize(float size) {
        this.size = size;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public static void printList(List<Shoe> list) {
        for (Shoe shoe : list) {
            System.out.println(shoe.name+" - "+shoe.code+" (размер: "+shoe.size+", "+shoe.color+", "+shoe.material+")");
        }
    }



    @Override
    public String toString() {
        return this.code;
    }

}

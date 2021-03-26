
import java.util.Map;

public class Consignment {
    Warehouses warehouse;
    Map<Shoe, Integer> shoeMap;
    public Consignment(Map<Shoe, Integer> shoeMap, Warehouses warehouse)
    {
        this.shoeMap=shoeMap; this.warehouse=warehouse;
    }

}

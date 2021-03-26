
import java.util.*;

public class Main {
    public static void main(final String[] args) {
        Scanner scanner=new Scanner(System.in);
        WarehouseDAO warehouseDAO = new WarehouseDAO();
        System.out.println("Для создания таблиц введите 0");
        System.out.println("Для заполнения списка складов и остатка товаров введите 1");
        System.out.println("Для записи сведений о новой обуви введите 2");
        System.out.println("Для вывода списка складов введите 3");
        System.out.println("Для вывода списка обуви введите 4");
        System.out.println("Для вывода остатков обуви введите 5");
        System.out.println("Для оприходования на склад партии обуви введите +");
        System.out.println("Для списания партии обуви со склада введите -");
        System.out.println("Для выхода введите любые другие символы");
        String n=scanner.nextLine();
        switch (n) {
            case "0" -> {
                boolean rez = warehouseDAO.createTable();
                if (rez) {
                    ShoeDAO shoeDAO = new ShoeDAO();
                    rez = shoeDAO.createTable();
                }
                if (rez) {
                    ConsignmentDAO consignmentDAO = new ConsignmentDAO();
                    consignmentDAO.createTable();
                }
            }
            case "1" -> {
                List<Warehouses> listW = new ArrayList<>();
                listW.add(new Warehouses("Здание1 блок 1", 500));
                listW.add(new Warehouses("Здание1 блок 2", 700));
                listW.add(new Warehouses("Здание2", 10000));
                listW.add(new Warehouses("Здание3 этаж1 помещение1", 500));
                listW.add(new Warehouses("Здание3 этаж1 помещение2", 500));
                listW.add(new Warehouses("Здание3 этаж1 помещение3", 100));
                listW.add(new Warehouses("Здание3 этаж2 помещение1", 400));
                listW.add(new Warehouses("Здание3 этаж2 помещение2", 700));
                boolean rez = warehouseDAO.addNewEntry(listW);
                if (! rez) System.exit(0);
                List<Shoe> shoeList = new ArrayList<>();
                shoeList.add(new Shoe("БЖ36СК", "Ботинки женские", "синие", "кожа",	36));
                shoeList.add(new Shoe("БЖ37СК", "Ботинки женские", "синие", "кожа",	37));
                shoeList.add(new Shoe("БЖ38СК", "Ботинки женские", "синие", "кожа",	38));
                shoeList.add(new Shoe("ТЖ37ЧЗ", "Туфли женские", "черные", "замша",	37));
                shoeList.add(new Shoe("ТЖ39ЧЗ", "Туфли женские", "черные", "замша",	39));
                shoeList.add(new Shoe("ТМ41ЧК", "Туфли мужские", "черные", "кожа",	41));
                shoeList.add(new Shoe("ТМ42ЧК", "Туфли мужские", "черные", "кожа",	42));
                shoeList.add(new Shoe("ТМ43ЧК", "Туфли мужские", "черные", "кожа",	43));
                shoeList.add(new Shoe("КрМС42КТ", "Кроссовки мужские", "серо-желтые", "кожа, текстиль",42));
                shoeList.add(new Shoe("КрЖ39Т", "Кроссовки женские", "цветные", "текстиль",42));
                ShoeDAO shoeDAO = new ShoeDAO();
                rez = shoeDAO.addNewEntry(shoeList);
                if (! rez) System.exit(0);
                ConsignmentDAO consignmentDAO = new ConsignmentDAO();
                Map<Shoe, Integer> shoeMap = new HashMap<>();
                for (Shoe shoe : shoeList) {
                    shoeMap.put(shoe, 10);
                }
                List<Warehouses> warehousesList = warehouseDAO.findAll("");
                for (Warehouses warehouse : warehousesList) {
                    if (!consignmentDAO.addNewEntry(new Consignment(shoeMap, warehouse))) break;
                }

           }
            case "2" -> {
                ShoeDAO shoeDAO = new ShoeDAO();
                List<Shoe> listShoe = new ArrayList<>();
                String code="", allCode = "";
                boolean theEnd = false;
                while (!theEnd) {
                    System.out.println("Введите артикул, для завершения введите end");
                    if (!code.isEmpty()) {scanner.nextLine();}
                    code = scanner.nextLine();
                    if (code.equals("end")) theEnd = true;
                    else if (code.isEmpty()) continue;
                    else if (allCode.contains(code)) {
                        System.out.println("Обувь с таким артикулом уже была добавлена ранее!");
                        code="";
                    }
                    else if (shoeDAO.findEntryByCode(code) != null) {
                        System.out.println("Обувь с таким артикулом уже была добавлена ранее!");
                        code="";
                    }
                    else {
                        System.out.println("Введите наименование:");
                        String name = scanner.nextLine();
                        System.out.println("Введите цвет:");
                        String color = scanner.nextLine();
                        System.out.println("Введите материал:");
                        String material = scanner.nextLine();
                        System.out.println("Введите размер:");
                        if (scanner.hasNextFloat()) {
                            float size = scanner.nextFloat();
                            listShoe.add(new Shoe(code, name, color, material, size));
                            allCode = allCode + "!!!" + code + "!!!";
                        }
                    }
                }
                shoeDAO.addNewEntry(listShoe);
            }
            case "3" -> Warehouses.printList(warehouseDAO.findAllWithBusy());
            case "4" -> {
                ShoeDAO shoeDAO = new ShoeDAO();
                Shoe.printList(shoeDAO.findAll(""));
            }
            case "5" -> {
                ConsignmentDAO consignmentDAO = new ConsignmentDAO();
                consignmentDAO.findAll("");
            }
            default -> {
                if (n.equals("+") || n.equals("-")) {
                    Map<Shoe, Integer> shoeMap = new HashMap<>();
                    System.out.println("Вводите поочередно артикул и количество обуви.");
                    boolean theEnd = false;
                    int allQ = 0;
                    String code="", allCode = "";
                    while (!theEnd) {
                        System.out.println("Введите артикул: (для завершения ввода партии, введите end)");
                        if (!code.isEmpty()) {scanner.nextLine();}
                        code = scanner.nextLine();
                        if (code.equals("end")) theEnd = true;
                        else if (allCode.contains(code)) {
                            System.out.println("Обувь с таким артикулом уже была добавлена ранее!");
                            code="";
                        }
                        else {
                            System.out.println("Введите количество обуви:");
                            if (scanner.hasNextInt()) {
                                int q = scanner.nextInt();
                                allQ += q;
                                if (q != 0) {
                                    shoeMap.put(new Shoe(code), q);
                                    allCode = allCode + "!!!" + code + "!!!";
                                }
                            }
                        }
                    }
                    if (shoeMap.size() == 0) {
                        System.out.println("Пустая партия!");
                        System.exit(0);
                    }
                    Warehouses warehouse = null;
                    int id;
                    System.out.println("Введите номер склада - целое число, ");
                    System.out.println("для выхода введите любые другие символы");
                    boolean isOk=false;
                    if (scanner.hasNextInt()) {
                        id = scanner.nextInt();
                        warehouse = warehouseDAO.findEntryAndBusyById(id);
                        if (warehouse == null) {
                            System.out.println("Не найден склад с таким номером!");
                        } else if (n.equals("+") && warehouse.busy + allQ > warehouse.capacity) {
                            System.out.println("На складе " + warehouse.name + " осталось место только для " +
                                    +(warehouse.capacity - warehouse.busy) + " пар!");
                        } else {
                            isOk = true;
                        }
                    }
                    if (isOk) {
                        Consignment consignment = new Consignment(shoeMap, warehouse);
                        movements(consignment, n);
                    }
                }
            }
        }
    }

    public static void movements(Consignment consignment, String direction)
    {
        String errorText="";
        String shoeCode="(";
        Set<Shoe> shoeSet = consignment.shoeMap.keySet();
        for (Shoe shoe : shoeSet) {
            shoeCode=shoeCode+(shoeCode.equals("(") ? "" : ",")+"'"+shoe.code+"'";
        }
        shoeCode+=")";
        String query_Text_Leftover = "select * from leftover where id_location=? and code_shoe in "+shoeCode;
        /*
        String query_Text_Leftover = "select lft.code_shoe, lft.id_location, lft.quantity, shoes.* " +
                "from (select code_shoe, id_location, quantity " +
                "from leftover where id_location=? and code_shoe in "+shoeCode+") as lft " +
                "inner join shoes on lft.code_shoe=shoes.vendor_code";
        */
        List<String> listQT = new ArrayList<>();
        String query_Text_Movement = "";
        ConsignmentDAO consignmentDAO = new ConsignmentDAO();
        Consignment consignmentLeftover = consignmentDAO.findEntryByNumber(consignment.warehouse, query_Text_Leftover);
        if (consignmentLeftover==null) { System.exit(0);}
        Set<Shoe> shoeSetLeftover = consignmentLeftover.shoeMap.keySet();
        for (Shoe shoe : shoeSet) {
            int q_Leftover = 0;
            boolean isLeftover=false;
            for (Shoe shoeLeftover : shoeSetLeftover) {
                if (shoeLeftover.code.equals(shoe.code)) {
                    isLeftover=consignmentLeftover.shoeMap.get(shoeLeftover) != null;
                    q_Leftover=isLeftover ? consignmentLeftover.shoeMap.get(shoeLeftover) : 0;
                    break;
                }
            }
            int q = consignment.shoeMap.get(shoe);
            if ((direction.equals("-") && q>q_Leftover) || (direction.equals("+") && -q>q_Leftover)) {
                errorText="Для списания не хватило "+(q-q_Leftover)+" пар с артикулом: "+shoe.code;
                break;
            }
            else if ((direction.equals("-") && q==q_Leftover) || (direction.equals("+") && -q==q_Leftover)) {
                query_Text_Movement= "delete from leftover WHERE id_location="+
                        +consignment.warehouse.id+" AND code_shoe="+"'"+shoe.code+"'";
            }
            else if (!isLeftover) {
                query_Text_Movement= "insert into leftover(id_location, code_shoe, quantity) values("+
                        +consignment.warehouse.id+", '"+shoe.code+"', "+q+")";
            }
            else {
                int q_rez = direction.equals("-") ? q_Leftover-q : q_Leftover+q;
                query_Text_Movement= "UPDATE leftover SET quantity=" + q_rez +" WHERE id_location="+
                        +consignment.warehouse.id+" AND code_shoe="+"'"+shoe.code+"'";
            }
            listQT.add(query_Text_Movement);
        }
        if (errorText.isEmpty()) consignmentDAO.updateEntry(listQT);
        else  System.out.println(errorText);
    }

}

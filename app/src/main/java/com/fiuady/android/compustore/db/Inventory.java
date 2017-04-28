package com.fiuady.android.compustore.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


import com.fiuady.android.compustore.db.Sales;
import com.fiuady.android.compustore.db.InventoryDbSchema.*;

class CustomersCursor extends CursorWrapper {
    public CustomersCursor(Cursor cursor) {
        super(cursor);
    }

    private int id;
    private String first_name = "";
    private String last_name = "";
    private String address = "";
    private String phone1 = "";
    private String phone2 = "";
    private String phone3 = "";
    private String e_mail = "";

    public Customers getCustomer() {
        Cursor cursor = getWrappedCursor();
        id = cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.id));
        first_name = cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.first_name));
        last_name = cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.last_name));
        address = cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.address));
        phone1 = cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.Phone1));
        //if (phone1 == null){phone1="";}
        phone2 = cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.Phone2));
        //if (phone2 == null){phone2="";}
        phone3 = cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.Phone3));
        // if (phone3 == null){phone3="";}
        e_mail = cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.e_mail));
        // if (e_mail == null){e_mail="";}

        return new Customers(id, first_name, last_name, address, phone1, phone2, phone3, e_mail);
    }
}

class OrdersCursor extends CursorWrapper {
    public OrdersCursor(Cursor cursor) {
        super(cursor);
    }


    private int id;
    private int status_id;
    private int customer_id;
    private String date;
    private String change_log;


    public Order getOrders() {
        Cursor cursor = getWrappedCursor();
        id = cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrdersTable.Columns.id));
        status_id = cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrdersTable.Columns.status_id));
        customer_id = cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrdersTable.Columns.customer_id));
        date = cursor.getString(cursor.getColumnIndex(InventoryDbSchema.OrdersTable.Columns.date));
        change_log = cursor.getString(cursor.getColumnIndex(InventoryDbSchema.OrdersTable.Columns.change_log));
        return new Order(id, status_id, customer_id, date, change_log);
    }
}

class OrdersAssembliesCursor extends CursorWrapper {
    public OrdersAssembliesCursor(Cursor cursor) {
        super(cursor);
    }

    private int id;
    private int assembly_id;
    private int qty;

    public Order_assemblies getOrderAsemblie() {
        Cursor cursor = getWrappedCursor();
        id = cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrderAssembliesTable.Columns.id));
        assembly_id = cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrderAssembliesTable.Columns.assembly_id));
        qty = cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrderAssembliesTable.Columns.qty));
        return new Order_assemblies(id,assembly_id,qty);
    }
}

class Order_statusCursor extends CursorWrapper {
    public Order_statusCursor(Cursor cursor) {
        super(cursor);
    }

    private int id;
    private int description;
    private int editable;
    private String previous;
    private String next;


    public Order_status getOrders() {
        Cursor cursor = getWrappedCursor();
        id = cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrdersStatusTable.Columns.id));
        description = cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrdersStatusTable.Columns.description));
        editable = cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrdersStatusTable.Columns.editable));
        previous = cursor.getString(cursor.getColumnIndex(InventoryDbSchema.OrdersStatusTable.Columns.previous));
        next = cursor.getString(cursor.getColumnIndex(InventoryDbSchema.OrdersStatusTable.Columns.next));
        return new Order_status(id, description, editable, previous, next);
    }
}

class ProductCursor extends CursorWrapper {
    public ProductCursor(Cursor cursor) {
        super(cursor);
    }


    public Products getProduct() {

        Cursor cursor = getWrappedCursor();
        return new Products(cursor.getInt(cursor.getColumnIndex((InventoryDbSchema.ProductTable.Columns.ID))), cursor.getInt(cursor.getColumnIndex((InventoryDbSchema.ProductTable.Columns.CATEGORY_ID))),
                cursor.getString(cursor.getColumnIndex((InventoryDbSchema.ProductTable.Columns.DESCRIPTION))), cursor.getInt(cursor.getColumnIndex((InventoryDbSchema.ProductTable.Columns.PRICE))), cursor.getInt(cursor.getColumnIndex((InventoryDbSchema.ProductTable.Columns.QUANTITY))));

    }

}

class SalesCursor extends CursorWrapper {
    public SalesCursor(Cursor cursor) {
        super(cursor);
    }


    public Sales getSales() {

        Cursor cursor = getWrappedCursor();
        return new Sales(cursor.getString(cursor.getColumnIndex((SalesTable.Columns.date))), cursor.getInt(cursor.getColumnIndex((SalesTable.Columns.total_cost))) );

    }

}

class Order_SalesCursor extends CursorWrapper {
    public Order_SalesCursor(Cursor cursor) {
        super(cursor);
    }

    public Order_Sales getOrderSales() {

        Cursor cursor = getWrappedCursor();
        return new Order_Sales(cursor.getInt(cursor.getColumnIndex((OrderSalesTable.Columns.id)))  ,cursor.getString(cursor.getColumnIndex((OrderSalesTable.Columns.first_name))), cursor.getString(cursor.getColumnIndex((OrderSalesTable.Columns.last_name))),
                cursor.getString(cursor.getColumnIndex((OrderSalesTable.Columns.date))), cursor.getString(cursor.getColumnIndex((OrderSalesTable.Columns.status))), cursor.getInt(cursor.getColumnIndex((OrderSalesTable.Columns.cost))));

    }

}



class CategoryCursor extends CursorWrapper {
    public CategoryCursor(Cursor cursor) {
        super(cursor);
    }

    public Category getCategory() {
        Cursor cursor = getWrappedCursor();
        return new Category(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.CategoriesTable.Columns.ID)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CategoriesTable.Columns.DESCRIPTION)));
    }
}

class AssembliesCursor extends CursorWrapper {
    public AssembliesCursor(Cursor cursor) {
        super(cursor);
    }

    public Assemblies getAssembly() {
        Cursor cursor = getWrappedCursor();
        return new Assemblies(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.AssembliesTable.Columns.ID)),
                cursor.getString(cursor.getColumnIndex(AssembliesTable.Columns.DESCRIPTION)));
    }
}

public final class Inventory {

    private InventoryHelper inventoryHelper;


    private SQLiteDatabase db;

    public Inventory(Context context) {
        //InventoryHelper.backupDatabaseFile(context);
        inventoryHelper = new InventoryHelper(context);
        db = inventoryHelper.getWritableDatabase();
    }


    public List<Products> getallProducts() {
        List<Products> list = new ArrayList<Products>();
        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);
        ProductCursor cursor = new ProductCursor((db.rawQuery("SELECT * FROM products ORDER BY description", null)));
        while (cursor.moveToNext()) {
            //list.add(new Category(cursor.getInt(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.ID))),
            //   cursor.getString(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.DESCRIPTION)))));
            list.add((cursor.getProduct()));  // metodo wrappcursor
        }
        cursor.close();
        return list;
    }

    public List<Products> getallProductsineverycategory(String description) {
        List<Products> list = new ArrayList<Products>();


        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        ProductCursor cursor = new ProductCursor((db.rawQuery("SELECT * " +
                "FROM products p " +
                "WHERE p.description LIKE'" + description + "%' " +
                " ORDER BY p.description", null)));

        while (cursor.moveToNext()) {

            //list.add(new Category(cursor.getInt(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.ID))),
            //   cursor.getString(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.DESCRIPTION)))));

            list.add((cursor.getProduct()));  // metodo wrappcursor

        }
        cursor.close();
        return list;
    }


    public List<Products> getoneProducts(String categroydescription, String productdescription) {
        List<Products> list = new ArrayList<Products>();


        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        ProductCursor cursor = new ProductCursor((db.rawQuery(" SELECT p.id, p.category_id, p.description, p.price, p.qty\n" +
                "FROM products p " +
                "INNER JOIN product_categories c ON (p.category_id = c.id) " +
                "WHERE c.description LIKE '" + categroydescription + "'" +
                "AND p.description LIKE '" + productdescription + "%" + "' " +
                " ORDER BY p.description", null)));

        while (cursor.moveToNext()) {

            //list.add(new Category(cursor.getInt(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.ID))),
            //   cursor.getString(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.DESCRIPTION)))));

            list.add((cursor.getProduct()));  // metodo wrappcursor

        }
        cursor.close();
        return list;
    }


    public List<Products> getonecategoryproduct(String categroy_description) {
        List<Products> list = new ArrayList<Products>();


        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        ProductCursor cursor = new ProductCursor((db.rawQuery("SELECT p.id, p.category_id, p.description, p.price, p.qty " +
                "FROM products p " +
                "INNER JOIN product_categories c ON (p.category_id = c.id) " +
                "WHERE c.description LIKE '" + categroy_description + "' ORDER BY p.description", null)));

        while (cursor.moveToNext()) {

            //list.add(new Category(cursor.getInt(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.ID))),
            //   cursor.getString(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.DESCRIPTION)))));

            list.add((cursor.getProduct()));  // metodo wrappcursor

        }
        cursor.close();
        return list;
    }

    public int return_categroty_id(String descritpion) {

        Cursor cursor = db.rawQuery("select p.id from product_categories p " +
                "Where p.description = '" + descritpion + "'", new String[]{});

        cursor.moveToFirst();

        int test = cursor.getInt(0);

        //  int cat_id = 0;

        //  for (Category c : getAllCategories()) {
        //   if (descritpion == c.getDescription()) {
        //    cat_id = c.getId();
        // }

        //  }

        return test;
    }

    public void addProduct(int id, String cat_id, String descritpion, String price, String qty) {
        int mx = -1;
        Cursor cursor = db.rawQuery("SELECT max(ID) from products", new String[]{});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                mx = cursor.getInt(0);
            }
            cursor.close();
        } else {
            mx = -1;
        }
        if (mx != -1) {
            //ContentValues values = new ContentValues();
            //values.put(CategoriesTable.Columns.DESCRIPTION, category.getDescription());

            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES (" + String.valueOf(mx + 1) + "," + cat_id + ",'" + descritpion + "'," + price + "," + qty + ");");
        }
    }

    public boolean DeleteProduct(String id) {
        Cursor cursor = db.rawQuery("SELECT p.id from " +
                "products p INNER JOIN assembly_products ap ON (p.id = ap.product_id) " +
                "GROUP BY p.id HAVING p.id = " + id + ";", new String[]{});
        if (cursor == null) {
            db.execSQL("DELETE from products WHERE id = " + id + ";");
            return true;
        } else if (!cursor.moveToFirst()) {
            db.execSQL("DELETE from products WHERE id = " + id + ";");
            cursor.close();
            return true;

        } else {
            cursor.close();
            return false;
        }

    }

    public void add_stock(String id, String qty) {

        ContentValues values = new ContentValues();

        values.put(ProductTable.Columns.QUANTITY, qty);
        db.update(ProductTable.Name,
                values,
                ProductTable.Columns.ID + "= ?",
                new String[]{id}
        );

        //  db.rawQuery("UPDATE products " +
        //   "SET qty = "+  qty + "" +
        //   " WHERE id = "+ id  , null);
    }

    public void Update_product(String id, String category_id, String description, String price, String qty) {

        ContentValues values = new ContentValues();

        //values.put(ProductTable.Columns.QUANTITY, qty);
        values.put(ProductTable.Columns.DESCRIPTION, description);
        values.put(ProductTable.Columns.CATEGORY_ID, category_id);
        values.put(ProductTable.Columns.PRICE, price);
        db.update(ProductTable.Name,
                values,
                ProductTable.Columns.ID + "= ?",
                new String[]{id}
        );

        //  db.rawQuery("UPDATE products " +
        //   "SET qty = "+  qty + "" +
        //   " WHERE id = "+ id  , null);

    }

    public boolean check_product(String description) {
        Cursor cursor = db.rawQuery("SELECT p.id from " +
                "products p " +
                " GROUP BY p.id HAVING p.description = '" + description + "';", new String[]{});
        if (cursor == null) {

            return true;
        } else if (!cursor.moveToFirst()) {

            cursor.close();
            return true;

        } else {
            cursor.close();
            return false;
        }
    }


    public List<Category> getAllCategories() {
        ArrayList<Category> list = new ArrayList<Category>();
        CategoryCursor cursor = new CategoryCursor(db.rawQuery("SELECT * FROM product_categories ORDER BY description", null));
        while (cursor.moveToNext()) {
            list.add(cursor.getCategory());
        }
        cursor.close();
        return list;
    }

    public List<Products> getmisssingProducts()
    {

        List<Products> list = new ArrayList<Products>();


        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        ProductCursor cursor = new ProductCursor((db.rawQuery("select p.id,p.category_id,p.description,p.price ,(sum(oa.qty * ap.qty)- p.qty) AS qty " +
                "from order_assemblies oa " +
                "inner join orders o on (oa.id = o.id)" +
                "inner join assemblies a on (oa.assembly_id = a.id) " +
                "inner join assembly_products ap on (a.id = ap.id) " +
                "inner join products p on (ap.product_id = p.id) " +
                "WHERE o.status_id = \"4\" or o.status_id=\"3\" or o.status_id = \"2\"" +
                "group by ap.product_id HAVING (sum(oa.qty * ap.qty)- p.qty) > 0 " +
                "order by qty desc", null)));

        while (cursor.moveToNext()) {

            list.add((cursor.getProduct()));  // metodo wrappcursor

        }
        cursor.close();
        return list;
    }

    public List<Sales> getSales()
    {

        List<Sales> list = new ArrayList<Sales>();

        SalesCursor cursor = new SalesCursor((db.rawQuery("SELECT o.date, SUM(oa.qty * ap.qty * p.price) AS total_cost " +
                "FROM orders o " +
                "INNER JOIN order_assemblies oa ON (o.id = oa.id) " +
                "INNER JOIN assemblies a ON (oa.assembly_id = a.id) " +
                "INNER JOIN assembly_products ap ON (a.id = ap.id) " +
                "INNER JOIN products p ON (ap.product_id = p.id) " +
                "WHERE o.status_id = \"4\" or o.status_id=\"3\" or o.status_id = \"2\"" +
                "GROUP BY oa.id,o.date " +
                "ORDER BY total_cost DESC", null)));

        while (cursor.moveToNext()) {

            list.add((cursor.getSales()));

        }
        cursor.close();
        return list;
    }

    public List<Order_Sales> getOrderSales(String date_value)
    {

        List<Order_Sales> list = new ArrayList<Order_Sales>();

        Order_SalesCursor cursor = new Order_SalesCursor((db.rawQuery("SELECT o.id, c.first_name, c.last_name, o.date, ords.description, SUM(oa.qty * ap.qty * p.price) AS total_cost\n" +
                "FROM orders o\n" +
                "INNER JOIN order_status ords ON (o.status_id = ords.id)\n" +
                "INNER JOIN customers c ON (o.customer_id = c.id)\n" +
                "INNER JOIN order_assemblies oa ON (o.id = oa.id)\n" +
                "INNER JOIN assemblies a ON (oa.assembly_id = a.id)\n" +
                "INNER JOIN assembly_products ap ON (a.id = ap.id)\n" +
                "INNER JOIN products p ON (ap.product_id = p.id)\n" +
                "WHERE (o.status_id = \"4\" or o.status_id=\"3\" or o.status_id = \"2\") AND o.date like '%" + date_value + "%'" +
                "GROUP BY oa.id,o.date\n" +
                "ORDER BY total_cost DESC", null)));

        while (cursor.moveToNext()) {

            list.add((cursor.getOrderSales()));
        }
        cursor.close();
        return list;
    }




    public List<Assemblies> getAllAssemblies() {
        ArrayList<Assemblies> list = new ArrayList<Assemblies>();
        AssembliesCursor cursor = new AssembliesCursor(db.rawQuery("SELECT * FROM assemblies ORDER BY description;", null));
        while (cursor.moveToNext()) {
            list.add(cursor.getAssembly());
        }
        cursor.close();
        return list;
    }

    public List<Products> getAssemblyProducts(Assemblies assembly) {
        List<Products> list = new ArrayList<Products>();
        ProductCursor cursor = new ProductCursor(db.rawQuery("SELECT p.id, p.category_id, p.description, p.price, ap.qty FROM assemblies a " +
                "INNER JOIN assembly_products ap ON (a.id = ap.id) " +
                "INNER JOIN products p ON (ap.product_id=p.id) " +
                "WHERE a.id=" + String.valueOf(assembly.getId()) +
                " ORDER BY p.description;", null));
        while (cursor.moveToNext()) {
            list.add(cursor.getProduct());
        }
        return list;
    }

    public void updateCategory(Category category) {
        ContentValues values = new ContentValues();

        values.put(CategoriesTable.Columns.DESCRIPTION, category.getDescription());
        db.update(CategoriesTable.Name,
                values,
                CategoriesTable.Columns.ID + "= ?",
                new String[]{Integer.toString(category.getId())}
        );

    }

    public void updateProductInAssembly(Assemblies assembly, Products product, int qty) {
        db.execSQL("UPDATE assembly_products SET qty = " + String.valueOf(qty) +
                " WHERE (id=" + String.valueOf(assembly.getId()) +
                ") AND (product_id=" + String.valueOf(product.getId()) +
                ");");
    }

    public void deleteProductInAssembly(Assemblies assembly, Products product) {
        db.execSQL("DELETE FROM assembly_products WHERE (product_id=" + String.valueOf(product.getId()) +
                ") AND (id=" + String.valueOf(assembly.getId()) +
                ");");
    }

    public boolean updateAssembly(Assemblies assembly, String description) {
        if (assembly.getDescription().equals(description)) {
            //db.execSQL("");
            return true;
        }
        Cursor cursor = db.rawQuery("SELECT id FROM assemblies  where description = '" +
                description +
                "';", new String[]{});
        if (!cursor.moveToFirst()) {
            db.execSQL("UPDATE assemblies  SET description = '" +
                    description +
                    "' WHERE id=" + String.valueOf(assembly.getId()) +
                    ";");
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
        //return true;
    }

    public void addCategory(Category category) {
        int mx = -1;
        Cursor cursor = db.rawQuery("SELECT max(ID) from product_categories", new String[]{});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                mx = cursor.getInt(0);
            }
            cursor.close();
        } else {
            mx = -1;
        }
        if (mx != -1) {
            //ContentValues values = new ContentValues();
            //values.put(CategoriesTable.Columns.DESCRIPTION, category.getDescription());

            db.execSQL("INSERT INTO product_categories (id, description) VALUES (" + String.valueOf(mx + 1) + ", '" + category.getDescription() + "');");
        }
    }


    public boolean addProductInAssembly(Products product, Assemblies assembly) {
        Cursor cursor = db.rawQuery("SELECT id FROM assembly_products ap WHERE ap.id=" + String.valueOf(assembly.getId()) +
                " AND ap.product_id=" + String.valueOf(product.getId()) +
                ";", new String[]{});
        if (!cursor.moveToFirst()) {
            db.execSQL("INSERT INTO assembly_products (id, product_id, qty) VALUES (" + String.valueOf(assembly.getId()) +
                    ", " + String.valueOf(product.getId()) +
                    ", 1);");
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean addProductInAssemblyWithQty(Products product, Assemblies assembly) {
        Cursor cursor = db.rawQuery("SELECT id FROM assembly_products ap WHERE ap.id=" + String.valueOf(assembly.getId()) +
                " AND ap.product_id=" + String.valueOf(product.getId()) +
                ";", new String[]{});
        if (!cursor.moveToFirst()) {
            db.execSQL("INSERT INTO assembly_products (id, product_id, qty) VALUES (" + String.valueOf(assembly.getId()) +
                    ", " + String.valueOf(product.getId()) +
                    ", " + String.valueOf(product.getQty()) +
                    ");");
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean addAssembly(Assemblies assemblies) {
        int mx = -1;
        Cursor cursor = db.rawQuery("SELECT max(ID) from assemblies;", new String[]{});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                mx = cursor.getInt(0);
            }
            cursor.close();
        } else {
            mx = -1;
        }
        if (mx != -1) { // Editado
            Cursor cursor1 = db.rawQuery("SELECT id FROM assemblies  where description = '" +
                    assemblies.getDescription() +
                    "';", new String[]{});
            if (!cursor1.moveToFirst()) {
                db.execSQL("INSERT INTO assemblies (id, description) VALUES (" + String.valueOf(mx + 1) +
                        ", '" + assemblies.getDescription() +
                        "');");
                cursor1.close();
                return true;
            } else {
                cursor1.close();
                return false;
            }

        }
        return false;

    }

    public void addAuxAssembly(Assemblies assemblies) {
        db.execSQL("INSERT INTO assemblies (id, description) VALUES (" + String.valueOf(assemblies.getId()) +
                ", '" + assemblies.getDescription() +
                "');");
    }

    public void transferProductsToDefinitiveAssembly() {
        int mx = -1;
        Cursor cursor = db.rawQuery("SELECT max(ID) from assemblies;", new String[]{});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                mx = cursor.getInt(0);
            }
            cursor.close();
        } else {
            mx = -1;
        }
        if (mx != -1) {
            db.execSQL("UPDATE assembly_products SET id = " + String.valueOf(mx) +
                    " WHERE id=9999");
        }
    }


    public void transferProductsToAnotherAssembly(Assemblies assemblies) {
        db.execSQL("DELETE From assembly_products WHERE id=" + String.valueOf(assemblies.getId()) +
                ";");
        db.execSQL("UPDATE assembly_products SET id = " + String.valueOf(assemblies.getId()) +
                " WHERE id=9999");
    }


    public void deleteAux() {
        db.execSQL("DELETE FROM assemblies where id=9999");
    }


    public void emptyNDeleteAux() {
        db.execSQL("DELETE From assembly_products WHERE id=9999;");
        db.execSQL("DELETE FROM assemblies where id=9999;");
    }

    public boolean deleteCategory(Category category) {
        Cursor cursor = db.rawQuery("SELECT pc.id from " +
                "product_categories pc INNER JOIN products p ON (pc.id = p.category_id) " +
                "GROUP BY pc.id HAVING pc.id = " + String.valueOf(category.getId()) + ";", new String[]{});
        if (cursor == null) {
            db.execSQL("DELETE from product_categories WHERE id = " + String.valueOf(category.getId()) + ";");
            return true;
        } else if (!cursor.moveToFirst()) {
            db.execSQL("DELETE from product_categories WHERE id = " + String.valueOf(category.getId()) + ";");
            cursor.close();
            return true;

        } else {
            cursor.close();
            return false;
        }
    }

    public boolean deleteAssemblies(Assemblies assemblies) {
        Cursor cursor = db.rawQuery("SELECT a.id FROM assemblies a " +
                "INNER JOIN order_assemblies oa ON (a.id = oa.assembly_id) " +
                "GROUP BY a.id HAVING a.id = " + String.valueOf(assemblies.getId()) +
                ";", new String[]{});
        if (cursor == null) {
            db.execSQL("DELETE from assemblies WHERE id = " + String.valueOf(assemblies.getId()) + ";");
            return true;
        } else if (!cursor.moveToFirst()) {
            db.execSQL("DELETE From assembly_products WHERE id = " + String.valueOf(assemblies.getId()) +
                    ";");
            db.execSQL("DELETE from assemblies WHERE id = " + String.valueOf(assemblies.getId()) + ";");
            cursor.close();
            return true;

        } else {
            cursor.close();
            return false;
        }
    }

    public List<Customers> getAllCustomers() {
        ArrayList<Customers> list = new ArrayList<Customers>();
        CustomersCursor cursor = new CustomersCursor(db.rawQuery("SELECT * FROM customers ORDER BY last_name", null));// ORDER BY last_name
        while (cursor.moveToNext()) {
            list.add(cursor.getCustomer());

        }
        cursor.close();
        return list;
    }

    public List<Customers> findby(String firstname, String lastname, String address, String phone, String email, String descrip) {
        ArrayList<Customers> list = new ArrayList<Customers>();
        if (descrip.length() == 0) {
            descrip = "holajeje.jpg"; //like \""+descrip + %\"
        }
        if (firstname.length() + lastname.length() + address.length() + phone.length() + email.length() == 0) {
            CustomersCursor cursor = new CustomersCursor(db.rawQuery("SELECT * FROM customers ORDER BY last_name", null));// ORDER BY last_name
            while (cursor.moveToNext()) {
                list.add(cursor.getCustomer());

            }
            cursor.close();
            return list;
        } else {
            String _firsname, _lastname, _address, _phones, _email;
            String or1 = "";
            String or2 = "";
            String or3 = "";
            String or4 = "";

            Boolean flag0 = false;
            Boolean flag1 = false;
            Boolean flag2 = false;
            Boolean flag3 = false;
            Boolean flag4 = false;

            if (firstname.length() > 0) {
                flag0 = true;
                _firsname = "first_name like \""+ descrip + "%\"";
            } else {
                flag0 = false;
                _firsname = "";
            }
            if (lastname.length() > 0) {
                flag1 = true;
                _lastname = " last_name like \"" + descrip + "%\"";
            } else {
                flag1 = false;
                _lastname = "";
            }
            if (address.length() > 0) {
                flag2 = true;
                _address = " address like \"" + descrip + "%\"";
            } else {
                flag2 = false;
                _address = "";
            }
            if (phone.length() > 0) {
                flag3 = true;
                _phones = " phone1 like \"" + descrip + "%\" or phone2 like \"" + descrip + "%\"  or phone3 like \"" + descrip + "%\"";
            } else {
                flag3 = false;
                _phones = "";
            }
            if (email.length() > 0) {
                flag4 = true;
                _email = " e_mail like \"" + descrip + "%\"";
            } else {
                flag4 = false;
                _email = "";
            }

            if (flag0 == true) {
                if (flag1 || flag2 || flag3 || flag4) {
                    or1 = "or";
                }
            }
            if (flag1 == true) {
                if (flag2 || flag3 || flag4) {
                    or2 = "or";
                }
            }
            if (flag2 == true) {
                if (flag3 || flag4) {
                    or3 = "or";
                }
            }
            if (flag3 == true) {
                if (flag4) {
                    or4 = "or";
                }
            }


            String aux = "SELECT * FROM customers WHERE " + _firsname + " " + or1 + " " + _lastname + " " + // this only get the last or
                    or2 + " " + _address + " " + or3 + " " + _phones + " " + or4 + " " + _email +" order by last_name";
            CustomersCursor cursor = new CustomersCursor(db.rawQuery(aux, null));
            while (cursor.moveToNext()) {
                list.add(cursor.getCustomer());

            }
            cursor.close();
            return list;


        }

    }

    public void addCustomer(String firstname, String lastname, String address, String phone1, String phone2, String phone3, String email) {
        ArrayList<Customers> list = new ArrayList<Customers>();
        CustomersCursor cursor = new CustomersCursor(db.rawQuery("SELECT * FROM customers ", null));// ORDER BY last_name
        while (cursor.moveToNext()) {
            list.add(cursor.getCustomer());

        }
        cursor.close();
        int id_prove = 0;
        for (Customers client : list) {
            if (client.getId() > id_prove) {
                id_prove = client.getId();
            }
        }
        id_prove = id_prove + 1;

        //db.execSQL("INSERT INTO product_categories (id, description) VALUES ("+String.valueOf(mx+1)+
        //      ", '"+category.getDescription()+"');");
        db.execSQL("INSERT INTO customers (id, first_name, last_name, address, phone1, phone2, phone3, e_mail)" +
                "VALUES (" + id_prove + ", '" + firstname + "', '" + lastname + "', '" + address + "', " + phone1 + ", " + phone2 + ", " + phone3 + ", " + email + ")");


    }

    public boolean deleteCustomer(Customers customer) {

        Cursor cursor = db.rawQuery("SELECT c.id from " +
                "customers c INNER JOIN orders o ON (c.id = o.customer_id) " +
                "GROUP BY c.id HAVING c.id = " + String.valueOf(customer.getId()) + ";", new String[]{});
        if (cursor == null) {
            db.execSQL("DELETE from customers WHERE id = " + String.valueOf(customer.getId()) + ";");
            return true;
        } else if (!cursor.moveToFirst()) {
            db.execSQL("DELETE from customers WHERE id = " + String.valueOf(customer.getId()) + ";");
            cursor.close();
            return true;

        } else {
            cursor.close();
            return false;
        }

    }

    public void updateCustomer(Customers customer) {


        db.execSQL("UPDATE customers SET  first_name = '" + customer.getFirst_name() +
                "' , last_name = '" + customer.getLast_name() +
                "' , address = '" + customer.getAddress() +
                "' , phone1 = " + customer.getPhone1() +
                " , phone2 = " + customer.getPhone2() +
                " , phone3 = " + customer.getPhone3() +
                " , e_mail = " + customer.getE_mail() +
                " WHERE id = " +
                String.valueOf(customer.getId()) + ";");


    }

    public boolean canudeletecustomer(Customers customer) {

        Cursor cursor = db.rawQuery("SELECT c.id from " +
                "customers c INNER JOIN orders o ON (c.id = o.customer_id) " +
                "GROUP BY c.id HAVING c.id = " + String.valueOf(customer.getId()) + ";", new String[]{});
        if (cursor == null) {

            return true;
        } else if (!cursor.moveToFirst()) {

            cursor.close();
            return true;

        } else {
            cursor.close();
            return false;
        }
    }

    //For orders
    public List<Order> getAllOrders() {
        ArrayList<Order> list = new ArrayList<Order>();
        OrdersCursor cursor = new OrdersCursor(db.rawQuery("SELECT * FROM orders ", null));// ORDER BY last_name
        while (cursor.moveToNext()) {
            list.add(cursor.getOrders());
        }
        cursor.close();
        return list;
    }

    public List<Order> getSpecificClientOrders(int i) {
        ArrayList<Order> list = new ArrayList<Order>();
        OrdersCursor cursor = new OrdersCursor(db.rawQuery("SELECT * FROM orders where customer_id =" + String.valueOf(i), null));// SELECT * FROM orders where customer_id = 2
        while (cursor.moveToNext()) {
            list.add(cursor.getOrders());
        }
        cursor.close();
        return list;
    }

    public List<Order> getAllordersWithSpecificsStatusOrders(boolean pending, boolean cancel, boolean confirmed, boolean ontransit, boolean finished) {
        ArrayList<Order> list = new ArrayList<Order>();
        if (pending || cancel || confirmed || ontransit || finished == true) {
            String statusid1 = "";
            String statusid2 = "";
            String statusid3 = "";
            String statusid4 = "";
            String statusid0 = "";

            String or1 = "";
            String or2 = "";
            String or3 = "";
            String or4 = "";

            Boolean flag0 = false;
            Boolean flag1 = false;
            Boolean flag2 = false;
            Boolean flag3 = false;
            Boolean flag4 = false;

            if (pending) {
                flag0 = true;
                statusid0 = "status_id = 0 ";
            } else {
                flag0 = false;
                statusid0 = "";
            }
            if (cancel) {
                flag1 = true;
                statusid1 = "status_id = 1 ";
            } else {
                flag1 = false;
                statusid1 = "";
            }
            if (confirmed) {
                flag2 = true;
                statusid2 = "status_id = 2 ";
            } else {
                flag2 = false;
                statusid2 = "";
            }
            if (ontransit) {
                flag3 = true;
                statusid3 = "status_id = 3 ";
            } else {
                flag3 = false;
                statusid3 = "";
            }
            if (finished) {
                flag4 = true;
                statusid4 = "status_id = 4 ";
            } else {
                flag4 = false;
                statusid4 = "";
            }

            if (flag0 == true) {
                if (flag1 || flag2 || flag3 || flag4) {
                    or1 = "or";
                }
            }
            if (flag1 == true) {
                if (flag2 || flag3 || flag4) {
                    or2 = "or";
                }
            }
            if (flag2 == true) {
                if (flag3 || flag4) {
                    or3 = "or";
                }
            }
            if (flag3 == true) {
                if (flag4) {
                    or4 = "or";
                }
            }

            String aux = "SELECT * FROM orders where " + statusid0 + " " + or1 + " " + statusid1 + " " + or2 + " " +
                    " " + statusid2 + " " + or3 + " " + statusid3 + " " + or4 + " " + statusid4;
            OrdersCursor cursor = new OrdersCursor(db.rawQuery("SELECT * FROM orders where " + statusid0 + " " + or1 + " " + statusid1 + " " + or2 + " " +
                    " " + statusid2 + " " + or3 + " " + statusid3 + " " + or4 + " " + statusid4, null));// SELECT * FROM orders where customer_id = 2
            while (cursor.moveToNext()) {
                list.add(cursor.getOrders());
            }
            cursor.close();
        }

        return list;
    }

    public List<Order> getSpecificiOrdersWithCustomerandStatus(boolean pending, boolean cancel, boolean confirmed, boolean ontransit, boolean finished, int id) {
        ArrayList<Order> list = new ArrayList<Order>();
        if (pending || cancel || confirmed || ontransit || finished == true) {
            String statusid1 = "";
            String statusid2 = "";
            String statusid3 = "";
            String statusid4 = "";
            String statusid0 = "";

            String or1 = "";
            String or2 = "";
            String or3 = "";
            String or4 = "";

            Boolean flag0 = false;
            Boolean flag1 = false;
            Boolean flag2 = false;
            Boolean flag3 = false;
            Boolean flag4 = false;

            if (pending) {
                flag0 = true;
                statusid0 = "(status_id = 0 and customer_id=" + String.valueOf(id) + " )";
            } else {
                flag0 = false;
                statusid0 = "";
            }
            if (cancel) {
                flag1 = true;
                statusid1 = "(status_id = 1 and customer_id=" + String.valueOf(id) + " )";
            } else {
                flag1 = false;
                statusid1 = "";
            }
            if (confirmed) {
                flag2 = true;
                statusid2 = "(status_id = 2 and customer_id=" + String.valueOf(id) + " )";
            } else {
                flag2 = false;
                statusid2 = "";
            }
            if (ontransit) {
                flag3 = true;
                statusid3 = "(status_id = 3 and customer_id=" + String.valueOf(id) + " )";
            } else {
                flag3 = false;
                statusid3 = "";
            }
            if (finished) {
                flag4 = true;
                statusid4 = "(status_id = 4 and customer_id=" + String.valueOf(id) + " )";
            } else {
                flag4 = false;
                statusid4 = "";
            }

            if (flag0 == true) {
                if (flag1 || flag2 || flag3 || flag4) {
                    or1 = "or";
                }
            }
            if (flag1 == true) {
                if (flag2 || flag3 || flag4) {
                    or2 = "or";
                }
            }
            if (flag2 == true) {
                if (flag3 || flag4) {
                    or3 = "or";
                }
            }
            if (flag3 == true) {
                if (flag4) {
                    or4 = "or";
                }
            }
            //SELECT * FROM orders where status_id = 4 and customer_id=2

            String aux = "SELECT * FROM orders where " + statusid0 + " " + or1 + " " + statusid1 + " " + or2 + " " +
                    " " + statusid2 + " " + or3 + " " + statusid3 + " " + or4 + " " + statusid4;
            OrdersCursor cursor = new OrdersCursor(db.rawQuery("SELECT * FROM orders where " + statusid0 + " " + or1 + " " + statusid1 + " " + or2 + " " +
                    " " + statusid2 + " " + or3 + " " + statusid3 + " " + or4 + " " + statusid4, null));// SELECT * FROM orders where customer_id = 2
            while (cursor.moveToNext()) {
                list.add(cursor.getOrders());
            }
            cursor.close();
        }

        return list;
    }

    public List<Order_status> getAllOrder_Status() {
        ArrayList<Order_status> list = new ArrayList<Order_status>();
        Order_statusCursor cursor = new Order_statusCursor(db.rawQuery("SELECT * FROM order_status ", null));// ORDER BY last_name
        while (cursor.moveToNext()) {
            list.add(cursor.getOrders());
        }
        cursor.close();
        return list;
    }

    public void UpdateOrder_status(int order_id, int to_status, String change_log) {
        //UPDATE orders SET status_id=2, change_log = 'Se cambio ahora al siguiente estado'  WHERE id = 9;
        db.execSQL("UPDATE orders SET status_id=" + String.valueOf(to_status) + ", change_log = '" + change_log + "'  WHERE id = " + String.valueOf(order_id) + ";");
    }

    //For order_assemblies
    public List<Assemblies> getAssembliesbyDescription(String description) {
        ArrayList<Assemblies> list = new ArrayList<Assemblies>();
        if(description.length()==0){description="asdasljdsadaslkj";}
        AssembliesCursor cursor = new AssembliesCursor(db.rawQuery("SELECT * FROM assemblies where description like \""+description+"%\" ORDER BY description;", null));
        while (cursor.moveToNext()) {
            list.add(cursor.getAssembly());
        }
        cursor.close();
        return list;
    }
    public Assemblies getAssembliebyId(int id_Aux) {
        Assemblies assembly = new Assemblies(0,"");
        AssembliesCursor cursor = new AssembliesCursor(db.rawQuery("SELECT * FROM assemblies where id = "+String.valueOf(id_Aux)+" ORDER BY description;", null));
        while (cursor.moveToNext()) {
            assembly = cursor.getAssembly();
        }
        cursor.close();
        return assembly;
    }
    public void AddOrder(int id,int customer_id,String date)
    {
        db.execSQL("INSERT INTO orders (id, status_id, customer_id, date, change_log) " +
                "VALUES ("+String.valueOf(id)+", 0, "+String.valueOf(customer_id)+", '"+date+"', NULL)");

                //INSERT INTO orders (id, status_id, customer_id, date, change_log) VALUES (8, 0, 3, '18-03-2017', NULL);
    }
    public void AddOrder_assembly(int id,int assembly_id,int qty)
    {
        String aux = "INSERT INTO order_assemblies (id, assembly_id, qty) " +
                "VALUES ("+id+", "+assembly_id+","+qty+")";
        db.execSQL("INSERT INTO order_assemblies (id, assembly_id, qty) " +
                "VALUES ("+String.valueOf(id)+", "+String.valueOf(assembly_id)+","+String.valueOf(qty)+")");
        //INSERT INTO order_assemblies (id, assembly_id, qty) VALUES (0, 0, 2);
    }
    public List<Order_assemblies> getOrderAssemblies_for_an_Order(int id_order)
    {
        ArrayList<Order_assemblies> list = new ArrayList<Order_assemblies>();
        OrdersAssembliesCursor cursor = new OrdersAssembliesCursor(db.rawQuery("SELECT * FROM order_assemblies" +
                " WHERE id="+String.valueOf(id_order), null));
        while (cursor.moveToNext()) {
            list.add(cursor.getOrderAsemblie());
        }
        cursor.close();
        return list;
        //SELECT * FROM order_assemblies WHERE id=6
    }
    public void UpdateOrderAssemblyQty(Order_assemblies oa)
    {
        db.execSQL("UPDATE order_assemblies SET qty="+String.valueOf(oa.getQty())+"  WHERE (id = "+String.valueOf(oa.getId())+" and assembly_id="+String.valueOf(oa.getAssembly_id())+");");

    }
    public void DeleteOrderAssembly(Order_assemblies oa)
    {
        db.execSQL("DELETE from order_assemblies WHERE (id ="+oa.getId()+" and assembly_id="+oa.getAssembly_id()+")");
    }
    public List<Order> GetAllPendingOrders()
    {
        ArrayList<Order> list = new ArrayList<Order>();
        OrdersCursor cursor = new OrdersCursor(db.rawQuery("    SELECT * FROM orders WHERE status_id=0 ", null));
        while (cursor.moveToNext()) {
            list.add(cursor.getOrders());
        }
        cursor.close();
        return list;
    }

    public List<Order_Sales> GetAllPendingOrdersbycost()
    {
        List<Order_Sales> list = new ArrayList<Order_Sales>();

        Order_SalesCursor cursor = new Order_SalesCursor((db.rawQuery("SELECT o.id, c.first_name, c.last_name, o.date, ords.description, SUM(oa.qty * ap.qty * p.price) AS total_cost\n" +
                "FROM orders o\n" +
                "INNER JOIN order_status ords ON (o.status_id = ords.id)\n" +
                "INNER JOIN customers c ON (o.customer_id = c.id)\n" +
                "INNER JOIN order_assemblies oa ON (o.id = oa.id)\n" +
                "INNER JOIN assemblies a ON (oa.assembly_id = a.id)\n" +
                "INNER JOIN assembly_products ap ON (a.id = ap.id)\n" +
                "INNER JOIN products p ON (ap.product_id = p.id)\n" +
                "WHERE o.status_id = \"0\"    \n" +
                "GROUP BY oa.id\n" +
                "ORDER BY total_cost DESC", null)));

        while (cursor.moveToNext()) {

            list.add((cursor.getOrderSales()));
        }
        cursor.close();
        return list;
    }

    public List<Order_Sales> GetAllPendingOrdersbyclient()
    {
        List<Order_Sales> list = new ArrayList<Order_Sales>();

        Order_SalesCursor cursor = new Order_SalesCursor((db.rawQuery("SELECT o.id, c.first_name, c.last_name, o.date, ords.description, SUM(oa.qty * ap.qty * p.price) AS total_cost\n" +
                "FROM orders o\n" +
                "INNER JOIN order_status ords ON (o.status_id = ords.id)\n" +
                "INNER JOIN customers c ON (o.customer_id = c.id)\n" +
                "INNER JOIN order_assemblies oa ON (o.id = oa.id)\n" +
                "INNER JOIN assemblies a ON (oa.assembly_id = a.id)\n" +
                "INNER JOIN assembly_products ap ON (a.id = ap.id)\n" +
                "INNER JOIN products p ON (ap.product_id = p.id)\n" +
                "WHERE o.status_id = \"0\"    \n" +
                "GROUP BY oa.id\n" +
                "ORDER BY c.last_name asc", null)));

        while (cursor.moveToNext()) {

            list.add((cursor.getOrderSales()));
        }
        cursor.close();
        return list;
    }

    public List<Products> getallMissingProductsbyOrder(String id ) {
        List<Products> list = new ArrayList<Products>();
        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);
        ProductCursor cursor = new ProductCursor((db.rawQuery("SELECT p.id,p.category_id,p.description,p.price, (sum(oa.qty * ap.qty) - p.qty )  AS qty\n" +
                "FROM orders o\n" +
                "INNER JOIN order_status ords ON (o.status_id = ords.id)\n" +
                "INNER JOIN customers c ON (o.customer_id = c.id)\n" +
                "INNER JOIN order_assemblies oa ON (o.id = oa.id)\n" +
                "INNER JOIN assemblies a ON (oa.assembly_id = a.id)\n" +
                "INNER JOIN assembly_products ap ON (a.id = ap.id)\n" +
                "INNER JOIN products p ON (ap.product_id = p.id)\n" +
                "where o.id = '" + id + "'" +
                "GROUP BY p.id Having (sum(oa.qty * ap.qty)- p.qty) > 0 \n" +
                "ORDER BY qty DESC", null)));

        while (cursor.moveToNext()) {
            //list.add(new Category(cursor.getInt(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.ID))),
            //   cursor.getString(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.DESCRIPTION)))));
            list.add((cursor.getProduct()));  // metodo wrappcursor
        }
        cursor.close();
        return list;
    }

    public List<Products> getallnotMissingProductsbyOrder(String id ) {
        List<Products> list = new ArrayList<Products>();
        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);
        ProductCursor cursor = new ProductCursor((db.rawQuery("SELECT p.id,p.category_id,p.description,p.price, (p.qty  - sum(oa.qty * ap.qty ))  AS qty\n" +
                "FROM orders o\n" +
                "INNER JOIN order_status ords ON (o.status_id = ords.id)\n" +
                "INNER JOIN customers c ON (o.customer_id = c.id)\n" +
                "INNER JOIN order_assemblies oa ON (o.id = oa.id)\n" +
                "INNER JOIN assemblies a ON (oa.assembly_id = a.id)\n" +
                "INNER JOIN assembly_products ap ON (a.id = ap.id)\n" +
                "INNER JOIN products p ON (ap.product_id = p.id)\n" +
                "where o.id = '" + id + "'" +
                "GROUP BY p.id Having (sum(oa.qty * ap.qty)- p.qty) <= 0 \n" +
                "ORDER BY qty DESC", null)));

        while (cursor.moveToNext()) {
            //list.add(new Category(cursor.getInt(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.ID))),
            //   cursor.getString(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.DESCRIPTION)))));
            list.add((cursor.getProduct()));  // metodo wrappcursor
        }
        cursor.close();
        return list;
    }

    public void Update_productquantity(String id, String qty) {

        ContentValues values = new ContentValues();

        values.put(ProductTable.Columns.QUANTITY, qty);
        db.update(ProductTable.Name,
                values,
                ProductTable.Columns.ID + "= ?",
                new String[]{id}
        );


    }





}

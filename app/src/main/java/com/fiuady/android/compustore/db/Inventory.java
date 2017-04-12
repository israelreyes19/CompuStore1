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

import com.fiuady.android.compustore.db.InventoryDbSchema.*;

class CustomersCursor extends CursorWrapper {
    public CustomersCursor(Cursor cursor) {
        super(cursor);
    }

    private int id;
    private String first_name="";
    private String last_name="";
    private String address="";
    private String phone1="";
    private String phone2="";
    private String phone3="";
    private String e_mail="";

    public Customers getCustomer() {
        Cursor cursor = getWrappedCursor();
        id =cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.id));
        first_name =  cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.first_name));
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

        return new Customers(id,first_name,last_name,address,phone1,phone2 ,phone3 ,e_mail );
    }
}


class ProductCursor extends CursorWrapper
{
    public ProductCursor(Cursor cursor) {
        super(cursor);
    }


    public Products getProduct()
    {

        Cursor cursor = getWrappedCursor();
        return  new Products(cursor.getInt(cursor.getColumnIndex((InventoryDbSchema.ProductTable.Columns.ID))), cursor.getInt(cursor.getColumnIndex((InventoryDbSchema.ProductTable.Columns.CATEGORY_ID))),
                cursor.getString(cursor.getColumnIndex((InventoryDbSchema.ProductTable.Columns.DESCRIPTION))),cursor.getInt(cursor.getColumnIndex((InventoryDbSchema.ProductTable.Columns.PRICE))),cursor.getInt(cursor.getColumnIndex((InventoryDbSchema.ProductTable.Columns.QUANTITY))));

    }

}


class CategoryCursor extends CursorWrapper
{
    public CategoryCursor(Cursor cursor) {
        super(cursor);
    }

    public Category getCategory()
    {
        Cursor cursor = getWrappedCursor();
        return new Category(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.CategoriesTable.Columns.ID)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CategoriesTable.Columns.DESCRIPTION)));
    }
}

class AssembliesCursor extends CursorWrapper
{
    public AssembliesCursor(Cursor cursor) {super(cursor);}

    public Assemblies getAssembly()
    {
        Cursor cursor = getWrappedCursor();
        return new Assemblies(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.AssembliesTable.Columns.ID)),
                cursor.getString(cursor.getColumnIndex(AssembliesTable.Columns.DESCRIPTION)));
    }
}

public final class Inventory {

    private InventoryHelper inventoryHelper;


    private SQLiteDatabase db;

    public Inventory(Context context){
        //InventoryHelper.backupDatabaseFile(context);
        inventoryHelper = new InventoryHelper(context);
        db = inventoryHelper.getWritableDatabase();
    }


    public List<Products> getallProducts()
    {
        List<Products> list = new ArrayList<Products>();
        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);
        ProductCursor cursor = new ProductCursor((db.rawQuery("SELECT * FROM products ORDER BY description", null)));
        while (cursor.moveToNext()){
            //list.add(new Category(cursor.getInt(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.ID))),
            //   cursor.getString(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.DESCRIPTION)))));
            list.add((cursor.getProduct()));  // metodo wrappcursor
        }
        cursor.close();
        return list;
    }

    public List<Products> getallProductsineverycategory(String description)
    {
        List<Products> list = new ArrayList<Products>();


        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        ProductCursor cursor = new ProductCursor((db.rawQuery("SELECT * " +
                "FROM products p " +
                "WHERE p.description LIKE'"   + description +  "%' " +
              " ORDER BY p.description"  , null)));

        while (cursor.moveToNext()){

            //list.add(new Category(cursor.getInt(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.ID))),
            //   cursor.getString(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.DESCRIPTION)))));

            list.add((cursor.getProduct()));  // metodo wrappcursor

        }
        cursor.close();
        return list;
    }


    public List<Products> getoneProducts(String categroydescription, String productdescription)
    {
        List<Products> list = new ArrayList<Products>();


        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        ProductCursor cursor = new ProductCursor((db.rawQuery(" SELECT p.id, p.category_id, p.description, p.price, p.qty\n" +
                "FROM products p " +
                "INNER JOIN product_categories c ON (p.category_id = c.id) " +
                "WHERE c.description LIKE '" + categroydescription + "'" +
                "AND p.description LIKE '" + productdescription +"%" + "' " +
                " ORDER BY p.description", null)));

        while (cursor.moveToNext()){

            //list.add(new Category(cursor.getInt(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.ID))),
            //   cursor.getString(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.DESCRIPTION)))));

            list.add((cursor.getProduct()));  // metodo wrappcursor

        }
        cursor.close();
        return list;
    }


    public  List<Products> getonecategoryproduct(String categroy_description)
    {
        List<Products> list = new ArrayList<Products>();


        //  Cursor cursor = db.rawQuery("SELECT * FROM categories ORDER BY id", null);

        ProductCursor cursor = new ProductCursor((db.rawQuery("SELECT p.id, p.category_id, p.description, p.price, p.qty " +
                "FROM products p " +
                "INNER JOIN product_categories c ON (p.category_id = c.id) " +
                "WHERE c.description LIKE '" + categroy_description + "' ORDER BY p.description" , null)));

        while (cursor.moveToNext()){

            //list.add(new Category(cursor.getInt(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.ID))),
            //   cursor.getString(cursor.getColumnIndex((InventoryDBSchema.CategoriesTable.Columns.DESCRIPTION)))));

            list.add((cursor.getProduct()));  // metodo wrappcursor

        }
        cursor.close();
        return list;
    }

    public int return_categroty_id(String descritpion)
    {

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

    public void addProduct(int id, String cat_id, String descritpion, String price, String qty )
    {
        int mx=-1;
        Cursor cursor = db.rawQuery("SELECT max(ID) from products", new String[]{});
        if (cursor!=null)
        {
            if(cursor.moveToFirst())
            {
                mx = cursor.getInt(0);
            }
            cursor.close();
        }
        else
        {
            mx = -1;
        }
        if(mx!=-1){
            //ContentValues values = new ContentValues();
            //values.put(CategoriesTable.Columns.DESCRIPTION, category.getDescription());

            db.execSQL("INSERT INTO products (id, category_id, description, price, qty) VALUES ("+String.valueOf(mx+1)+"," + cat_id + ",'"+descritpion+"'," + price +","+ qty +");");
        }
    }

    public boolean DeleteProduct(String id)
    {
        Cursor cursor = db.rawQuery("SELECT p.id from " +
                "products p INNER JOIN assembly_products ap ON (p.id = ap.product_id) " +
                "GROUP BY p.id HAVING p.id = "+ id + ";", new String[]{});
        if (cursor==null)
        {
            db.execSQL("DELETE from products WHERE id = " + id + ";");
            return true;
        }
        else if(!cursor.moveToFirst())
        {
            db.execSQL("DELETE from products WHERE id = " + id + ";");
            cursor.close();
            return true;

        }
        else
        {
            cursor.close();
            return false;
        }

    }

public void add_stock(String id, String qty)
{

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

    public void Update_product(String id, String category_id,String description, String price, String qty)
    {

        ContentValues values = new ContentValues();

        //values.put(ProductTable.Columns.QUANTITY, qty);
        values.put(ProductTable.Columns.DESCRIPTION,description);
        values.put(ProductTable.Columns.CATEGORY_ID,category_id);
        values.put(ProductTable.Columns.PRICE,price);
        db.update(ProductTable.Name,
                values,
                ProductTable.Columns.ID + "= ?",
                new String[]{id}
        );

        //  db.rawQuery("UPDATE products " +
        //   "SET qty = "+  qty + "" +
        //   " WHERE id = "+ id  , null);

    }

public boolean check_product(String description)
{
    Cursor cursor = db.rawQuery("SELECT p.id from " +
            "products p " +
            " GROUP BY p.id HAVING p.description = '"+ description + "';", new String[]{});
    if (cursor==null)
    {

        return true;
    }
    else if(!cursor.moveToFirst())
    {

        cursor.close();
        return true;

    }
    else
    {
        cursor.close();
        return false;
    }
}


    public List<Category> getAllCategories()
    {
        ArrayList<Category> list = new ArrayList<Category>();
        CategoryCursor cursor = new CategoryCursor(db.rawQuery("SELECT * FROM product_categories ORDER BY description", null));
        while(cursor.moveToNext())
        {
            list.add(cursor.getCategory());
        }
        cursor.close();
        return list;
    }

    public List<Assemblies> getAllAssemblies()
    {
        ArrayList<Assemblies> list = new ArrayList<Assemblies>();
        AssembliesCursor cursor = new AssembliesCursor(db.rawQuery("SELECT * FROM assemblies ORDER BY description;", null));
        while(cursor.moveToNext())
        {
            list.add(cursor.getAssembly());
        }
        cursor.close();
        return list;
    }

    public List<Products> getAssemblyProducts(Assemblies assembly)
    {
        List<Products> list = new ArrayList<Products>();
        ProductCursor cursor = new ProductCursor(db.rawQuery("SELECT p.id, p.category_id, p.description, p.price, ap.qty FROM assemblies a " +
                "INNER JOIN assembly_products ap ON (a.id = ap.id) " +
                "INNER JOIN products p ON (ap.product_id=p.id) " +
                "WHERE a.id=" + String.valueOf(assembly.getId()) +
                " ORDER BY p.description;",null));
        while(cursor.moveToNext())
        {
            list.add(cursor.getProduct());
        }
        return list;
    }

    public void updateCategory(Category category)
    {
        ContentValues values = new ContentValues();

        values.put(CategoriesTable.Columns.DESCRIPTION, category.getDescription());
        db.update(CategoriesTable.Name,
                values,
                CategoriesTable.Columns.ID + "= ?",
                new String[]{Integer.toString(category.getId())}
        );

    }

    public void updateProductInAssembly(Assemblies assembly, Products product, int qty)
    {
        db.execSQL("UPDATE assembly_products SET qty = " + String.valueOf(qty)+
                " WHERE (id=" +String.valueOf(assembly.getId())+
                ") AND (product_id=" + String.valueOf(product.getId())+
                ");");
    }

    public void deleteProductInAssembly(Assemblies assembly, Products product)
    {
        db.execSQL("DELETE FROM assembly_products WHERE (product_id="+ String.valueOf(product.getId()) +
                ") AND (id="+ String.valueOf(assembly.getId()) +
                ");");
    }

    public boolean updateAssembly(Assemblies assembly, String description)
    {
        if(assembly.getDescription().equals(description))
        {
            //db.execSQL("");
            return true;
        }
        Cursor cursor = db.rawQuery("SELECT id FROM assemblies  where description = '" +
                description +
                "';", new String[]{});
        if(!cursor.moveToFirst())
        {
            db.execSQL("UPDATE assemblies  SET description = '" +
                    description +
                    "' WHERE id="+String.valueOf(assembly.getId()) +
                    ";");
            cursor.close();
            return true;
        }
        else
        {
            cursor.close();
            return false;
        }
        //return true;
    }

    public void addCategory(Category category)
    {
        int mx=-1;
        Cursor cursor = db.rawQuery("SELECT max(ID) from product_categories", new String[]{});
        if (cursor!=null)
        {
            if(cursor.moveToFirst())
            {
                mx = cursor.getInt(0);
            }
            cursor.close();
        }
        else
        {
            mx = -1;
        }
        if(mx!=-1){
            //ContentValues values = new ContentValues();
            //values.put(CategoriesTable.Columns.DESCRIPTION, category.getDescription());

            db.execSQL("INSERT INTO product_categories (id, description) VALUES ("+String.valueOf(mx+1)+", '"+category.getDescription()+"');");
        }
    }


    public boolean addProductInAssembly(Products product, Assemblies assembly)
    {
        Cursor cursor = db.rawQuery("SELECT id FROM assembly_products ap WHERE ap.id=" + String.valueOf(assembly.getId())+
                " AND ap.product_id=" + String.valueOf(product.getId())+
                ";",new String[]{});
        if(!cursor.moveToFirst())
        {
            db.execSQL("INSERT INTO assembly_products (id, product_id, qty) VALUES ("+String.valueOf(assembly.getId()) +
                    ", " + String.valueOf(product.getId())+
                    ", 1);");
            cursor.close();
            return true;
        }
        else
        {
            cursor.close();
            return false;
        }
    }

    public boolean addAssembly(Assemblies assemblies)
    {
        int mx=-1;
        Cursor cursor = db.rawQuery("SELECT max(ID) from assemblies;", new String[]{});
        if (cursor!=null)
        {
            if(cursor.moveToFirst())
            {
                mx = cursor.getInt(0);
            }
            cursor.close();
        }
        else
        {
            mx = -1;
        }
        if(mx!=-1){ // Editado
            Cursor cursor1 = db.rawQuery("SELECT id FROM assemblies  where description = '" +
                    assemblies.getDescription() +
                    "';", new String[]{});
            if(!cursor1.moveToFirst())
            {
                db.execSQL("INSERT INTO assemblies (id, description) VALUES (" +String.valueOf(mx+1) +
                        ", '" + assemblies.getDescription() +
                        "');");
                cursor1.close();
                return true;
            }
            else
            {
                cursor1.close();
                return false;
            }

        }
        return false;

    }

    public void addAuxAssembly(Assemblies assemblies)
    {
        db.execSQL("INSERT INTO assemblies (id, description) VALUES (" +String.valueOf(assemblies.getId()) +
                ", '" + assemblies.getDescription() +
                "');");
    }

    public void transferProductsToDefinitiveAssembly()
    {
        int mx=-1;
        Cursor cursor = db.rawQuery("SELECT max(ID) from assemblies;", new String[]{});
        if (cursor!=null)
        {
            if(cursor.moveToFirst())
            {
                mx = cursor.getInt(0);
            }
            cursor.close();
        }
        else
        {
            mx = -1;
        }
        if(mx!=-1){
            db.execSQL("UPDATE assembly_products SET id = " +String.valueOf( mx)+
                    " WHERE id=9999");
        }
    }


    public  void  deleteAux()
    {
        db.execSQL("DELETE FROM assemblies where id=9999");
    }


    public void emptyNDeleteAux()
    {
        db.execSQL("DELETE From assembly_products WHERE id=9999;");
        db.execSQL("DELETE FROM assemblies where id=9999;");
    }

    public boolean  deleteCategory(Category category)
    {
        Cursor cursor = db.rawQuery("SELECT pc.id from " +
                "product_categories pc INNER JOIN products p ON (pc.id = p.category_id) " +
                "GROUP BY pc.id HAVING pc.id = "+ String.valueOf(category.getId())+";", new String[]{});
        if (cursor==null)
        {
            db.execSQL("DELETE from product_categories WHERE id = " + String.valueOf(category.getId()) + ";");
            return true;
        }
        else if(!cursor.moveToFirst())
        {
            db.execSQL("DELETE from product_categories WHERE id = " + String.valueOf(category.getId()) + ";");
            cursor.close();
            return true;

        }
        else
        {
            cursor.close();
            return false;
        }
    }

    public boolean deleteAssemblies(Assemblies assemblies)
    {
        Cursor cursor = db.rawQuery("SELECT a.id FROM assemblies a " +
                "INNER JOIN order_assemblies oa ON (a.id = oa.assembly_id) " +
                "GROUP BY a.id HAVING a.id = " + String.valueOf(assemblies.getId()) +
                ";", new String[]{});
        if (cursor == null)
        {
            db.execSQL("DELETE from assemblies WHERE id = " + String.valueOf(assemblies.getId()) + ";");
            return true;
        }
        else if(!cursor.moveToFirst())
        {
            db.execSQL("DELETE From assembly_products WHERE id = " + String.valueOf(assemblies.getId())+
                    ";");
            db.execSQL("DELETE from assemblies WHERE id = " + String.valueOf(assemblies.getId()) + ";");
            cursor.close();
            return true;

        }
        else
        {
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

        if (firstname.length() + lastname.length() + address.length() + phone.length() + email.length() == 0)
        {
            CustomersCursor cursor = new CustomersCursor(db.rawQuery("SELECT * FROM customers ORDER BY last_name", null));// ORDER BY last_name
            while (cursor.moveToNext()) {
                list.add(cursor.getCustomer());

            }
            cursor.close();
            return list;
        }
        else
        {
            String _firsname,_lastname, _address, _phones, _email;
            String or1 ="";
            String or2 = "";
            String or3 = "";
            String or4 = "";
           //SELECT * FROM customers c WHERE c.first_name or c.last_name
           // or c.address or c.phone1 or c.phone2 or c.phone3 or c.e_mail like "m%" order by c.last_name
            Boolean flag0= false;
            Boolean flag1= false;
            Boolean flag2= false;
            Boolean flag3= false;
            Boolean flag4= false;

            if (firstname.length()>0){flag0 = true; _firsname= "first_name ";}else {flag0 = false;_firsname ="";}
            if(lastname.length()>0) {flag1 = true; _lastname= " last_name ";}else {flag1 = false;_lastname="";}
            if (address.length()>0){flag2 = true; _address =" address ";}else{flag2 = false;_address ="";}
            if(phone.length()>0) {flag3 = true;_phones = " phone1 or phone2 or phone3";}else {flag3 = false;_phones="";}
            if(email.length()>0){flag4= true; _email = " e_mail";}else {flag4 = false;_email ="";}

            if (flag0==true) {if (flag1 ||flag2||flag3||flag4 ){or1 = "or";} }
            if (flag1==true) {if (flag2||flag3||flag4 ){or2 = "or";} }
            if (flag2==true) {if (flag3||flag4 ){or3 = "or";} }
            if (flag3==true) {if (flag4 ){or4 = "or";} }
            if (descrip.length() == 0){descrip= "holajeje.jpg";}

            String aux = "SELECT * FROM customers WHERE "+_firsname+" "+or1+" "+_lastname+" " + // this only get the last or
                    or2+" "+_address+" "+or3+" "+_phones+" "+or4+" "+_email+" like \""+descrip+"%\" order by last_name";
            CustomersCursor cursor = new CustomersCursor(db.rawQuery(aux, null));
           while (cursor.moveToNext()) {
               list.add(cursor.getCustomer());

           }
           cursor.close();
           return list;


        }

    }
    public void addCustomer(String firstname, String lastname, String address,String phone1,String phone2,String phone3,String email){
        ArrayList<Customers> list = new ArrayList<Customers>();
        CustomersCursor cursor = new CustomersCursor(db.rawQuery("SELECT * FROM customers ", null));// ORDER BY last_name
        while (cursor.moveToNext()) {
            list.add(cursor.getCustomer());

        }
        cursor.close();
        int id_prove = 0;
        for (Customers client: list)
        {
            if (client.getId() >id_prove)
            {
                id_prove=client.getId();
            }
        }
         id_prove = id_prove+1;

        //db.execSQL("INSERT INTO product_categories (id, description) VALUES ("+String.valueOf(mx+1)+
          //      ", '"+category.getDescription()+"');");
        db.execSQL("INSERT INTO customers (id, first_name, last_name, address, phone1, phone2, phone3, e_mail)" +
        "VALUES ("+id_prove+", '"+firstname+"', '"+lastname+"', '"+address+"', "+phone1+", "+phone2+", "+phone3+", "+email+")");


    }
    public boolean deleteCustomer(Customers customer){
        boolean val;
        String prove = "SELECT c.id from " +
                "customers c INNER JOIN orders o ON (c.id = o.customer_id) " +
                "GROUP BY c.id HAVING c.id = "+ String.valueOf(customer.getId())+";";
        Cursor cursor = db.rawQuery("SELECT c.id from " +
                "customers c INNER JOIN orders o ON (c.id = o.customer_id) " +
                "GROUP BY c.id HAVING c.id = "+ String.valueOf(customer.getId())+";", new String[]{});
        if (cursor==null)
        {
            db.execSQL("DELETE from customers WHERE id = " + String.valueOf(customer.getId())+ ";");
            return true;
        }
        else if(!cursor.moveToFirst())
        {
            db.execSQL("DELETE from customers WHERE id = " + String.valueOf(customer.getId()) + ";");
            cursor.close();
            return true;

        }
        else
        {
            cursor.close();
            return false;
        }

    }
    public void updateCustomer(Customers customer)
    {
        String aux = "UPDATE customers SET  first_name = '"+ customer.getFirst_name()+
                "' , last_name = '"+ customer.getLast_name()+
                "' , address = '"+ customer.getAddress()+
                "' , phone1 = '"+ customer.getPhone1()+
                "' , phone2 = '"+ customer.getPhone2()+
                "' , phone3 = '"+ customer.getPhone3()+
                "' , e_mail = '"+ customer.getE_mail()+
                "' WHERE id = "+
                String.valueOf(customer.getId())+";";


        db.execSQL("UPDATE customers SET  first_name = '"+ customer.getFirst_name()+
                "' , last_name = '"+ customer.getLast_name()+
                "' , address = '"+ customer.getAddress()+
                "' , phone1 = "+ customer.getPhone1()+
                " , phone2 = "+ customer.getPhone2()+
                " , phone3 = "+ customer.getPhone3()+
                " , e_mail = "+ customer.getE_mail()+
                " WHERE id = "+
                String.valueOf(customer.getId())+";");



    }
}

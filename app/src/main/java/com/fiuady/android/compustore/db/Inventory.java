package com.fiuady.android.compustore.db;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
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
                "WHERE p.description LIKE'"   + description +  "%'", null)));

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
                "AND p.description LIKE '" + productdescription +"%" + "'", null)));

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
        CustomersCursor cursor = new CustomersCursor(db.rawQuery("SELECT * FROM customers", null));// ORDER BY last_name
        while (cursor.moveToNext()) {
            list.add(cursor.getCustomer());

        }
        cursor.close();
        return list;
    }


}

package com.fiuady.android.compustore.db;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import com.fiuady.android.compustore.db.InventoryDbSchema.*;

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

public final class Inventory {

    private InventoryHelper inventoryHelper;


    private SQLiteDatabase db;

    public Inventory(Context context){
        //InventoryHelper.backupDatabaseFile(context);
        inventoryHelper = new InventoryHelper(context);
        db = inventoryHelper.getWritableDatabase();
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
}

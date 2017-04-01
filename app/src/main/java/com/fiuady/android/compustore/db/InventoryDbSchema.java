package com.fiuady.android.compustore.db;


public final class InventoryDbSchema {
    public static final class CategoriesTable
    {
        public static final String Name = "product_categories";

        public static final class Columns{
            public static final String ID = "id";
            public static final String DESCRIPTION = "description";
        }
    }
    public static final class ProductTable
    {
        public static final String Name = "products";

        public static final class Columns{
            public static final String ID = "id";
            public static final String CATEGORY_ID = "category_id";
            public static final String DESCRIPTION = "description";
            public static final String PRICE= "price";
            public static final String QUANTITY = "qty";
        }
    }



}


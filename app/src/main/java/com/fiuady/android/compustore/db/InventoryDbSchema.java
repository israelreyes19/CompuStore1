package com.fiuady.android.compustore.db;


public final class InventoryDbSchema {
    public static final class CategoriesTable {
        public static final String Name = "product_categories";

        public static final class Columns {
            public static final String ID = "id";
            public static final String DESCRIPTION = "description";
        }
    }

    public static final class ProductTable {
        public static final String Name = "products";

        public static final class Columns {
            public static final String ID = "id";
            public static final String CATEGORY_ID = "category_id";
            public static final String DESCRIPTION = "description";
            public static final String PRICE = "price";
            public static final String QUANTITY = "qty";
        }
    }

    public static final class CustomersTable {
        public static final String Name = "clients";

        public static final class Columns {
            public static final String id = "id";
            public static final String first_name = "first_name";
            public static final String last_name = "last_name";
            public static final String address = "address";
            public static final String Phone1 = "phone1";
            public static final String Phone2 = "phone2";
            public static final String Phone3 = "phone3";
            public static final String e_mail = "e_mail";
            //id, first_name, last_name, address, phone1, phone2, phone3, e_mail)
        }

    }

    public static final class OrdersTable {
        public static final String Name = "orders";

        public static final class Columns {
            public static final String id = "id";
            public static final String status_id = "status_id";
            public static final String customer_id = "customer_id";
            public static final String date = "date";
            public static final String change_log = "change_log";
            //INSERT INTO orders (id, status_id, customer_id, date, change_log) VALUES (8, 0, 3, '18-03-2017', NULL);

        }

    }

    public static final class OrdersStatusTable {
        public static final String Name = "order_status";

        public static final class Columns {
            public static final String id = "id";
            public static final String description = "description";
            public static final String editable = "editable";
            public static final String previous = "previous";
            public static final String next = "next";
            // INSERT INTO order_status (id, description, editable, previous, next) VALUES (4, 'Finalizado', 0, '-', '-');

        }

    }

    public static final class OrderAssembliesTable {
        public static final String Name = "order_assemblies";

        public static final class Columns {
            public static final String id = "id";
            public static final String assembly_id = "assembly_id";
            public static final String qty = "qty";
        }
        //INSERT INTO order_assemblies (id, assembly_id, qty) VALUES (0, 0, 2);
    }

    public static class AssembliesTable {
        public static final String Name = "assemblies";

        public static final class Columns {
            public static final String ID = "id";
            public static final String DESCRIPTION = "description";
        }
    }

}


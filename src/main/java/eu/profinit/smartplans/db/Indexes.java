/*
 * This file is generated by jOOQ.
 */
package eu.profinit.smartplans.db;


import eu.profinit.smartplans.db.tables.Transaction;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>public</code> schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index IDX_CLIENT = Indexes0.IDX_CLIENT;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index IDX_CLIENT = Internal.createIndex("idx_client", Transaction.TRANSACTION, new OrderField[] { Transaction.TRANSACTION.CLIENT_ID, Transaction.TRANSACTION.TRANSACTION_ID }, true);
    }
}
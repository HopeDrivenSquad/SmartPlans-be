/*
 * This file is generated by jOOQ.
 */
package eu.profinit.smartplans.db;


import eu.profinit.smartplans.db.tables.Plan;
import eu.profinit.smartplans.db.tables.Transaction;
import eu.profinit.smartplans.db.tables.records.PlanRecord;
import eu.profinit.smartplans.db.tables.records.TransactionRecord;

import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>public</code> schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<PlanRecord, Integer> IDENTITY_PLAN = Identities0.IDENTITY_PLAN;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<PlanRecord> PLAN_PKEY = UniqueKeys0.PLAN_PKEY;
    public static final UniqueKey<TransactionRecord> U_TX_ID = UniqueKeys0.U_TX_ID;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<PlanRecord, Integer> IDENTITY_PLAN = Internal.createIdentity(Plan.PLAN, Plan.PLAN.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<PlanRecord> PLAN_PKEY = Internal.createUniqueKey(Plan.PLAN, "plan_pkey", new TableField[] { Plan.PLAN.ID }, true);
        public static final UniqueKey<TransactionRecord> U_TX_ID = Internal.createUniqueKey(Transaction.TRANSACTION, "u_tx_id", new TableField[] { Transaction.TRANSACTION.TRANSACTION_ID }, true);
    }
}

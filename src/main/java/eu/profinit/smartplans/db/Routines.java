/*
 * This file is generated by jOOQ.
 */
package eu.profinit.smartplans.db;


import eu.profinit.smartplans.db.routines.PgStatStatementsReset;

import org.jooq.Configuration;


/**
 * Convenience access to all stored procedures and functions in public
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Routines {

    /**
     * Call <code>public.pg_stat_statements_reset</code>
     */
    public static void pgStatStatementsReset(Configuration configuration) {
        PgStatStatementsReset p = new PgStatStatementsReset();

        p.execute(configuration);
    }
}

/*
 * This file is generated by jOOQ.
 */
package eu.profinit.smartplans.db;


import org.jooq.Sequence;
import org.jooq.impl.Internal;


/**
 * Convenience access to all sequences in public
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>public.plan_id_seq</code>
     */
    public static final Sequence<Integer> PLAN_ID_SEQ = Internal.createSequence("plan_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.INTEGER.nullable(false), null, null, null, null, false, null);
}

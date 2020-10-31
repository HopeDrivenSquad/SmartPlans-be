/*
 * This file is generated by jOOQ.
 */
package eu.profinit.smartplans.db.tables.records;


import eu.profinit.smartplans.db.tables.Plan;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PlanRecord extends UpdatableRecordImpl<PlanRecord> implements Record6<Integer, Integer, String, Boolean, BigDecimal, LocalDate> {

    private static final long serialVersionUID = -387967991;

    /**
     * Setter for <code>public.plan.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.plan.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.plan.client_id</code>.
     */
    public void setClientId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.plan.client_id</code>.
     */
    public Integer getClientId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.plan.name</code>.
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.plan.name</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.plan.enabled</code>.
     */
    public void setEnabled(Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.plan.enabled</code>.
     */
    public Boolean getEnabled() {
        return (Boolean) get(3);
    }

    /**
     * Setter for <code>public.plan.amount</code>.
     */
    public void setAmount(BigDecimal value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.plan.amount</code>.
     */
    public BigDecimal getAmount() {
        return (BigDecimal) get(4);
    }

    /**
     * Setter for <code>public.plan.date_to</code>.
     */
    public void setDateTo(LocalDate value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.plan.date_to</code>.
     */
    public LocalDate getDateTo() {
        return (LocalDate) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, Integer, String, Boolean, BigDecimal, LocalDate> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Integer, Integer, String, Boolean, BigDecimal, LocalDate> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Plan.PLAN.ID;
    }

    @Override
    public Field<Integer> field2() {
        return Plan.PLAN.CLIENT_ID;
    }

    @Override
    public Field<String> field3() {
        return Plan.PLAN.NAME;
    }

    @Override
    public Field<Boolean> field4() {
        return Plan.PLAN.ENABLED;
    }

    @Override
    public Field<BigDecimal> field5() {
        return Plan.PLAN.AMOUNT;
    }

    @Override
    public Field<LocalDate> field6() {
        return Plan.PLAN.DATE_TO;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getClientId();
    }

    @Override
    public String component3() {
        return getName();
    }

    @Override
    public Boolean component4() {
        return getEnabled();
    }

    @Override
    public BigDecimal component5() {
        return getAmount();
    }

    @Override
    public LocalDate component6() {
        return getDateTo();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getClientId();
    }

    @Override
    public String value3() {
        return getName();
    }

    @Override
    public Boolean value4() {
        return getEnabled();
    }

    @Override
    public BigDecimal value5() {
        return getAmount();
    }

    @Override
    public LocalDate value6() {
        return getDateTo();
    }

    @Override
    public PlanRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public PlanRecord value2(Integer value) {
        setClientId(value);
        return this;
    }

    @Override
    public PlanRecord value3(String value) {
        setName(value);
        return this;
    }

    @Override
    public PlanRecord value4(Boolean value) {
        setEnabled(value);
        return this;
    }

    @Override
    public PlanRecord value5(BigDecimal value) {
        setAmount(value);
        return this;
    }

    @Override
    public PlanRecord value6(LocalDate value) {
        setDateTo(value);
        return this;
    }

    @Override
    public PlanRecord values(Integer value1, Integer value2, String value3, Boolean value4, BigDecimal value5, LocalDate value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PlanRecord
     */
    public PlanRecord() {
        super(Plan.PLAN);
    }

    /**
     * Create a detached, initialised PlanRecord
     */
    public PlanRecord(Integer id, Integer clientId, String name, Boolean enabled, BigDecimal amount, LocalDate dateTo) {
        super(Plan.PLAN);

        set(0, id);
        set(1, clientId);
        set(2, name);
        set(3, enabled);
        set(4, amount);
        set(5, dateTo);
    }
}
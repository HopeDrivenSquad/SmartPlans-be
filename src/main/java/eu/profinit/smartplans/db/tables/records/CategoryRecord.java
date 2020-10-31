/*
 * This file is generated by jOOQ.
 */
package eu.profinit.smartplans.db.tables.records;


import eu.profinit.smartplans.db.tables.Category;

import java.math.BigDecimal;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CategoryRecord extends TableRecordImpl<CategoryRecord> implements Record3<Long, String, BigDecimal> {

    private static final long serialVersionUID = -53513048;

    /**
     * Setter for <code>public.category.category_id</code>.
     */
    public void setCategoryId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.category.category_id</code>.
     */
    public Long getCategoryId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.category.category_name</code>.
     */
    public void setCategoryName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.category.category_name</code>.
     */
    public String getCategoryName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.category.necessity</code>.
     */
    public void setNecessity(BigDecimal value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.category.necessity</code>.
     */
    public BigDecimal getNecessity() {
        return (BigDecimal) get(2);
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, String, BigDecimal> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Long, String, BigDecimal> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Category.CATEGORY.CATEGORY_ID;
    }

    @Override
    public Field<String> field2() {
        return Category.CATEGORY.CATEGORY_NAME;
    }

    @Override
    public Field<BigDecimal> field3() {
        return Category.CATEGORY.NECESSITY;
    }

    @Override
    public Long component1() {
        return getCategoryId();
    }

    @Override
    public String component2() {
        return getCategoryName();
    }

    @Override
    public BigDecimal component3() {
        return getNecessity();
    }

    @Override
    public Long value1() {
        return getCategoryId();
    }

    @Override
    public String value2() {
        return getCategoryName();
    }

    @Override
    public BigDecimal value3() {
        return getNecessity();
    }

    @Override
    public CategoryRecord value1(Long value) {
        setCategoryId(value);
        return this;
    }

    @Override
    public CategoryRecord value2(String value) {
        setCategoryName(value);
        return this;
    }

    @Override
    public CategoryRecord value3(BigDecimal value) {
        setNecessity(value);
        return this;
    }

    @Override
    public CategoryRecord values(Long value1, String value2, BigDecimal value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CategoryRecord
     */
    public CategoryRecord() {
        super(Category.CATEGORY);
    }

    /**
     * Create a detached, initialised CategoryRecord
     */
    public CategoryRecord(Long categoryId, String categoryName, BigDecimal necessity) {
        super(Category.CATEGORY);

        set(0, categoryId);
        set(1, categoryName);
        set(2, necessity);
    }
}
package com.normalworks.common.utils.currency;

import com.normalworks.common.utils.AssertUtil;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * Money
 * 多币种标准货币单位
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年11月29日 9:06 上午
 */
public class Money implements Serializable, Comparable<Money>, Cloneable {

    private static final long serialVersionUID = 1025877350004283992L;

    public static final int DEFAULT_ROUNDING_MODE = 6;

    private static final int[] centFactors = new int[]{1, 10, 100, 1000, 10000, 100000};

    /**
     * 货币最小单位值
     */
    private final long cent;

    /**
     * 货币单位
     */
    private final Currency currency;

    /**
     * 货币单位值 156
     */
    private final String currencyValue;

    /**
     * 构造函数
     *
     * @param cent     货币最小单位值
     * @param currency 货币单位
     */
    public Money(long cent, Currency currency) {
        this.cent = cent;
        CurrencyEnum supportCurrency = CurrencyEnum.getByCurrencyCode(currency.getCurrencyCode());
        AssertUtil.notNull(supportCurrency, CommonResultCode.PARAM_ILLEGAL, "currency is not support:" + currency);
        this.currencyValue = supportCurrency.getCurrencyValue();
        this.currency = currency;
    }

    /**
     * 构造函数
     *
     * @param amount   12.50 币值
     * @param currency CNY 币种
     */
    public Money(BigDecimal amount, Currency currency) {
        this(amount, currency, DEFAULT_ROUNDING_MODE);
    }


    /**
     * 构造函数
     *
     * @param amount   币值
     * @param currency 货币单位
     */
    public Money(String amount, Currency currency) {
        this(new BigDecimal(amount), currency);
    }

    /**
     * 私有构造函数
     *
     * @param amount       币值
     * @param currency     货币
     * @param roundingMode 精度
     */
    private Money(BigDecimal amount, Currency currency, int roundingMode) {
        this.cent = this.rounding(amount.movePointRight(currency.getDefaultFractionDigits()), roundingMode);
        CurrencyEnum supportCurrency = CurrencyEnum.getByCurrencyCode(currency.getCurrencyCode());
        AssertUtil.notNull(supportCurrency, CommonResultCode.PARAM_ILLEGAL, "currency is not support:" + currency);
        this.currencyValue = supportCurrency.getCurrencyValue();
        this.currency = currency;
    }

    /**
     * 货币加法，加到new的货币
     *
     * @param money
     * @return
     */
    public Money add(Money money) {
        AssertUtil.isTrue(this.currency.equals(money.currency), CommonResultCode.PARAM_ILLEGAL, "Money currency is not mismatch");
        return this.newMoneyWithSameCurrency(this.cent + money.cent);
    }

    /**
     * 货币减法，减到new的货币
     *
     * @param money
     * @return
     */
    public Money subtract(Money money) {
        AssertUtil.isTrue(this.currency.equals(money.currency), CommonResultCode.PARAM_ILLEGAL, "Money currency is not mismatch");
        return this.newMoneyWithSameCurrency(this.cent - money.cent);
    }

    /**
     * 货币乘法，乘到new的货币
     *
     * @param val 乘法系数
     * @return
     */
    public Money multiply(long val) {
        return this.newMoneyWithSameCurrency(this.cent * val);
    }

    /**
     * 货币乘法，乘到new的货币
     *
     * @param val 乘法系数
     * @return
     */
    public Money multiply(BigDecimal val) {
        return this.multiply(val, DEFAULT_ROUNDING_MODE);
    }


    /**
     * 货币除法，除到new的货币
     *
     * @param val 除法系数
     * @return
     */
    public Money divide(BigDecimal val) {
        return this.divide(val, DEFAULT_ROUNDING_MODE);
    }

    public boolean equals(Object obj) {
        return obj instanceof Money && this.equals((Money) obj);
    }

    public boolean equals(Money money) {
        return this.currency.equals(money.currency) && this.cent == money.cent;
    }

    public int hashCode() {
        return (int) (this.cent ^ this.cent >>> 32);
    }

    public Money clone() {
        return new Money(this.getAmount(), this.getCurrency());
    }

    public boolean greaterThan(Money money) {
        return this.compareTo(money) > 0;
    }

    public boolean greaterThanOrEquals(Money money) {
        return this.compareTo(money) >= 0;
    }

    @Override
    public int compareTo(Money money) {
        AssertUtil.isTrue(this.currency.equals(money.currency), CommonResultCode.PARAM_ILLEGAL, "Money currency is not mismatch");
        if (this.cent < money.cent) {
            return -1;
        } else {
            return this.cent == money.cent ? 0 : 1;
        }
    }


    public String getCurrencyCode() {
        return this.currency.getCurrencyCode();
    }

    public String fetchAmountStr() {
        return this.getAmount() == null ? "" : this.getAmount().toString();
    }

    public BigDecimal getAmount() {
        return BigDecimal.valueOf(this.cent, currency.getDefaultFractionDigits());
    }

    public final int getCentFactor() {
        return centFactors[currency.getDefaultFractionDigits()];
    }

    protected long rounding(BigDecimal val, int roundingMode) {
        return val.setScale(0, roundingMode).longValue();
    }

    protected Money newMoneyWithSameCurrency(long cent) {
        return new Money(cent, this.currency);
    }

    public long getCent() {
        return cent;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getCurrencyValue() {
        return currencyValue;
    }

    public CurrencyEnum getCurrencyEnum() {
        return CurrencyEnum.getByCurrencyCode(this.getCurrencyCode());
    }


    private Money multiply(BigDecimal val, int roundingMode) {
        BigDecimal newCent = BigDecimal.valueOf(this.cent).multiply(val);
        return newMoneyWithSameCurrency(this.rounding(newCent, roundingMode));
    }

    /**
     * 为了支持Xero计算tax的规则（向下取整），将roundingMode开放出来
     */
    public Money divide(BigDecimal val, int roundingMode) {
        BigDecimal newCent = BigDecimal.valueOf(this.cent).divide(val, roundingMode);
        return newMoneyWithSameCurrency(newCent.longValue());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

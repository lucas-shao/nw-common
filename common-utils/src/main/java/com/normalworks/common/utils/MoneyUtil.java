package com.normalworks.common.utils;

import com.normalworks.common.utils.assertion.CommonResultCode;
import com.normalworks.common.utils.currency.CurrencyEnum;
import com.normalworks.common.utils.currency.Money;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * MoneyUtil
 * 多币种工具类
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年11月29日 9:18 上午
 */
public class MoneyUtil {

    /**
     * 金额本地化输出
     *
     * @param money  金额
     * @param locale 本地化
     * @return 金额本地化输出
     */
    public static String formatByLocale(Money money, Locale locale) {
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        format.setCurrency(money.getCurrency());
        return format.format(money.getAmount().doubleValue());
    }

    /**
     * 生成一个指定币种的0元实例
     *
     * @param currency
     * @return
     */
    public static Money getZeroAmount(CurrencyEnum currency) {
        return new Money(0, getCurrency(currency));
    }

    /**
     * 金额是否为0
     */
    public static boolean isZero(Money money) {
        return money.getCent() == 0;
    }

    /**
     * 两个金额相加，返回相加的结果，不改变入参
     *
     * @param money1
     * @param money2
     * @return
     */
    public static Money add(Money money1, Money money2) {
        AssertUtil.isTrue(StringUtils.equals(money1.getCurrency().getCurrencyCode(), money2.getCurrency().getCurrencyCode()), CommonResultCode.PARAM_ILLEGAL, "两个金额币种不一致，无法相加");
        return new Money(money1.getCent() + money2.getCent(), money1.getCurrency());
    }

    /**
     * 金额取绝对值
     *
     * @param money 金额
     * @return
     */
    public static Money abs(Money money) {
        return new Money(Math.abs(money.getCent()), money.getCurrency());
    }


    /**
     * 减法，不改变入参
     *
     * @param money1
     * @param money2
     * @return
     */
    public static Money subtract(Money money1, Money money2) {
        AssertUtil.isTrue(StringUtils.equals(money1.getCurrency().getCurrencyCode(), money2.getCurrency().getCurrencyCode()), CommonResultCode.PARAM_ILLEGAL, "两个金额币种不一致，无法相减");
        return new Money(money1.getCent() - money2.getCent(), money1.getCurrency());
    }

    /**
     * 初始化一个金额对象
     */
    public static Money init(Long cent, CurrencyEnum currency) {
        return new Money(cent, getCurrency(currency));
    }

    /**
     * 换汇
     *
     * @param baseMoney      基准金额 100JPY
     * @param targetCurrency 目标币种 CNY
     * @param exchangeRate   基准币种/目标币种汇率 0.050
     */
    public static Money exchange(Money baseMoney, CurrencyEnum targetCurrency, Double exchangeRate) {
        Money targetSameCurrencyMoney = baseMoney.multiply(BigDecimal.valueOf(exchangeRate));
        Money targetMoney = new Money(targetSameCurrencyMoney.getAmount(), getCurrency(targetCurrency));
        return targetMoney;
    }

    public static Money init(Long cent, String currencyCode) {
        return new Money(cent, Currency.getInstance(currencyCode));
    }

    public static Money init(Double amount, CurrencyEnum currency) {
        BigDecimal amountInBigDecimal = new BigDecimal(amount);
        return new Money(amountInBigDecimal, getCurrency(currency));
    }

    private static Currency getCurrency(CurrencyEnum currency) {
        return Currency.getInstance(currency.getCurrencyCode());
    }

    public static void main(String[] args) {
        Money money = init(10000l, CurrencyEnum.JPY.getCurrencyCode());
        System.out.println(formatByLocale(money, Locale.US));

        Money targetMoney = exchange(money, CurrencyEnum.CNY, 0.050);
        System.out.println(formatByLocale(targetMoney, Locale.US));
    }
}

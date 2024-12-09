package com.normalworks.common.utils;

import com.normalworks.common.utils.currency.CurrencyEnum;
import com.normalworks.common.utils.currency.Money;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class MoneyTest {

    @Test
    public void testMultiple() {
        Money amount = MoneyUtil.init(9999L, CurrencyEnum.GBP);
        System.out.println(amount);
        Money amount2 = amount.multiply(-1);
        System.out.println(amount2);
        Money amount3 = amount2.multiply(-1);
        assertEquals(amount, amount3);
    }

    @Test
    public void testAdd() {
        Money amount1 = MoneyUtil.init(2345L, CurrencyEnum.GBP);
        Money amount2 = MoneyUtil.init(1234L, CurrencyEnum.GBP);
        Money amount3 = amount1.add(amount2);
        assertEquals(amount3.getCent(), amount1.getCent() + amount2.getCent());
    }

    @Test
    public void testSubtract() {
        Money amount1 = MoneyUtil.init(2345L, CurrencyEnum.GBP);
        Money amount2 = MoneyUtil.init(-1234L, CurrencyEnum.GBP);
        Money amount3 = amount1.subtract(amount2);
        assertEquals(amount3.getCent(), amount1.getCent() - amount2.getCent());
    }

    @Test
    public void testDivide() {
        Money amount1 = MoneyUtil.init(2344L, CurrencyEnum.GBP);
        Money amount2 = amount1.divide(BigDecimal.valueOf(-2L));
        assertEquals(amount2.getCent(), amount1.getCent() / -2);
    }
}

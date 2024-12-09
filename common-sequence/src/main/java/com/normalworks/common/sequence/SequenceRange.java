package com.normalworks.common.sequence;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.concurrent.atomic.AtomicLong;

/**
 * SequenceRange
 * 数据库序列
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年12月19日 1:43 下午
 */
public class SequenceRange {

    /**
     * 区间最小值
     */
    private final long minValue;

    /**
     * 区间最大值
     */
    private final long maxValue;

    /**
     * 当前值
     */
    private final AtomicLong currentValue;

    public SequenceRange(long minValue, long maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = new AtomicLong(minValue);
    }

    /**
     * 获取当前序列值，并将序列值递增
     *
     * @return
     */
    public long fetchAndIncrementCurrentValue() {
        long currentValue = this.currentValue.getAndIncrement();
        if (currentValue > maxValue) {
            return SequenceConstants.OVER_FLOW_SEQ_RANGE;
        }
        return currentValue;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

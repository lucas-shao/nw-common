package com.normalworks.common.sequence;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * Sequence
 * 序列
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年12月19日 2:36 下午
 */
public class Sequence {

    private Long id;

    private String sequenceName;

    private Long currentValue;

    private Long incrementValue;

    private Long minimum;

    private Long maximum;

    private Date createTime;

    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public Long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Long currentValue) {
        this.currentValue = currentValue;
    }

    public Long getIncrementValue() {
        return incrementValue;
    }

    public void setIncrementValue(Long incrementValue) {
        this.incrementValue = incrementValue;
    }

    public Long getMinimum() {
        return minimum;
    }

    public void setMinimum(Long minimum) {
        this.minimum = minimum;
    }

    public Long getMaximum() {
        return maximum;
    }

    public void setMaximum(Long maximum) {
        this.maximum = maximum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}

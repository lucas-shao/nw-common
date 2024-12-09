package com.normalworks.common.model.service;


import java.util.Date;

/**
 * GenericPageQueryResult
 * 分页查询返回值
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年11月15日 3:35 下午
 */
public class GenericPageQueryResult<T> extends GenericResult<T> {

    private static final long serialVersionUID = -1869119889448712779L;

    /**
     * 排序类型，不填默认为倒序。DESC/倒序查询 ASC/顺序查询
     */
    private OrderTypeEnum orderType;

    /**
     * 倒序查询，本次查询最小的序列值，即下次批量查询的maxIndex值
     */
    private String index;

    /**
     * 倒序查询，本次查询最早的日期，即下次查询的maxDate值
     */
    private Date date;

    /**
     * 倒序查询，本次查询最小的金额，即消磁查询的maxCent值
     */
    private Long cent;

    /**
     * 总数量
     */
    private Long sum;

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public OrderTypeEnum getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderTypeEnum orderType) {
        this.orderType = orderType;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getCent() {
        return cent;
    }

    public void setCent(Long cent) {
        this.cent = cent;
    }
}

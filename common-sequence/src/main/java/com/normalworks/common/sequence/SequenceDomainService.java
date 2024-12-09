package com.normalworks.common.sequence;

/**
 * SequenceDomainService
 * 序列领域服务
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年12月19日 1:47 下午
 */
public interface SequenceDomainService {

    /**
     * 获取该序列名称对应的新的序列区间
     *
     * @param sequenceName 序列名称
     * @return
     */
    SequenceRange fetchNewSequenceRange(String sequenceName);
}

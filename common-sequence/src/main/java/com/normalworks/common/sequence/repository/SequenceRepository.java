package com.normalworks.common.sequence.repository;

import com.normalworks.common.sequence.Sequence;

import java.util.List;

/**
 * SequenceRepository
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年12月19日 2:37 下午
 */
public interface SequenceRepository {

    /**
     * 获取全量的序列配置
     *
     * @return
     */
    List<Sequence> queryAllSequence();

    /**
     * 通过序列名称锁定
     *
     * @param sequenceName 序列名称
     * @return
     */
    Sequence activeBySequenceName(String sequenceName);

    /**
     * 更新序列
     *
     * @param sequence 序列
     * @return
     */
    int update(Sequence sequence);
}

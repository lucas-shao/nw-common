package com.normalworks.common.sequence;

import com.normalworks.common.sequence.repository.SequenceRepository;
import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SequenceHolder
 * 序列生成器
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年12月19日 1:36 下午
 */
public class SequenceHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger("COMMON-DAL-SEQUENCE");

    /**
     * 锁
     */
    private final Lock lock = new ReentrantLock();

    /**
     * 序列名称
     */
    private String sequenceName;

    /**
     * 序列区间
     */
    private SequenceRange sequenceRange;

    private SequenceDomainService sequenceDomainService;

    private SequenceRepository sequenceRepository;

    /**
     * 获取该序列下的下一个值
     *
     * @return
     */
    public long getNextValue() {

        long currentValue = sequenceRange.fetchAndIncrementCurrentValue();
        if (currentValue == SequenceConstants.OVER_FLOW_SEQ_RANGE) {
            try {
                lock.lock();
                currentValue = sequenceRange.fetchAndIncrementCurrentValue();
                if (currentValue != SequenceConstants.OVER_FLOW_SEQ_RANGE) {
                    return currentValue;
                } else {
                    sequenceRange = retryFetchRange();
                    currentValue = sequenceRange.fetchAndIncrementCurrentValue();
                }
            } finally {
                lock.unlock();
            }
        }
        return currentValue;
    }

    /**
     * 初始化序列Holder
     */
    public void init(String sequenceName, SequenceRepository sequenceRepository, SequenceDomainService sequenceDomainService) {
        this.sequenceName = sequenceName;
        this.sequenceRepository = sequenceRepository;
        this.sequenceDomainService = sequenceDomainService;
        this.sequenceRange = initEmptySequenceRange();
        LOGGER.info("初始化" + sequenceName + "序列成功");
    }

    /**
     * 表数量较多，每次启动时初始化所有表sequence range耗时超过2分钟
     * 改为懒加载，第一次获取时再初始化
     */
    private SequenceRange initEmptySequenceRange() {
        return new SequenceRange(1, 0);
    }

    private SequenceRange retryFetchRange() {
        for (int i = 0; i < SequenceConstants.FETCH_SEQ_RANGE_RETRY_TIMES; i++) {
            SequenceRange sequenceRangeTmp = null;

            try {
                sequenceRangeTmp = sequenceDomainService.fetchNewSequenceRange(sequenceName);
            } catch (Throwable throwable) {
                LOGGER.error("尝试获取新的序列区间报错", throwable);
            }

            if (null == sequenceRangeTmp) {
                LOGGER.error("尝试执行第" + (i + 1) + "次，获取" + sequenceName + "序列异常，无法获取指定的数据");
                continue;
            } else {
                return sequenceRangeTmp;
            }
        }
        throw new AssertionException(CommonResultCode.SYSTEM_EXCEPTION, "尝试多次获取序列" + sequenceName + "异常");
    }
}

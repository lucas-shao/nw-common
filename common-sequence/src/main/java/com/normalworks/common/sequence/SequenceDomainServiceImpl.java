package com.normalworks.common.sequence;

import com.normalworks.common.sequence.repository.SequenceRepository;
import com.normalworks.common.utils.AssertUtil;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * SequenceDomainServiceImpl
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年12月19日 3:44 下午
 */
@Service
public class SequenceDomainServiceImpl implements SequenceDomainService {

    private static final Logger LOGGER = LoggerFactory.getLogger("COMMON-DAL-SEQUENCE");

    @Resource
    private SequenceRepository sequenceRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public SequenceRange fetchNewSequenceRange(String sequenceName) {

        Sequence sequence = sequenceRepository.activeBySequenceName(sequenceName);
        AssertUtil.notNull(sequence, CommonResultCode.PARAM_ILLEGAL, "序列不存在，sequenceName = " + sequenceName);

        long newMinValue = sequence.getCurrentValue();
        long newMaxValue = sequence.getCurrentValue() + sequence.getIncrementValue();
        if (newMaxValue > sequence.getMaximum()) {
            newMaxValue = sequence.getMaximum();

            // 重新开始循环计数
            sequence.setCurrentValue(1L);
        } else {
            sequence.setCurrentValue(newMaxValue);
        }
        int result = sequenceRepository.update(sequence);
        if (result > 0) {
            LOGGER.info("获取序列" + sequenceName + "的数据成功，新的序列范围为[" + newMinValue + "," + (newMaxValue - 1) + "]");
            SequenceRange sequenceRange = new SequenceRange(newMinValue, newMaxValue - 1);
            return sequenceRange;
        } else {
            LOGGER.error("获取" + sequenceName + "的数据失败，数据库中没有查询到此数据");
            return null;
        }
    }
}

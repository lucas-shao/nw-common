package com.normalworks.common.sequence;

import com.normalworks.common.sequence.repository.SequenceRepository;
import com.normalworks.common.utils.AssertUtil;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SequenceFactory
 * 序列工厂类
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年12月19日 1:35 下午
 */
@Service
public class SequenceFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger("COMMON-DAL-SEQUENCE");

    private final Lock lock = new ReentrantLock();

    private final Map<String, SequenceHolder> sequenceHolderMap = new ConcurrentHashMap<>();

    @Resource
    private SequenceRepository sequenceRepository;

    @Resource
    private SequenceDomainService sequenceDomainService;

    /**
     * 获取某序列的下一个值
     *
     * @param sequenceName 序列名称
     * @return
     */
    public long getNextValue(String sequenceName) {

        LOGGER.info("开始获取" + sequenceName + "序列值");

        if (CollectionUtils.isEmpty(sequenceHolderMap)) {
            // 防止系统重启后没有加载，再懒加载一次
            initAll();
        }

        SequenceHolder sequenceHolder = sequenceHolderMap.get(sequenceName);
        AssertUtil.notNull(sequenceHolder, CommonResultCode.PARAM_ILLEGAL, "该序列" + sequenceName + "配置不存在");
        long nextValue = sequenceHolder.getNextValue();
        LOGGER.info("获取" + sequenceName + "序列值完成，获取到的值为" + nextValue);
        return nextValue;
    }

    @PostConstruct
    private void init() {
        LOGGER.info("开始初始化所有的Sequence序列");
        initAll();
        LOGGER.info("初始化所有的Sequence序列完成");
    }

    private void initAll() {

        if (CollectionUtils.isEmpty(sequenceHolderMap)) {
            try {
                lock.lock();
                if (!CollectionUtils.isEmpty(sequenceHolderMap)) {
                    return;
                } else {
                    List<Sequence> sequenceList = sequenceRepository.queryAllSequence();
                    AssertUtil.isTrue(!CollectionUtils.isEmpty(sequenceList), CommonResultCode.PARAM_ILLEGAL, "序列值全量配置查询为空");

                    for (Sequence sequence : sequenceList) {
                        SequenceHolder sequenceHolder = new SequenceHolder();
                        sequenceHolder.init(sequence.getSequenceName(), sequenceRepository, sequenceDomainService);
                        sequenceHolderMap.put(sequence.getSequenceName(), sequenceHolder);
                    }
                }
            } finally {
                lock.unlock();
            }
        }

    }
}

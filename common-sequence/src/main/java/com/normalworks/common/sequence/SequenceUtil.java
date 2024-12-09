package com.normalworks.common.sequence;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * SequenceUtil
 * 序列工具类
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年12月19日 4:19 下午
 */
@Component
public class SequenceUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger("COMMON-DAL-SEQUENCE");

    @Resource
    private SequenceFactory sequenceFactory;

    /**
     * 获取某序列的序列号值
     *
     * @param sequenceName 序列
     * @return
     */
    public String fetchSequenceNo(String sequenceName, int sequenceLength) {
        LOGGER.info("准备获取" + sequenceName + "的序号");
        long nextValue = sequenceFactory.getNextValue(sequenceName);
        String nextSequenceNo = StringUtils.leftPad(String.valueOf(nextValue), sequenceLength, "0");
        LOGGER.info("获取" + sequenceName + "的序列号成功，编号为" + nextSequenceNo);
        return nextSequenceNo;
    }
}

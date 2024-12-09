package com.normalworks.common.task.converter;


import com.alibaba.fastjson.JSON;
import com.normalworks.common.task.model.RetryTask;
import com.normalworks.common.task.model.RetryTaskConfig;
import com.normalworks.common.task.model.dal.RetryTaskDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * RetryTaskConverter
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年12月08日 9:17 上午
 */
public class RetryTaskConverter {

    public static List<RetryTask> batchToDomain(List<RetryTaskDO> retryTaskDOList) {
        List<RetryTask> retryTaskList = new ArrayList<>();
        if (CollectionUtils.isEmpty(retryTaskDOList)) {
            return retryTaskList;
        }

        for (RetryTaskDO retryTaskDO : retryTaskDOList) {
            RetryTask retryTask = RetryTaskConverter.toDomain(retryTaskDO);
            retryTaskList.add(retryTask);
        }
        return retryTaskList;
    }

    public static RetryTask toDomain(RetryTaskDO retryTaskDO) {
        if (null == retryTaskDO) {
            return null;
        }
        RetryTask retryTask = new RetryTask();
        retryTask.setTaskId(retryTaskDO.getId() != null ? retryTaskDO.getId().toString() : null);
        retryTask.setBusinessId(retryTaskDO.getBusinessId());
        retryTask.setRetryTimes(retryTaskDO.getRetryTimes());
        retryTask.setExtInfo(JSON.parseObject(retryTaskDO.getExtInfo(), HashMap.class));
        retryTask.setNextExecuteTime(retryTaskDO.getNextExecuteTime());
        retryTask.setCreateTime(retryTaskDO.getCreateTime());
        retryTask.setModifyTime(retryTaskDO.getModifyTime());

        RetryTaskConfig retryTaskConfig = new RetryTaskConfig();
        retryTaskConfig.setRetryTaskCode(retryTaskDO.getRetryTaskCode());
        retryTask.setRetryTaskConfig(retryTaskConfig);

        return retryTask;
    }

    public static RetryTaskDO toDO(RetryTask retryTask) {
        if (null == retryTask) {
            return null;
        }

        RetryTaskDO retryTaskDO = new RetryTaskDO();
        retryTaskDO.setId(StringUtils.isNotBlank(retryTask.getTaskId()) ? Long.valueOf(retryTask.getTaskId()) : null);
        retryTaskDO.setRetryTaskCode(retryTask.getRetryTaskConfig() != null ? retryTask.getRetryTaskConfig().getRetryTaskCode() : null);
        retryTaskDO.setBusinessId(retryTask.getBusinessId());
        retryTaskDO.setRetryTimes(retryTask.getRetryTimes());
        retryTaskDO.setExtInfo(JSON.toJSONString(retryTask.getExtInfo()));
        retryTaskDO.setNextExecuteTime(retryTask.getNextExecuteTime());
        retryTaskDO.setCreateTime(retryTask.getCreateTime());
        retryTaskDO.setModifyTime(retryTask.getModifyTime());

        return retryTaskDO;
    }
}

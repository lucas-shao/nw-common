package com.normalworks.common.shorturlcode;

import com.normalworks.common.utils.AssertUtil;
import com.normalworks.common.utils.ContextUtil;
import com.normalworks.common.utils.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * ShortUrlCodeService
 *
 * @author: lingeng
 * @date: 07/03/2023
 */
@Service
public class ShortUrlCodeService {

    private Logger LOGGER = LoggerFactory.getLogger(ShortUrlCodeService.class);
    @Resource
    private ShortUrlCodeRepository shortUrlCodeRepository;

    @Resource
    private ShortUrlCodeConfig shortUrlCodeConfig;

    /**
     * 长链换短链
     */
    public ShortUrlCodeRecord longToShort(String longUrl) {

        //统一使用小写字母
        String lowerCaseLongUrl = longUrl.toLowerCase();

        ShortUrlCodeRecord record = shortUrlCodeRepository.queryByLongUrl(lowerCaseLongUrl);
        if (record == null) {
            record = initRecord(lowerCaseLongUrl);
            try {
                shortUrlCodeRepository.create(record);
            } catch (DuplicateKeyException e) {
                LoggerUtil.error(e, "存储短链码，数据库幂等异常。record=", record);
                return null;
            }
        } else {
            LoggerUtil.info(LOGGER, "长链已经存在。record=", record);
        }

        return record;
    }

    /**
     * 短链换长链
     * 由业务层通过是否为空判断短链码是否存在以及后续处理逻辑
     */
    public ShortUrlCodeRecord shortToLong(String shortCode) {
        return shortUrlCodeRepository.queryByShortCode(shortCode);
    }

    /**
     * 获取新的短链码
     */
    private String getNewShortCode() {

        String shortCode = null;
        ShortUrlCodeRecord existRecord = null;
        int tryTimes = 0;

        /**
         * 如果短码存在，则尝试重新生成
         */
        do {
            shortCode = generateShortCode();
            existRecord = shortUrlCodeRepository.queryByShortCode(shortCode);
            LoggerUtil.info(LOGGER, "生成短码=", shortCode, "，查询短码是否存在，返回record=", existRecord);
            tryTimes++;
        } while (existRecord != null && (tryTimes < shortUrlCodeConfig.getTryMaxTimes()));

        AssertUtil.notBlank(shortCode, ShortUrlCodeErrorCode.GENERATE_SHORT_CODE_ERROR, "生成短码失败，重试次数：", tryTimes);
        return shortCode;
    }

    /**
     * 生成短链码，规格：指定长度且全部小写
     */
    private String generateShortCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, shortUrlCodeConfig.getShortCodeLength()).toLowerCase();
    }

    private ShortUrlCodeRecord initRecord(String longUrl) {
        ShortUrlCodeRecord record = new ShortUrlCodeRecord();
        record.setLongUrl(longUrl);
        String shortCode = getNewShortCode();
        record.setShortCode(shortCode);
        record.setCreateTime(ContextUtil.getTime());
        record.setModifyTime(ContextUtil.getTime());
        return record;
    }
}

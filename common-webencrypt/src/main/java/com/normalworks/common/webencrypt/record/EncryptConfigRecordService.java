package com.normalworks.common.webencrypt.record;

import com.normalworks.common.utils.LoggerUtil;
import com.normalworks.common.webencrypt.record.repository.EncryptConfigRecordRepository;
import com.normalworks.common.webencrypt.util.AESUtil;
import com.normalworks.common.webencrypt.util.RSAUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * EncryptConfigRecordService
 *
 * @author: lingeng
 * @date: 10/18/22
 */
@Service
public class EncryptConfigRecordService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EncryptConfigRecordService.class);

    private static final int RSA_VALID_PERIOD_BY_MINUTE = 7 * 24 * 60;

    /**
     * AES密钥长期有效期，10年
     * P.S 由于TIMESTAMP只能到2038年，所以有效期调整为10年
     */
    private static final int AES_VALID_PERIOD_BY_MINUTE = 10 * 365 * 24 * 60;

    @Resource
    private EncryptConfigRecordRepository encryptConfigRecordRepository;

    /**
     * TODO 重命名方法
     * 查询某个客户端最新的有效的秘钥信息
     */
    public EncryptConfigRecord fetchServerRsaPublicKey(String clientIp) {


        EncryptConfigRecord record = encryptConfigRecordRepository.queryLatestValidApiDataEncryptConfigRecord(clientIp, new Date());

        LoggerUtil.info(LOGGER, "queryLatestValidApiDataByClientIp,record=", record);

        if (null == record) {
            // 重新生成RSA公私钥对
            Map<String, String> keyMap = RSAUtil.initKey();
            String publicKeyString = keyMap.get(RSAUtil.PUBLIC_KEY_STR);
            String privateKeyString = keyMap.get(RSAUtil.PRIVATE_KEY_STR);

            record = new EncryptConfigRecord();
            record.setClientIp(clientIp);
            record.setRsaKeyId(UUID.randomUUID().toString());
            record.setRsaPublicKey(publicKeyString);
            record.setRsaPrivateKey(privateKeyString);
            record.setRsaInvalidTime(DateUtils.addMinutes(new Date(), RSA_VALID_PERIOD_BY_MINUTE));

            encryptConfigRecordRepository.insert(record);
            LoggerUtil.debug(LOGGER, "record after insert, record=", record);
        }

        return record;
    }

    /**
     * 查询服务端的AES 秘钥信息
     *
     * @param serverRsaKeyId     服务端的RSA key id
     * @param clientRsaPublicKey 客户端生成的RSA 公钥
     * @return
     */
    public EncryptConfigRecord fetchServerAesKey(String serverRsaKeyId, String clientRsaPublicKey) {
        EncryptConfigRecord record = encryptConfigRecordRepository.queryByRsaKeyId(serverRsaKeyId);

        if (record == null) {
            return null;
        }

        if (StringUtils.isBlank(record.getAesKeyId())) {
            // 还未生成AES密钥
            String aesKey = AESUtil.initHexKey();
            record.setAesKeyId(UUID.randomUUID().toString());
            record.setAesKey(aesKey);
            record.setAesInvalidTime(DateUtils.addMinutes(new Date(), AES_VALID_PERIOD_BY_MINUTE));
            encryptConfigRecordRepository.updateAesKey(record);
        }

        if (StringUtils.isNotBlank(clientRsaPublicKey)) {
            fillEncryptedAesKey(record, clientRsaPublicKey);
        }
        return record;
    }

    /**
     * 通过AESKeyId查询AES 秘钥信息
     *
     * @param aesKeyId
     * @param clientRsaPublicKey 客户端生成的RSA 公钥
     * @return
     */
    public EncryptConfigRecord queryServerAesKeyByAesKeyId(String aesKeyId, String clientRsaPublicKey) {


        EncryptConfigRecord record = encryptConfigRecordRepository.queryByAesKeyId(aesKeyId);

        if (null == record) {
            return null;
        } else {
            fillEncryptedAesKey(record, clientRsaPublicKey);
            return record;
        }
    }

    public EncryptConfigRecord fetchAesKey() {
        EncryptConfigRecord apiDataEncryptConfigRecord = fetchServerRsaPublicKey("127.0.0.1");
        apiDataEncryptConfigRecord = fetchServerAesKey(apiDataEncryptConfigRecord.getRsaKeyId(), null);

        return apiDataEncryptConfigRecord;
    }


    /**
     * 给AES密钥使用客户端RSA公钥加密
     *
     * @param record
     * @param clientRsaPublicKey
     * @return
     */
    private void fillEncryptedAesKey(EncryptConfigRecord record, String clientRsaPublicKey) {
        String encryptedAesKey = RSAUtil.encryptByPubKey(record.getAesKey(), clientRsaPublicKey);
        record.setEncryptedAesKey(encryptedAesKey);
    }
}

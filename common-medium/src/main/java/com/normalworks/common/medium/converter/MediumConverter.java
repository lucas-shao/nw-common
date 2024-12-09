package com.normalworks.common.medium.converter;

import com.alibaba.fastjson.JSON;
import com.normalworks.common.utils.enums.HttpContentTypeEnum;
import com.normalworks.common.medium.enums.MediumStatusEnum;
import com.normalworks.common.medium.enums.MediumTypeEnum;
import com.normalworks.common.medium.model.File;
import com.normalworks.common.medium.model.Image;
import com.normalworks.common.medium.model.Medium;
import com.normalworks.common.medium.model.dal.MediumDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * MediumConverter
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年11月21日 11:18 上午
 */
public class MediumConverter {

    public static Medium toDomain(MediumDO mediumDO) {

        if (null == mediumDO) {
            return null;
        }
        MediumTypeEnum mediumType = MediumTypeEnum.valueOf(mediumDO.getMediumType());
        switch (mediumType) {
            case IMAGE:
                Image image = ImageConverter.toDomain(mediumDO);
                return image;
            case FILE:
                File file = FileConverter.toDomain(mediumDO);
                return file;
        }
        return null;
    }

    public static List<Medium> batchToDomain(List<MediumDO> mediumDOList) {

        List<Medium> mediumList = new ArrayList<>();
        if (CollectionUtils.isEmpty(mediumDOList)) {
            return mediumList;
        }

        for (MediumDO mediumDO : mediumDOList) {
            Medium medium=toDomain(mediumDO);
            mediumList.add(medium);
        }
        return mediumList;
    }

    public static Medium toBaseDomain(MediumDO mediumDO) {

        if (null == mediumDO) {
            return null;
        }

        Medium medium = new Medium();
        medium.setMediumId(mediumDO.getId() != null ? mediumDO.getId().toString() : null);
        medium.setMediumName(mediumDO.getMediumName());
        medium.setMediumType(MediumTypeEnum.getByValue(mediumDO.getMediumType()));
        medium.setMediumDigest(mediumDO.getMediumDigest());
        medium.setMediumSize(mediumDO.getMediumSize());
        medium.setUploadUserId(mediumDO.getUploadUserId());
        medium.setMediumStatus(MediumStatusEnum.getByValue(mediumDO.getMediumStatus()));
        medium.setMediumContentType(HttpContentTypeEnum.getByRawContentType(mediumDO.getMediumContentType()));
        medium.setAuditFailedDetails(mediumDO.getAuditFailedDetails());
        medium.setExtInfo(JSON.parseObject(mediumDO.getExtInfo(), HashMap.class));
        medium.setCreateTime(mediumDO.getCreateTime());
        medium.setModifyTime(mediumDO.getModifyTime());

        return medium;
    }

    public static MediumDO toBaseDO(Medium medium) {

        if (null == medium) {
            return null;
        }

        MediumDO mediumDO = new MediumDO();
        mediumDO.setId(StringUtils.isNotBlank(medium.getMediumId()) ? Long.valueOf(medium.getMediumId()) : null);
        mediumDO.setMediumName(medium.getMediumName());
        mediumDO.setMediumType(medium.getMediumType() != null ? medium.getMediumType().getValue() : null);
        mediumDO.setMediumDigest(medium.getMediumDigest());
        mediumDO.setMediumSize(medium.getMediumSize());
        mediumDO.setUploadUserId(medium.getUploadUserId());
        mediumDO.setMediumStatus(medium.getMediumStatus() != null ? medium.getMediumStatus().getValue() : null);
        mediumDO.setMediumContentType(HttpContentTypeEnum.getRawContentType(medium.getMediumContentType()));
        mediumDO.setAuditFailedDetails(medium.getAuditFailedDetails());
        mediumDO.setExtInfo(JSON.toJSONString(medium.getExtInfo()));
        mediumDO.setCreateTime(medium.getCreateTime());
        mediumDO.setModifyTime(medium.getModifyTime());

        return mediumDO;
    }
}

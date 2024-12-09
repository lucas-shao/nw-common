package com.normalworks.common.medium.converter;

import com.normalworks.common.medium.model.File;
import com.normalworks.common.medium.model.Medium;
import com.normalworks.common.medium.model.dal.MediumDO;
import org.springframework.beans.BeanUtils;

/**
 * FileConverter
 *
 * @author: lingeng
 * @date: 7/21/22
 */
public class FileConverter {

    public static MediumDO toDO(File file) {

        if (file == null) {
            return null;
        }

        MediumDO mediumDO = MediumConverter.toBaseDO(file);
        mediumDO.setMediumUrl(file.getFileUrl());

        return mediumDO;
    }

    public static File toDomain(MediumDO mediumDO) {

        if (mediumDO == null) {
            return null;
        }

        File file = new File();
        Medium medium = MediumConverter.toBaseDomain(mediumDO);
        BeanUtils.copyProperties(medium, file);
        file.setFileUrl(mediumDO.getMediumUrl());
        return file;
    }
}

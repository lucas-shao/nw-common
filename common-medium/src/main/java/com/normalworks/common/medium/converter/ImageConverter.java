package com.normalworks.common.medium.converter;

import com.normalworks.common.medium.model.Image;
import com.normalworks.common.medium.model.Medium;
import com.normalworks.common.medium.model.dal.MediumDO;
import org.springframework.beans.BeanUtils;

/**
 * ImageConverter
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年11月21日 11:17 上午
 */
public class ImageConverter {

    public static Image toDomain(MediumDO mediumDO) {

        if (null == mediumDO) {
            return null;
        }

        Image image = new Image();
        Medium medium = MediumConverter.toBaseDomain(mediumDO);
        BeanUtils.copyProperties(medium, image);
        image.setSourceImageUrl(mediumDO.getMediumUrl());
        image.setSourceImageHeight(mediumDO.getImageHeight());
        image.setSourceImageWidth(mediumDO.getImageWidth());
        image.setStandardImageUrl(mediumDO.getStandardMediumUrl());
        image.setBlurImageUrl(mediumDO.getBlurMediumUrl());

        return image;
    }

    public static MediumDO toDO(Image image) {

        if (null == image) {
            return null;
        }

        MediumDO mediumDO = MediumConverter.toBaseDO(image);
        mediumDO.setMediumUrl(image.getSourceImageUrl());
        mediumDO.setImageHeight(image.getSourceImageHeight());
        mediumDO.setImageWidth(image.getSourceImageWidth());
        mediumDO.setStandardMediumUrl(image.getStandardImageUrl());
        mediumDO.setBlurMediumUrl(image.getBlurImageUrl());

        return mediumDO;
    }
}

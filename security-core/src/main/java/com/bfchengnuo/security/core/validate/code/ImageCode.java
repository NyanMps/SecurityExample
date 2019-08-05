package com.bfchengnuo.security.core.validate.code;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

/**
 * 图形验证码的实体
 *
 * @author Created by 冰封承諾Andy on 2019/7/25.
 */
@Getter
@Setter
public class ImageCode extends ValidateCode {
    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expireIn4Second) {
        super(code, expireIn4Second);
        this.image = image;
    }
}

package com.bfchengnuo.security.core.validate.code.image;

import com.bfchengnuo.security.core.validate.code.ValidateCode;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

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

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime){
        super(code, expireTime);
        this.image = image;
    }
}

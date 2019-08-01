package com.bfchengnuo.security.core.validate.code;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 存储验证码的实体
 *
 * @author Created by 冰封承諾Andy on 2019/7/25.
 */
@Data
@AllArgsConstructor
public class ImageCode {
    private BufferedImage image;
    private String code;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    public ImageCode(BufferedImage image, String code, int expireIn4Second) {
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn4Second);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}

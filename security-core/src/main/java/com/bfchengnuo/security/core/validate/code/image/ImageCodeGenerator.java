package com.bfchengnuo.security.core.validate.code.image;

import com.bfchengnuo.security.core.properties.SecurityProperties;
import com.bfchengnuo.security.core.validate.code.ValidateCodeGenerator;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 图形验证码生成具体实现
 * 本实现通过 {@link com.bfchengnuo.security.core.validate.code.ValidateCodeBeanConfig} 注入
 *
 * @see com.bfchengnuo.security.core.validate.code.sms.SmsCodeGenerator 同类实现，通过 @Component 注入
 * @author Created by 冰封承諾Andy on 2019/8/1.
 */
@AllArgsConstructor
public class ImageCodeGenerator implements ValidateCodeGenerator {
    private final SecurityProperties securityProperties;

    @Override
    public ImageCode generate(ServletWebRequest request) {
        int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width",
                securityProperties.getValidateCode().getImage().getWidth());
        int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height",
                securityProperties.getValidateCode().getImage().getHeight());
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));

        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        StringBuilder sRand = new StringBuilder();

        for (int i = 0; i < securityProperties.getValidateCode().getImage().getLength(); i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand.append(rand);
            g.setColor(new Color(20 + random.nextInt(110),
                    20 + random.nextInt(110),
                    20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();

        return new ImageCode(image,
                sRand.toString(),
                securityProperties.getValidateCode().getImage().getExpireIn());
    }

    /**
     * 生成随机的背景条纹
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}

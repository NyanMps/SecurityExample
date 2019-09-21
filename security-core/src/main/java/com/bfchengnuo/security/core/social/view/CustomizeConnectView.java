package com.bfchengnuo.security.core.social.view;

import com.bfchengnuo.security.core.social.wx.config.WeixinAutoConfiguration;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 自定义的账号绑定结果视图
 *
 * @see WeixinAutoConfiguration#weixinConnectedView()
 *
 * @author 冰封承諾Andy
 * @date 2019-09-21
 */
public class CustomizeConnectView extends AbstractView {
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        if (model.get("connections") == null) {
            response.getWriter().write("<h3>解绑成功</h3>");
        } else {
            response.getWriter().write("<h3>绑定成功</h3>");
        }
    }
}

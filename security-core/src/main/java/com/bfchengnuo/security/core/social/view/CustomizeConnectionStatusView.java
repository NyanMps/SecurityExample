package com.bfchengnuo.security.core.social.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义的账号绑定情况视图
 * social 自带的 {@link org.springframework.social.connect.web.ConnectController} 会返回 connect/status 的视图名，还携带了绑定信息
 * 这里的自定义视图来取出相关数据，并组装后返回给前台
 * 访问地址： /connect
 *
 * 认证成功放到 session 里的一定要是 SocialUserDetails
 * 参考 demo 下的 MyUserDetailsService#loadUserByUserId
 *
 * @see org.springframework.social.connect.web.ConnectController#connectionStatus(NativeWebRequest, Model)
 *
 * @author 冰封承諾Andy
 * @date 2019-09-21
 */
@Component("connect/status")
public class CustomizeConnectionStatusView extends AbstractView {
    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) model.get("connectionMap");

        Map<String, Boolean> result = new HashMap<>();
        for (String key : connections.keySet()) {
            result.put(key, CollectionUtils.isNotEmpty(connections.get(key)));
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}

package com.bfchengnuo.secunity.demo.web.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * 用来连接 WireMock 服务，控制 WireMock 服务的具体逻辑
 * <p>
 * WireMock 使用说明：http://wiremock.org/docs/running-standalone/
 *
 * @author Created by 冰封承諾Andy on 2019/7/14.
 */
public class MockServer {
    public static void main(String[] args) throws IOException {
        WireMock.configureFor(8090);
        WireMock.removeAllMappings();

        mock("/user/1", "user.json");
    }

    private static void mock(String url, String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource("mock/response/" + fileName);
        String content = FileUtils.readFileToString(resource.getFile(), "UTF-8");
        WireMock.stubFor(
                WireMock.get(WireMock.urlPathEqualTo(url))
                        .willReturn(
                                WireMock.aResponse()
                                        .withBody(content)
                                        .withStatus(200))
        );
    }
}

package com.bfchengnuo.secunity.demo.web.controller;

import lombok.Cleanup;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 处理文件上传下载等请求
 *
 * @author Created by 冰封承諾Andy on 2019/7/14.
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @PostMapping
    public Map<String, Object> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        System.out.println("Name:" + multipartFile.getName());
        System.out.println("OriginalFilename:" + multipartFile.getOriginalFilename());
        System.out.println("Size:" + multipartFile.getSize());

        File localFile = new File(ResourceUtils.getFile("classpath:upload/"), Objects.requireNonNull(multipartFile.getOriginalFilename()));

        multipartFile.transferTo(localFile);

        Map<String, Object> result = new HashMap<>();
        result.put("path", localFile.getAbsolutePath());

        return result;
    }

    @GetMapping("/{name}")
    public void download(@PathVariable String name, HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = ResourceUtils.getFile("classpath:upload/" + name);
        @Cleanup InputStream is = new FileInputStream(file);
        @Cleanup OutputStream os = response.getOutputStream();

        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment;filename=" + name);
        FileCopyUtils.copy(is, os);
        os.flush();
    }
}

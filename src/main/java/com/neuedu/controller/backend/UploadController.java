package com.neuedu.controller.backend;

import com.neuedu.common.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class UploadController {
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String upload() {
        return "upload"; //前缀+逻辑视图+后缀
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse upload(@RequestParam MultipartFile upload) {
        if(upload != null){
            String fileName = upload.getOriginalFilename();
            //获取图片的扩展名
            int lastIndex = fileName.indexOf(".");
            String fileExtend = fileName.substring(lastIndex);
            //获取文件新名称
            String fileNameNew = UUID.randomUUID().toString() + fileExtend;

            File file = new File("D:\\ftpfile", fileNameNew);
            try {
                //将内存数据写入磁盘
                upload.transferTo(file);
                return ServerResponse.createBySussess("成功", fileNameNew);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

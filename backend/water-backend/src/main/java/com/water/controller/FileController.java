package com.water.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.water.common.R;
import com.water.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/08/30/23:44
 * @Description:
 */
@RestController
@RequestMapping("/files")
public class FileController {

    //文件上传存储路径
    private static final String filePath = System.getProperty("user.dir") + "/file/";


    @Resource
    private UserService userService;

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public R upload(MultipartFile file){

        synchronized (FileController.class) {
            //获取当前时间戳
            String flag = System.currentTimeMillis() + "";
            //获取原始文件名（就是你上传的文件本身的名字）
            String fileName = file.getOriginalFilename();

            try {
                //如果没有file文件夹，会给你在项目根目录下创建一个file文件夹
                if (!FileUtil.isDirectory(filePath)){
                    FileUtil.mkdir(filePath);
                }
                //文件存储形式：时间戳-文件名
                FileUtil.writeBytes(file.getBytes(),filePath + flag + "-" + fileName);
                System.out.println(fileName + "--上传成功");
                Thread.sleep(1L);
            } catch (Exception e) {
                System.err.println(fileName + "--文件上传失败");
            }
            return R.success(flag);
        }
    }

    /**
     * 获取文件
     */
    @GetMapping("/{flag}")
    public void avatarPath(@PathVariable String flag, HttpServletResponse response){
        //去到之前创建的文件夹里面找
        if (!FileUtil.isDirectory(filePath)){
            FileUtil.mkdir(filePath);
        }
        OutputStream os;
        //拿到所有文件
        List<String> fileNames = FileUtil.listFileNames(filePath);
//        String avatar = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        String avatar = "";
        for (String name : fileNames) {
            if (name.contains(flag)) {
                avatar = name;
                break;
            }
        }
        try {
            if (StrUtil.isNotEmpty(avatar)){
                response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(avatar,"UTF-8"));
                response.setContentType("application/octet-stream");
                //读取比特流，用字节数组去存
                byte[] bytes = FileUtil.readBytes(filePath + avatar);
                //用返回流将文件写出
                os = response.getOutputStream();
                os.write(bytes);
                //刷新和关闭流
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }

}














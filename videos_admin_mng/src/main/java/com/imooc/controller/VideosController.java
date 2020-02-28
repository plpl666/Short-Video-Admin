package com.imooc.controller;

import com.imooc.config.ResourceConfig;
import com.imooc.pojo.Bgm;
import com.imooc.service.VideosService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.PagedResult;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/videos")
public class VideosController {

    @Autowired
    private ResourceConfig resourceConfig;

    @Resource
    private VideosService videosService;

    @GetMapping("/showAddBgm")
    public String showAddBgm() {
        return"video/addBgm";
    }

    @GetMapping("/showBgmList")
    public String showBgmList() {
        return "video/bgmList";
    }

    @PostMapping("/addBgm")
    @ResponseBody
    public IMoocJSONResult bgmUpload(@RequestParam("author") String author,
                                     @RequestParam("name") String name,
                                     @RequestBody MultipartFile file) throws IOException {
        String fileSpace = resourceConfig.getFileSpace();
        String uploadPath = File.separator + "Bgm";
        if (file != null && StringUtils.isNotBlank(author) && StringUtils.isNotBlank(name)) {
            FileOutputStream fos = null;
            InputStream is = null;
            String fileName = file.getOriginalFilename();
            if (StringUtils.isNoneBlank(fileName)) {
                //文件上传的最终保存路径(绝对路径)
                String finalPath = fileSpace + uploadPath + File.separator + fileName;
                File outFile = new File(finalPath);
                if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                    outFile.getParentFile().mkdirs();
                }
                fos = new FileOutputStream(outFile);
                is = file.getInputStream();
                IOUtils.copy(is, fos);
                fos.flush();
                fos.close();
                is.close();
            } else {
                return IMoocJSONResult.errorMsg("上传出错...");
            }
            //数据库保存路径
            uploadPath += (File.separator + fileName);
            Bgm bgm = new Bgm();
            bgm.setName(name);
            bgm.setAuthor(author);
            bgm.setPath(uploadPath);
            videosService.addBgm(bgm);
            return IMoocJSONResult.ok();
        } else {
            return IMoocJSONResult.errorMsg("上传出错...");
        }

    }

    @PostMapping("/queryBgmList")
    @ResponseBody
    public PagedResult queryBgmList(Integer page) {
        return videosService.queryBgmList(page,10);
    }

    @PostMapping("/removeBgm")
    @ResponseBody
    public IMoocJSONResult removeBgm(@RequestParam("id") String id) {
        String fileSpace = resourceConfig.getFileSpace();
        String bgmPath = videosService.removeBgm(id);
        try {
            FileUtils.forceDelete(new File(fileSpace + bgmPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return IMoocJSONResult.ok();
    }

    @GetMapping("/showReportList")
    public String showReportList() {
        return "video/reportList";
    }

    @PostMapping("/queryReportList")
    @ResponseBody
    public PagedResult queryReportList(Integer page) {
        return videosService.queryReportList(page,10);
    }

    @PostMapping("/forbidVideo")
    @ResponseBody
    public IMoocJSONResult forbidVideo(String videoId) {
        videosService.updateVideoStatus(videoId);
        return IMoocJSONResult.ok();
    }

}

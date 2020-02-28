package com.imooc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.VideoStatus;
import com.imooc.mapper.BgmMapper;
import com.imooc.mapper.UsersReportExpandMapper;
import com.imooc.mapper.VideosMapper;
import com.imooc.pojo.*;
import com.imooc.service.VideosService;
import com.imooc.enums.BgmOperatorType;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.PagedResult;
import com.imooc.utils.ZKCurator;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class VideosServiceImpl implements VideosService {

    @Resource
    private BgmMapper bgmMapper;

    @Resource
    private UsersReportExpandMapper usersReportExpandMapper;

    @Resource
    private VideosMapper videosMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private ZKCurator zkCurator;

    @Override
    public PagedResult<Bgm> queryBgmList(Integer page, Integer pageSize) {
        Page<Bgm> pages = PageHelper.startPage(page, pageSize);
        BgmExample example = new BgmExample();
        bgmMapper.selectByExample(example);
        PageInfo<Bgm> pageList = pages.toPageInfo();
        return new PagedResult<>(pageList.getPageNum(), pageList.getPages(), pageList.getTotal(), pageList.getList());
    }

    @Override
    public void addBgm(Bgm bgm) {
        bgm.setId(sid.nextShort());
        bgmMapper.insert(bgm);
        Map<String,String> map = new HashMap<>();
        map.put("operType",BgmOperatorType.ADD.getType());
        map.put("path",bgm.getPath());
        zkCurator.sendBgmOperator(bgm.getId(), JsonUtils.objectToJson(map));
    }

    @Override
    public String removeBgm(String id) {
        Bgm bgm = bgmMapper.selectByPrimaryKey(id);
        bgmMapper.deleteByPrimaryKey(id);
        Map<String,String> map = new HashMap<>();
        map.put("operType",BgmOperatorType.DELETE.getType());
        map.put("path",bgm.getPath());
        zkCurator.sendBgmOperator(id, JsonUtils.objectToJson(map));
        return bgm.getPath();
    }

    @Override
    public PagedResult queryReportList(Integer page, Integer pageSize) {
        Page<UsersReport> pages = PageHelper.startPage(page, pageSize);
        usersReportExpandMapper.selectAllUsersReport();
        PageInfo<UsersReport> pageList = pages.toPageInfo();
        return new PagedResult<>(pageList.getPageNum(), pageList.getPages(), pageList.getTotal(), pageList.getList());
    }

    @Override
    public void updateVideoStatus(String videoId) {
        Videos aimVideo = videosMapper.selectByPrimaryKey(videoId);
        Videos video = new Videos();
        video.setId(videoId);
        if (aimVideo.getStatus() == VideoStatus.SUCCESS.value) {
            video.setStatus(VideoStatus.FORBID.value);
        } else if (aimVideo.getStatus() == VideoStatus.FORBID.value) {
            video.setStatus(VideoStatus.SUCCESS.value);
        }
        videosMapper.updateByPrimaryKeySelective(video);
    }
}

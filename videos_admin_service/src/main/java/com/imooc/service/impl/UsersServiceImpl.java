package com.imooc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.UsersExample;
import com.imooc.service.UsersService;
import com.imooc.utils.PagedResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UsersServiceImpl implements UsersService {

    @Resource
    private UsersMapper usersMapper;

    @Override
    public PagedResult queryUserList(Users user, Integer page, Integer pageSize) {
        Page<Users> pages = PageHelper.startPage(page, pageSize);
        UsersExample example = new UsersExample();
        if (user != null) {
            UsersExample.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(user.getUsername())) {
                criteria.andUsernameLike("%" + user.getUsername() + "%");
            }
            if (StringUtils.isNotBlank(user.getNickname())) {
                criteria.andNicknameLike("%" + user.getNickname() + "%");
            }
        }
        usersMapper.selectByExample(example);
        PageInfo<Users> pageList = pages.toPageInfo();
        return new PagedResult<>(pageList.getPageNum(), pageList.getPages(), pageList.getTotal(), pageList.getList());
    }
}

package com.second.hand.trading.server.service.impl;

import com.second.hand.trading.server.dao.AdminDao;
import com.second.hand.trading.server.dao.MessageDao;
import com.second.hand.trading.server.dao.UserDao;
import com.second.hand.trading.server.dao.IdleItemDao;
import com.second.hand.trading.server.model.AdminModel;
import com.second.hand.trading.server.model.IdleItemModel;
import com.second.hand.trading.server.model.MessageModel;
import com.second.hand.trading.server.model.UserModel;
import com.second.hand.trading.server.service.AdminService;

import com.second.hand.trading.server.vo.PageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminDao adminDao;

    @Resource
    private MessageDao messageDao;

    @Resource
    private IdleItemDao idleItemDao;

    @Resource
    private UserDao userDao;

    public AdminModel login(String accountNumber, String adminPassword){
        return adminDao.login(accountNumber,adminPassword);
    }

    public PageVo<AdminModel> getAdminList(int page, int nums){
        List<AdminModel> list=adminDao.getList((page-1)*nums,nums);
        int count=adminDao.getCount();
        return new PageVo<>(list,count);
    }

    public boolean addAdmin(AdminModel adminModel){
        return adminDao.insert(adminModel)==1;
    }


    // AdminServiceImpl.java  实现删除管理员的方法
    public boolean deleteAdmin(Long id) {
        // 防止删除默认的超级管理员（可以根据实际需求调整）
        if (id == 1) {
            return false; // 不允许删除ID为1的默认超级管理员
        }
        return adminDao.deleteByPrimaryKey(id) == 1;
    }

    // MessageServiceImpl.java  实现所有留言的方法
    public PageVo<MessageModel> getAllMessage(int page, int nums) {
        int start = (page - 1) * nums;
        List<MessageModel> list = messageDao.getAllMessage(start, nums);
        int count = messageDao.countAllMessage();

        if(list.size() > 0) {
            // 获取用户信息
            List<Long> userIds = new ArrayList<>();
            List<Long> idleIds = new ArrayList<>();
            for(MessageModel message : list) {
                if(!userIds.contains(message.getUserId())) {
                    userIds.add(message.getUserId());
                }
                if(!idleIds.contains(message.getIdleId())) {
                    idleIds.add(message.getIdleId());
                }
            }

            // 获取用户信息
            List<UserModel> users = userDao.findUserByList(userIds);
            Map<Long, UserModel> userMap = new HashMap<>();
            for(UserModel user : users) {
                userMap.put(user.getId(), user);
            }

            // 获取闲置物品信息
            List<IdleItemModel> idles = idleItemDao.findIdleByList(idleIds);
            Map<Long, IdleItemModel> idleMap = new HashMap<>();
            for(IdleItemModel idle : idles) {
                idleMap.put(idle.getId(), idle);
            }

            // 设置关联信息
            for(MessageModel message : list) {
                message.setFromU(userMap.get(message.getUserId()));
                message.setIdle(idleMap.get(message.getIdleId()));
            }
        }

        return new PageVo<MessageModel>(list, count);
    }

}

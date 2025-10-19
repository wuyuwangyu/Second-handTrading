package com.second.hand.trading.server.service.impl;

import com.second.hand.trading.server.dao.BannerDao;
import com.second.hand.trading.server.dao.IdleItemDao;
import com.second.hand.trading.server.dao.MessageDao;
import com.second.hand.trading.server.dao.UserDao;
import com.second.hand.trading.server.model.IdleItemModel;
import com.second.hand.trading.server.model.MessageModel;
import com.second.hand.trading.server.model.UserModel;
import com.second.hand.trading.server.service.MessageService;
import com.second.hand.trading.server.vo.PageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageDao messageDao;

    @Resource
    private UserDao userDao;

    @Resource
    private IdleItemDao idleItemDao;

    /**
     * 增加一条留言
     * @param messageModel
     * @return
     */
    public boolean addMessage(MessageModel messageModel){
        return messageDao.insert(messageModel)==1;
    }

    /**
     * 删除一条留言，未做用户身份验证
     * @param id
     * @return
     */
    public boolean deleteMessage(Long id){
        return messageDao.deleteByPrimaryKey(id)==1;
    }

//    @Override
//    public boolean deleteMessage(Long id, Long userId) {
//        // 先获取留言信息，验证是否是当前用户发布的
//        MessageModel message = messageDao.selectByPrimaryKey(id);
//        if (message == null) {
//            return false;
//        }
//
//        // 验证留言是否属于当前用户
//        if (!message.getUserId().equals(userId)) {
//            return false; // 不是当前用户发布的留言，拒绝删除
//        }
//
//        // 执行删除操作
//        return messageDao.deleteByPrimaryKey(id) == 1;
//    }
    /**
     * 获取一条留言
     * @param id
     * @return
     */
    public MessageModel getMessage(Long id){
        return messageDao.selectByPrimaryKey(id);
    }

    /**
     * 获取一个用户收到的所有留言，未做分页查询
     * 同时查询出用户的信息和闲置的信息
     * userId建索引
     * @param userId
     * @return
     */
    public List<MessageModel> getAllMyMessage(Long userId){
        List<MessageModel> list=messageDao.getMyMessage(userId);
        if(list.size()>0){
            List<Long> idList=new ArrayList<>();
            for(MessageModel i:list){
                idList.add(i.getUserId());
            }
            List<UserModel> userList=userDao.findUserByList(idList);
            Map<Long,UserModel> map=new HashMap<>();
            for(UserModel user:userList){
                map.put(user.getId(),user);
            }
            for(MessageModel i:list){
                i.setFromU(map.get(i.getUserId()));
            }

            List<Long> idleIdList=new ArrayList<>();
            for(MessageModel i:list){
                idleIdList.add(i.getIdleId());
            }
            List<IdleItemModel> idleList=idleItemDao.findIdleByList(idleIdList);
            Map<Long,IdleItemModel> idleMap=new HashMap<>();
            for(IdleItemModel idle:idleList){
                idleMap.put(idle.getId(),idle);
            }
            for(MessageModel i:list){
                i.setIdle(idleMap.get(i.getIdleId()));
            }
        }
        return list;
    }

    /**
     * 查询一个闲置下的所有留言，未做分页
     * 同时查出发送者和接收者的信息
     * idleId建索引
     * @param idleId
     * @return
     */
    public List<MessageModel> getAllIdleMessage(Long idleId){
        List<MessageModel> list=messageDao.getIdleMessage(idleId);
        if(list.size()>0){
            List<Long> idList=new ArrayList<>();
            for(MessageModel i:list){
                idList.add(i.getUserId());
            }
            List<UserModel> userList=userDao.findUserByList(idList);
            Map<Long,UserModel> map=new HashMap<>();
            for(UserModel user:userList){
                map.put(user.getId(),user);
            }
            for(MessageModel i:list){
                i.setFromU(map.get(i.getUserId()));
            }
            Map<Long,MessageModel> mesMap=new HashMap<>();
            for(MessageModel i:list){
                mesMap.put(i.getId(),i);
            }
            for(MessageModel i:list){
                MessageModel toM=new MessageModel();
                UserModel toU=new UserModel();
                if(i.getToMessage()!=null){
                    toM.setContent(mesMap.get(i.getToMessage()).getContent());
                    toU.setNickname(map.get(i.getToUser()).getNickname());
                }
                i.setToM(toM);
                i.setToU(toU);
            }
        }
        return list;
    }


    //获取留言
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

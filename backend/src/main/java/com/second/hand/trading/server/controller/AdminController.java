package com.second.hand.trading.server.controller;

import com.second.hand.trading.server.enums.ErrorMsg;
import com.second.hand.trading.server.model.AdminModel;
import com.second.hand.trading.server.model.IdleItemModel;
import com.second.hand.trading.server.model.UserModel;
import com.second.hand.trading.server.service.*;
import com.second.hand.trading.server.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private IdleItemService idleItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private BannerService bannerService;

    // 在登录方法中添加角色信息到session
    @PostMapping("login")
    public ResultVo login(HttpSession session,
                          @RequestParam("accountNumber") @NotNull @NotEmpty String accountNumber,
                          @RequestParam("adminPassword") @NotNull @NotEmpty String adminPassword){
        AdminModel adminModel = adminService.login(accountNumber, adminPassword);
        if(null == adminModel){
            return ResultVo.fail(ErrorMsg.ACCOUNT_NOT_EXIT);
        }
        session.setAttribute("admin", adminModel);
        session.setAttribute("adminId", adminModel.getId());
        session.setAttribute("adminRole", adminModel.getRole()); // 保存角色信息
        return ResultVo.success(adminModel);
    }   

    // 登出
    @GetMapping("loginOut")
    public ResultVo loginOut( HttpSession session){
        session.removeAttribute("admin");
        return ResultVo.success();
    }
    // 获取管理员列表
    @GetMapping("list")
    public ResultVo getAdminList(HttpSession session,
                                 @RequestParam(value = "page",required = false) Integer page,
                                 @RequestParam(value = "nums",required = false) Integer nums){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }
        return ResultVo.success(adminService.getAdminList(p,n));
    }

    // 添加管理员
    @PostMapping("/admin/add")
    public ResultVo<Boolean> addAdmin(@RequestAttribute("adminRole") Byte adminRole,
                                      @RequestBody AdminModel adminModel) {
        // 验证是否为超级管理员
        if (adminRole != 1) {
            return ResultVo.<Boolean>fail(ErrorMsg.valueOf("只有超级管理员可以添加管理员"));
        }

        // 设置默认为普通管理员
        if (adminModel.getRole() == null) {
            adminModel.setRole((byte) 0);
        }

        boolean result = adminService.addAdmin(adminModel);
        return ResultVo.success(result);
    }


    // @DeleteMapping("/admin/delete/{id}")
    // 删除管理员
    @GetMapping("/admin/delete/{id}")
    public ResultVo<Boolean> deleteAdmin(@RequestAttribute("adminRole") Byte adminRole,
                                         @PathVariable Long id,
                                         @RequestAttribute("adminId") Long adminId) {
        // 验证是否为超级管理员
        if (adminRole != 1) {
            return ResultVo.<Boolean>fail(ErrorMsg.valueOf("只有超级管理员可以删除管理员"));
        }

        // 防止超级管理员被删除
        if (id == 1) {
            return ResultVo.<Boolean>fail(ErrorMsg.valueOf("不能删除超级管理员"));
        }

        // 防止自己删除自己
        if (adminId.equals(id)) {
            return ResultVo.<Boolean>fail(ErrorMsg.valueOf("不能删除自己"));
        }

        boolean result = adminService.deleteAdmin(id);
        return ResultVo.success(result);
    }


    // 获取闲置物品列表
    @GetMapping("idleList")
    public ResultVo idleList(HttpSession session,
                             @RequestParam("status") @NotNull @NotEmpty Integer status,
                             @RequestParam(value = "page",required = false) Integer page,
                             @RequestParam(value = "nums",required = false) Integer nums){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }
        return ResultVo.success(idleItemService.adminGetIdleList(status,p,n));
    }

    @GetMapping("updateIdleStatus")
    public ResultVo updateIdleStatus(HttpSession session,
                                     @RequestParam("id") @NotNull @NotEmpty Long id,
                                     @RequestParam("status") @NotNull @NotEmpty Integer status
                                     ){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        IdleItemModel idleItemModel=new IdleItemModel();
        idleItemModel.setId(id);
        idleItemModel.setIdleStatus(status.byteValue());
        if(idleItemService.updateIdleItem(idleItemModel)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @GetMapping("orderList")
    public ResultVo orderList(HttpSession session,
                              @RequestParam(value = "page",required = false) Integer page,
                              @RequestParam(value = "nums",required = false) Integer nums){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }
        return ResultVo.success(orderService.getAllOrder(p,n));
    }

    @GetMapping("deleteOrder")
    public ResultVo deleteOrder(HttpSession session,
                              @RequestParam("id") @NotNull @NotEmpty Long id){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        if(orderService.deleteOrder(id)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @GetMapping("userList")
    public ResultVo userList(HttpSession session,
                             @RequestParam(value = "page",required = false) Integer page,
                             @RequestParam(value = "nums",required = false) Integer nums,
                             @RequestParam("status") @NotNull @NotEmpty Integer status){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }
        return ResultVo.success(userService.getUserByStatus(status,p,n));
    }

    @GetMapping("updateUserStatus")
    public ResultVo updateUserStatus(HttpSession session,
                                     @RequestParam("id") @NotNull @NotEmpty Long id,
                                     @RequestParam("status") @NotNull @NotEmpty Integer status){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        UserModel userModel=new UserModel();
        userModel.setId(id);
        userModel.setUserStatus(status.byteValue());
        if(userService.updateUserInfo(userModel))
            return ResultVo.success();
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }


    // 按订单闲置物品名称查询
    @GetMapping("queryIdle")
    public ResultVo queryIdle(@RequestParam(value = "findValue",required = false) String findValue,
                              @RequestParam(value = "page",required = false) Integer page,
                              @RequestParam(value = "nums",required = false) Integer nums,
                              @RequestParam("status") @NotNull @NotEmpty Integer status){
            if(null==findValue){
                findValue="";
            }
            int p=1;
            int n=8;
            if(null!=page){
                p=page>0?page:1;
            }
            if(null!=nums){
                n=nums>0?nums:8;
            }

        System.out.println(findValue + " " + page + " " + nums + " " + status);

            if(status == 1)
                return ResultVo.success(idleItemService.findIdleItem(findValue,p,n));
            return ResultVo.success(idleItemService.findIdleItem1(findValue,status,p,n));

    }

    // 按订单号查询订单
    @GetMapping("queryOrder")
    public ResultVo queryOrder(HttpSession session,
                              @RequestParam(value = "page",required = false) Integer page,
                              @RequestParam(value = "nums",required = false) Integer nums,
                              @RequestParam(value = "searchValue",required = false) String searchValue){

        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }

        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }

//        System.out.println("---------------------" + searchValue + "--------------");
        if(null == searchValue || "".equals(searchValue))
            return ResultVo.success(orderService.getAllOrder(p,n));
        return ResultVo.success(orderService.findOrderByNumber(searchValue, p, n));
    }




    // 根据用户账号来查找信息
    @GetMapping("queryUser")
    public ResultVo queryUser(HttpSession session,
                              @RequestParam(value = "searchValue",required = false) String searchValue,
                              @RequestParam(value = "mode",required = false) Integer mode,
                              @RequestParam(value = "page",required = false) Integer page,
                              @RequestParam(value = "nums",required = false) Integer nums){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }
//        return ResultVo.success(userService.getUserByStatus(0,p,n));

        if(mode == 1){
            if(null == searchValue || "".equals(searchValue)){
                return ResultVo.success(userService.getUserByStatus(0,p,n));
            }else{
                return ResultVo.success(userService.getUserByNumber(searchValue,mode));
            }
        }else if(mode == 2){
            if(null == searchValue || "".equals(searchValue)){
                return ResultVo.success(userService.getUserByStatus(1,p,n));
            }else{
                return ResultVo.success(userService.getUserByNumber(searchValue,mode));
            }
        }else
            return ResultVo.success(adminService.getAdminList(p,n));

    }

    // AdminController.java
    @GetMapping("messageList")
    public ResultVo messageList(HttpSession session,
                                @RequestParam(value = "page",required = false) Integer page,
                                @RequestParam(value = "nums",required = false) Integer nums){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        int p=1;
        int n=8;
        if(null!=page){
            p=page>0?page:1;
        }
        if(null!=nums){
            n=nums>0?nums:8;
        }
        return ResultVo.success(messageService.getAllMessage(p, n));
    }

    @GetMapping("deleteMessage")
    public ResultVo deleteMessage(HttpSession session,
                                  @RequestParam("id") @NotNull @NotEmpty Long id){
        if(session.getAttribute("admin")==null){
            return ResultVo.fail(ErrorMsg.COOKIE_ERROR);
        }
        if(messageService.deleteMessage(id)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
}

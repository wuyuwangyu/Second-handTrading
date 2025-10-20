<template>
    <div>
        <app-head></app-head>
        <app-body>
            <div class="message-container">
                <div class="message-container-title">我的消息</div>
                <div v-for="(mes,index) in meslist" class="message-container-list" @click="toDetails(mes.idle.id)">
                    <div class="message-container-list-left">
                        <el-image
                                style="width: 55px; height: 55px;border-radius: 5px;"
                                :src="mes.fromU.avatar"
                                fit="cover"></el-image>
                        <div class="message-container-list-text">
                            <div class="message-nickname">{{mes.fromU.nickname}}</div>
                            <div class="message-content">{{mes.content}}</div>
                            <div class="message-time">{{mes.createTime}}</div>
                        </div>
                    </div>
                    <div class="message-container-list-right">
                        <!-- 添加删除按钮，只有消息发布者才能看到 -->
                        <!-- <el-button
                            v-if="mes.userId === currentUserID"
                            size="mini"
                            type="danger"
                            icon="el-icon-delete"
                            @click.stop="handleDeleteMessage(index, mes.id)"
                            circle
                            title="删除留言">
                        </el-button> -->
                        <el-image
                                v-if="mes.idle.imgUrl"
                                style="width:130px; height: 90px;"
                                :src="mes.idle.imgUrl"
                                fit="contain"></el-image>
                    </div>
                </div>
                 <!-- 无消息提示 -->
                <div v-if="meslist.length === 0" class="no-messages">
                <i class="el-icon-message"></i>
                    <p>暂无消息</p>
                </div>
            </div>
        </app-body>
        <app-foot></app-foot>
    </div>
</template>

<script>
    import AppHead from '../common/AppHeader.vue';
    import AppBody from '../common/AppPageBody.vue'
    import AppFoot from '../common/AppFoot.vue'

    // 原版的
    export default {
        name: "message",
        components: {
            AppHead,
            AppBody,
            AppFoot
        },
        data(){
            return{
                meslist:[]
            };
        },
        created(){
            this.$api.getAllMyMessage().then(res=>{
                console.log(res);
                if(res.status_code===1){
                    for(let i=0;i<res.data.length;i++){
                        let imgList=JSON.parse(res.data[i].idle.pictureList);
                        res.data[i].idle.imgUrl=imgList?imgList[0]:'';
                        let contentList=res.data[i].content.split('<br>');
                        let contenHtml=contentList[0];
                        for(let i=1;i<contentList.length;i++){
                            contenHtml+='  '+contentList[i];
                        }
                        res.data[i].content=contenHtml;
                    }
                    this.meslist=res.data;
                }
            })
        },
        methods:{
            toDetails(id){
                this.$router.push({path: '/details',query:{id:id}});
            }
        }
    }

//   export default {
//     name: "message",
//     components: {
//         AppHead,
//         AppBody,
//         AppFoot
//     },
//     data() {
//         return {
//             meslist: [],
//             currentUserID: null // 当前用户ID
//         }
//     },
//     mounted() {
//         this.getCurrentUser();
//         this.getMessageList();
//     },
//     methods: {
//         // 获取当前用户信息
//         getCurrentUser() {
//             this.currentUserID = this.$globalData.userInfo.id;
//         },
        
//         // 获取消息列表
//         getMessageList() {
//             this.$api.getAllMyMessage().then(res => {
//                 if (res.status_code === 1) {
//                     this.meslist = res.data;
//                 } else {
//                     this.$message.error('获取消息失败：' + res.msg);
//                 }
//             }).catch(() => {
//                 this.$message.error('获取消息失败，请稍后重试！');
//             });
//         },
        
//         // 跳转到详情页
//         toDetails(idleId) {
//             if (idleId) {
//                 this.$router.push({ path: '/details', query: { id: idleId } });
//             }
//         },
        
//         // 处理删除消息
//         handleDeleteMessage(index, message) {
//             // 再次验证用户身份
//             if (message.userId !== this.currentUserID) {
//                 this.$message.error('您只能删除自己发布的留言！');
//                 return;
//             }
            
//             this.$confirm('此操作将永久删除该留言, 是否继续?', '提示', {
//                 confirmButtonText: '确定',
//                 cancelButtonText: '取消',
//                 type: 'warning'
//             }).then(() => {
//                 // 调用删除留言API
//                 this.$api.deleteMessage({ id: message.id }).then(res => {
//                     if (res.status_code === 1) {
//                         this.$message({
//                             message: '删除成功！',
//                             type: 'success'
//                         });
//                         // 从列表中移除已删除的消息
//                         this.meslist.splice(index, 1);
//                     } else {
//                         this.$message.error('删除失败：' + res.msg);
//                     }
//                 }).catch(() => {
//                     this.$message.error('删除失败，请稍后重试！');
//                 });
//             }).catch(() => {
//                 this.$message({
//                     type: 'info',
//                     message: '已取消删除'
//                 });
//             });
//         }
//     }
// }
</script>

<style scoped>.message-container {
  min-height: calc(100vh - 200px);
  padding: 20px;
}

.message-container-title {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #ebeef5;
}

.message-container-list {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  margin-bottom: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: all 0.3s;
}

.message-container-list:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 20px 0 rgba(0, 0, 0, 0.15);
}

.message-container-list-left {
  display: flex;
  flex: 1;
}

.message-container-list-text {
  margin-left: 15px;
  flex: 1;
}

.message-nickname {
  font-weight: 600;
  font-size: 16px;
  color: #303133;
  margin-bottom: 8px;
}

.message-content {
  font-size: 14px;
  color: #606266;
  margin-bottom: 10px;
  line-height: 1.5;
  word-break: break-word;
}

.message-time {
  font-size: 12px;
  color: #909399;
}

.message-container-list-right {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  justify-content: space-between;
  margin-left: 15px;
}

.no-messages {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 300px;
  color: #909399;
}

.no-messages i {
  font-size: 48px;
  margin-bottom: 15px;
}
</style>

<!-- <style scoped>
    .message-container{
        min-height: 85vh;
        padding: 0 20px;
    }
    .message-container-title{
        font-size: 16px;
        padding: 20px 0;
        font-weight: 600;
    }
    .message-container-list{
        cursor:pointer;
        height: 110px;
        border-top: 1px solid #eeeeee;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .message-container-list-left{
        width: 800px;
        display: flex;
    }
    .message-container-list-right{
        width: 130px;
    }
    .message-container-list-text{
        margin-left: 10px;
    }
    .message-nickname{
        font-weight: 600;
        font-size: 18px;
        padding-bottom: 5px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
    .message-content{
        font-size: 16px;
        padding-bottom: 15px;
        color: #555555;
        width: 710px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
    .message-time{
        font-size: 13px;
        color: #555555;
    }
</style> -->
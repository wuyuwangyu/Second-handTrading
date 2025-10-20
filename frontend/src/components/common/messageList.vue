<!-- src/components/common/messageList.vue -->
<template>
    <div class="main-border">
        <div class="page-header">
            <h2 class="page-title">留言管理</h2>
            <div class="search-container">
                <el-input 
                    placeholder="搜索留言内容..." 
                    v-model="searchValue" 
                    @keyup.enter.native="searchMessage"
                    prefix-icon="el-icon-search"
                    clearable
                    class="search-input">
                    <el-button slot="append" icon="el-icon-search" @click="searchMessage">搜索</el-button>
                </el-input>
            </div>
        </div>

        <!-- 留言表格 -->
        <div class="table-container">
            <el-table
                :data="messageData"
                stripe
                style="width: 100%"
                :header-cell-style="{ background: '#f5f7fa', color: '#303133', fontWeight: 'bold' }"
                border
                v-loading="tableLoading">
                <el-table-column prop="fromU.nickname" label="留言用户" min-width="100"></el-table-column>
                <el-table-column prop="idle.idleName" label="闲置物品" min-width="120" show-overflow-tooltip></el-table-column>
                <el-table-column prop="content" label="留言内容" min-width="150" show-overflow-tooltip></el-table-column>
                <el-table-column prop="createTime" label="留言时间" width="180"></el-table-column>
                <el-table-column label="操作" width="120" align="center">
                    <template slot-scope="scope">
                        <el-button
                            size="mini"
                            type="danger"
                            icon="el-icon-delete"
                            @click="confirmDeleteMessage(scope.$index, scope.row)"
                            round>
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 分页 -->
        <div class="pagination-container">
            <el-pagination
                @current-change="handleCurrentChange"
                :current-page.sync="nowPage"
                :page-size="8"
                background
                layout="total, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
export default {
    name: "messageList",
    data() {
        return {
            messageData: [],
            nowPage: 1,
            total: 0,
            searchValue: '',
            tableLoading: false
        }
    },
    mounted() {
        this.getMessageData();
    },
    methods: {
        getMessageData() {
            this.tableLoading = true;
            this.$api.getAdminMessageList({
                page: this.nowPage,
                nums: 8
            }).then(res => {
                this.tableLoading = false;
                if(res.status_code == 1) {
                    this.messageData = res.data.list;
                    this.total = res.data.count;
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                this.tableLoading = false;
                console.log(e)
            })
        },
        handleCurrentChange(val) {
            this.nowPage = val;
            this.getMessageData();
        },
        confirmDeleteMessage(index, row) {
            this.$confirm('此操作将永久删除该留言, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.deleteMessage(index, row);
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });          
            });
        },
        deleteMessage(i, row) {
            this.tableLoading = true;
            this.$api.deleteAdminMessage({
                id: row.id
            }).then(res => {
                this.tableLoading = false;
                if (res.status_code == 1) {
                    this.$message.success('留言已成功删除');
                    this.getMessageData();
                } else {
                    this.$message.error(res.msg)
                }
            }).catch(e => {
                this.tableLoading = false;
                console.log(e)
            })
        },
        searchMessage() {
            // 搜索功能可以后续实现
            this.$message.info('搜索功能待实现');
        }
    }
}
</script>

<style scoped>
.main-border {
    padding: 20px;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    margin: 20px;
}

.page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    flex-wrap: wrap;
}

.page-title {
    margin: 0;
    color: #303133;
    font-size: 24px;
    font-weight: 600;
}

.search-container {
    flex-shrink: 0;
    width: 300px;
}

.table-container {
    min-height: 442px;
    margin-bottom: 20px;
}

.pagination-container {
    display: flex;
    justify-content: center;
}

@media (max-width: 768px) {
    .page-header {
        flex-direction: column;
        align-items: stretch;
    }
    
    .search-container {
        width: 100%;
        margin-top: 15px;
    }
}
</style>

<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>
<style>
    html, body, #app{
        overflow: visible;
        background-color: #f6f6f6;
    }

    /* 修改2003 */
    /* 全局字体和颜色 */
    body, html {
        font-family: Arial, sans-serif;
        background-color: #f5f7fa;
        color: #303133;
    }

    /* 按钮样式 */
    .el-button {
        border-radius: 5px;
        font-size: 14px;
        padding: 8px 15px;
    }

    .el-button--primary {
        background-color: #409EFF;
        border-color: #409EFF;
    }

    .el-button--primary:hover {
        background-color: #3A5FD9;
        border-color: #3A5FD9;
    }

    .el-button--danger {
        background-color: #ff6b6b;
        border-color: #ff6b6b;
    }

    .el-button--danger:hover {
        background-color: #ff8c8c;
        border-color: #ff8c8c;
    }

    .el-button--success {
        background-color: #67c23a;
        border-color: #67c23a;
    }

    .el-button--success:hover {
        background-color: #85ce61;
        border-color: #85ce61;
    }

    /* 输入框样式 */
    .el-input__inner {
        border-radius: 5px;
        font-size: 14px;
        padding: 8px 10px;
    }

    /* 表格样式 */
    .el-table {
        border-radius: 8px;
        overflow: hidden;
        border: 1px solid #ebeef5;
    }

    /* 分页组件样式 */
    .pagination-container .el-pagination {
        display: flex;
        align-items: center;
        gap: 8px;
    }

    .pagination-container .el-pagination .el-pager li {
        border-radius: 6px;
        margin: 0 2px;
        min-width: 32px;
        height: 32px;
        line-height: 32px;
    }

    .pagination-container .el-pagination .el-pager li.active {
        background-color: #409EFF;
        color: white;
    }

    .pagination-container .el-pagination button {
        border-radius: 6px;
        padding: 0 12px;
        height: 32px;
        min-width: 32px;
    }

    /* 加载状态样式 */
    .el-loading-mask {
        background-color: rgba(255, 255, 255, 0.8);
    }
</style>
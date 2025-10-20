<template>
    <div class="footer">
        <div class="footer-content">
            <div class="footer-logo">
                <router-link to="/">                
                    <i class="el-icon-s-shop"></i>
                    <span>校园跳蚤市场</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "app-foot"
    }
</script>

<!-- <style scoped>
    .footer {
        background-color: #f5f7fa;
        color: #606266;
        padding: 30px 0 20px;
        width: 100%;
        border-top: 1px solid #e6e6e6;
    }

    .footer-content {
        max-width: 1200px;
        margin: 0 auto;
        padding: 0 20px;
        text-align: center;
    }

    .footer-logo {
        display: flex;
        align-items: center;
        justify-content: center;
        color: #66b1ff;
        font-size: 20px;
        font-weight: 600;
        margin-bottom: 20px;
    }

    .footer-logo i {
        font-size: 24px;
        margin-right: 10px;
    }

    .footer-divider {
        height: 1px;
        background-color: #e6e6e6;
        margin-bottom: 20px;
    }

    .footer-bottom {
        text-align: center;
    }

    .footer-bottom p {
        margin: 5px 0;
        font-size: 14px;
        color: #909399;
    }

    /* 响应式调整 */
    @media (max-width: 768px) {
        .footer {
            padding: 25px 0 15px;
        }
        
        .footer-logo {
            font-size: 18px;
        }
        
        .footer-logo i {
            font-size: 22px;
        }
        
        .footer-bottom p {
            font-size: 13px;
        }
    }
</style> -->


<style scoped>
.footer {
    background-color: #ffffff;
    color: #606266;
    padding: 40px 0;
    width: 100%;
    border-top: 1px solid #ebeef5;
    box-shadow: 0 -2px 12px 0 rgba(0, 0, 0, 0.1);
    margin-top: 20px;
}

.footer-content {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
}

.footer-main {
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
}

.footer-logo {
    display: flex;
    align-items: center;
    color: #409EFF;
    font-size: 18px;
    font-weight: 600;
    justify-content: center;
}

.footer-logo a {
    color: #66b1ff;
    font-size: 20px;
    font-weight: 600;
    text-decoration: none;
    display: flex;
    align-items: center;
    transition: all 0.3s ease;
}

.footer-logo a:hover {
    color:#13227a;
    transform: scale(1.25);
}

.footer-logo i {
    font-size: 22px;
    margin-right: 8px;
    padding: 3.5px 0 0 0;
}

.footer-info {
    text-align: right;
}

.footer-info p {
    margin: 5px 0;
    font-size: 13px;
    color: #909399;
}

/* 响应式调整 */
@media (max-width: 768px) {
    .footer-main {
        flex-direction: column;
        text-align: center;
    }
    
    .footer-logo {
        justify-content: center;
        margin-bottom: 15px;
    }
    
    .footer-info {
        text-align: center;
    }
    
    .footer-info p {
        font-size: 12px;
    }
}
</style>
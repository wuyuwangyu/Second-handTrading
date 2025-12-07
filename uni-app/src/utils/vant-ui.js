import Vue from 'vue'
// 插件安装初始化
import {
  Tabbar, TabbarItem,
  NavBar, Dialog, Loading, Tab,
  Tabs, Swipe, SwipeItem, Icon, Badge,
  Tag, GoodsAction, GoodsActionIcon, GoodsActionButton,
  CouponCell, CouponList, Popup, TreeSelect, PullRefresh,
  Checkbox, CheckboxGroup, Sku, Stepper
} from 'vant'

Vue.use(Icon)
Vue.use(Loading)
Vue.use(Tabbar)
Vue.use(TabbarItem)
Vue.use(NavBar)
Vue.use(Dialog)
Vue.use(Loading)
Vue.use(Tab)
Vue.use(Tabs)
Vue.use(Swipe)
Vue.use(SwipeItem)
Vue.use(Badge)
Vue.use(Tag)

Vue.use(GoodsAction)
Vue.use(GoodsActionButton)
Vue.use(GoodsActionIcon)

Vue.use(CouponCell)
Vue.use(CouponList)
Vue.use(Popup)

Vue.use(TreeSelect)

Vue.use(PullRefresh)

Vue.use(Checkbox)
Vue.use(CheckboxGroup)

Vue.use(Sku)

Vue.use(Stepper)

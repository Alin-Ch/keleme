import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import UsersView from '../views/UsersView.vue'
import AboutView from '../views/AboutView.vue'
import LoginView from '../views/LoginView.vue'
import LayoutView from '../views/LayoutView.vue'
import RegisterView from '../views/RegisterView.vue'
import GoodView from '../views/GoodView.vue'
import TypeView from '../views/TypeView.vue'
import StationView from '../views/StationView.vue'
import EmployView from '@/views/EmployView'
import TicketView from '../views/TicketView.vue'
import AddressView from '@/views/AddressView'
import OrderView from '../views/OrderView.vue'
import LogView from '../views/LogView.vue'
import HelpView from '@/views/HelpView' 


Vue.use(VueRouter)

const routes = [
  //登录
  {
    path: '/login',
    name: 'Login',
    component: LoginView
  },
  //注册
  {
    path: '/register',
    name: 'Register',
    component: RegisterView
  },
  {
    path: '/',
    name: 'Layout',
    component: LayoutView,
    children: [
      // 首页
      {
        path: '',
        name: 'home',
        component: HomeView
      },
      //用户信息
      {
        path: 'users',
        name: 'users',
        component: UsersView
      },
      //关于我们
      {
        path: 'about',
        name: 'about',
        component: AboutView
      },
      //商品管理
      {
        path: 'good',
        name: 'good',
        component: GoodView
      },
      //收货地址管理
      {
        path: 'address',
        name: 'address',
        component: AddressView
      },
      //分类管理
      {
        path: 'type',
        name: 'type',
        component: TypeView
      },
      //水站管理
      {
        path: 'station',
        name: 'station',
        component: StationView
      },{ //水站人员管理
        path: 'employee',
        name: 'employee',
        component: EmployView
      },
      //水票管理
      {
        path: 'ticket',
        name: 'ticket',
        component: TicketView
      },
      //订单管理
      {
        path: 'order',
        name: 'order',
        component: OrderView
      },
      {
        path: 'log',
        name: 'log',
        component: LogView
      },{
        path: 'help',
        name: 'help',
        component: HelpView
      },
    ]
  },
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
//路由守卫
router.beforeEach((to,from,next) =>{
  if(to.path === '/login' || to.path === '/register'){
    next();
  }
  const user = localStorage.getItem('user');
  if(!user && to.path !== '/login' && to.path !== '/register'){
    return next('/login');
  }
  next();
});

export default router

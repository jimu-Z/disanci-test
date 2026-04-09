import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'
import r from 'highlight.js/lib/languages/r'

/**
 * Note: 路由配置项
 *
 * hidden: true                     // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true                 // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                  // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                  // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                  // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect             // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'               // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * query: '{"id": 1, "name": "ry"}' // 访问路由的默认传递参数
 * roles: ['admin', 'common']       // 访问路由的角色权限
 * permissions: ['a:a:a', 'b:b:b']  // 访问路由的菜单权限
 * meta : {
    noCache: true                   // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
    title: 'title'                  // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'                // 设置该路由的图标，对应路径src/assets/icons/svg
    breadcrumb: false               // 如果设置为false，则不会在breadcrumb面包屑中显示
    activeMenu: '/system/user'      // 当路由设置了该属性，则会高亮相对应的侧边栏。
  }
 */

// 公共路由
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login'),
    hidden: true
  },
  {
    path: '/register',
    component: () => import('@/views/register'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error/401'),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: 'index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/index'),
        name: 'Index',
        meta: { title: '首页', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: () => import('@/views/system/user/profile/index'),
        name: 'Profile',
        meta: { title: '个人中心', icon: 'user' }
      }
    ]
  },
  //个人中心
  {
      path: '/system/alumni',
      name: 'Alumni',
      component: Layout,
      redirect: '/system/alumni/index',
      hidden: true,
      children: [
        {
          path: 'index',
          component: () => import('@/views/system/alumni/mydesktop'),
          name: 'mydesktop',
          meta: { title: '我的桌面', icon: 'user' }
        }
      ]
  },
  // 论坛模块主路由（嵌套路由，统一管理论坛所有页面）
    {
      path: '/system/forum',
      name: 'Forum',
      redirect: '/system/forum/index', // 重定向到论坛首页
      meta: { title: '论坛管理', icon: 'el-icon-chat-dot-round' }, // 侧边栏/面包屑配置
      component: Layout,
        hidden: true,
      children: [
        // 1. 论坛首页（核心：对应生成的ForumIndex.vue）
        {
          path: 'index',
          name: 'ForumIndex',
          component: () => import('@/views/system/forum_post/index.vue'), // 论坛首页组件路径
          meta: { title: '论坛首页', icon: 'el-icon-home', noCache: true }
        },
        // 2. 帖子发布页（需登录权限）
        {
          path: 'post/add',
          name: 'ForumPostAdd',
          component: () => import('@/views/system/forum_post/add'), // 帖子发布组件路径
          meta: { title: '发布新帖', icon: 'el-icon-plus', requireAuth: true }, // requireAuth：标记需登录
          hidden: false // false：显示在侧边栏；true：隐藏（仅通过按钮跳转）
        },
        // 3. 帖子详情页（动态路由，传帖子ID）
        {
          path: 'post/detail/:id', // :id为动态参数，接收帖子ID
          name: 'ForumPostDetail',
          component: () => import('@/views/system/forum_post/postDetail'), // 帖子详情组件路径
          meta: { title: '帖子详情', requireAuth: false }, // 无需登录即可查看
          hidden: true // 隐藏在侧边栏，仅通过首页帖子列表跳转
        },
        // 4. 帖子编辑页（可选，需登录+作者权限）
        {
          path: 'post/edit/:id',
          name: 'ForumPostEdit',
          component: () => import('@/views/system/forum_post/postEdit'),
          meta: { title: '编辑帖子', requireAuth: true },
          hidden: true
        }
      ]
    },
  //题库模块路由
  {
    path: '/system/question',
    name: 'Question',
    redirect: '/system/question/index',
    meta: { title: '题库管理', icon: 'el-icon-s-promotion' },
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'index',
        name: 'QuestionIndex',
        component: () => import('@/views/system/question_bank/index'),
        meta: { title: '题目管理', requireAuth: true },
        hidden: true
      },
      {
        path: 'detail/:id',
        name: 'QuestionDetail',
        component: () => import('@/views/system/question_bank/questionDetail'),
        meta: { title: '题目详情', requireAuth: true },
        hidden: true
      }
    ]
  },
  //个人简历模块路由
  {
    path: '/system/resume',
    name: 'Resume',
    redirect: '/system/resume/index',
    meta: { title: '个人简历管理', icon: 'el-icon-s-promotion' },
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'index',
        name: 'ResumeIndex',
        component: () => import('@/views/system/student_resume/index'),
        meta: { title: '个人简历', requireAuth: true },
        hidden: true
      },
      {
        path: 'detail/:id',
        name: 'resumeDetail',
        component: () => import('@/views/system/student_resume/resumeDetail'),
        meta: { title: '简历详情', requireAuth: true },
        hidden: true
      }        ,

    ]
  },
  //获奖
  {
    path: '/system/honor',
    name: 'Honor',
    redirect: '/system/alumni_honor/index',
    meta: { title: '个人获奖管理', icon: 'el-icon-s-promotion' },
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'index',
        name: 'honorIndex',
        component: () => import('@/views/system/alumni_honor/index'),
        meta: { title: '获奖列表', requireAuth: true },
        hidden: true
      },
      {
        path: 'detail/:id',
        name: 'honorDetail',
        component: () => import('@/views/system/alumni_honor/honorDetail'),
        meta: { title: '获奖详情', requireAuth: true },
        hidden: true
      }        ,

    ]
  },
  //新闻
  {
    path: '/system/notice',
    name: 'news',
    redirect: '/system/notice/index',
    meta: { title: '新闻', icon: 'el-icon-s-promotion' },
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'index',
        name: 'newsIndex',
        component: () => import('@/views/system/notice/index'),
        meta: { title: '新闻列表', requireAuth: true },
        hidden: true
      },
      {
        path: 'detail/:id',
        name: 'noticeDetail',
        component: () => import('@/views/system/notice/noticeDetail'),
        meta: { title: '详情', requireAuth: true },
        hidden: true
      }        ,

    ]
  },
  //报名申请
  {
    path: '/system/apply',
    name: 'apply',
    redirect: '/system/apply/index',
    meta: { title: '报名申请', icon: 'el-icon-s-promotion' },
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'index',
        name: 'applyIndex',
        component: () => import('@/views/system/student_apply/index'),
        meta: { title: '报名申请', requireAuth: true },
        hidden: true
      },
      ]
     },
  //录取信息
  {
    path: '/system/admission',
    name: 'admission',
    redirect: '/system/admission/index',
    meta: { title: '录取信息', icon: 'el-icon-s-promotion' },
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'index',
        name: 'admissionIndex',
        component: () => import('@/views/system/student_admission/index'),
        meta: { title: '录取信息', requireAuth: true },
        hidden: true
      },

    ]
  },
]

// 动态路由，基于用户权限动态去加载
export const dynamicRoutes = [
  {
    path: '/system/user-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:user:edit'],
    children: [
      {
        path: 'role/:userId(\\d+)',
        component: () => import('@/views/system/user/authRole'),
        name: 'AuthRole',
        meta: { title: '分配角色', activeMenu: '/system/user' }
      }
    ]
  },
  {
    path: '/system/role-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:role:edit'],
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/views/system/role/authUser'),
        name: 'AuthUser',
        meta: { title: '分配用户', activeMenu: '/system/role' }
      }
    ]
  },
  {
    path: '/system/dict-data',
    component: Layout,
    hidden: true,
    permissions: ['system:dict:list'],
    children: [
      {
        path: 'index/:dictId(\\d+)',
        component: () => import('@/views/system/dict/data'),
        name: 'Data',
        meta: { title: '字典数据', activeMenu: '/system/dict' }
      }
    ]
  },
  {
    path: '/monitor/job-log',
    component: Layout,
    hidden: true,
    permissions: ['monitor:job:list'],
    children: [
      {
        path: 'index/:jobId(\\d+)',
        component: () => import('@/views/monitor/job/log'),
        name: 'JobLog',
        meta: { title: '调度日志', activeMenu: '/monitor/job' }
      }
    ]
  },
  {
    path: '/tool/gen-edit',
    component: Layout,
    hidden: true,
    permissions: ['tool:gen:edit'],
    children: [
      {
        path: 'index/:tableId(\\d+)',
        component: () => import('@/views/tool/gen/editTable'),
        name: 'GenEdit',
        meta: { title: '修改生成配置', activeMenu: '/tool/gen' }
      }
    ]
  },

]

// 防止连续点击多次路由报错
let routerPush = Router.prototype.push
let routerReplace = Router.prototype.replace
// push
Router.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(err => err)
}
// replace
Router.prototype.replace = function push(location) {
  return routerReplace.call(this, location).catch(err => err)
}

export default new Router({
  mode: 'history', // 去掉url中的#
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

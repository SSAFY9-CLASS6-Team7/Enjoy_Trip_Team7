import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'main',
    component: () => import(/* webpackChunkName: "main" */ '../views/AppMain.vue'),
  },
  {
    path: '/board',
    name: 'board',
    component: () => import(/* webpackChunkName: "board" */ '../views/AppBoard.vue'),
    redirect: '/board/list',
    children: [
      {
        path: 'list',
        name: 'boardlist',
        component: () => import(/* webpackChunkName: "board" */ '@/components/board/BoardList.vue'),
      },
      {
        path: 'create',
        name: 'boardcreate',
        component: () =>
          import(/* webpackChunkName: "board" */ '../components/board/BoardCreate.vue'),
      },
      {
        path: 'update/:boardId',
        name: 'boardupdate',
        component: () =>
          import(/* webpackChunkName: "board" */ '../components/board/BoardUpdate.vue'),
      },
      {
        path: 'view/:boardId',
        name: 'boardview',
        component: () =>
          import(/* webpackChunkName: "board" */ '../components/board/BoardView.vue'),
      },
    ],
  },
  {
    path: '/history',
    name: 'history',
    component: () => import(/* webpackChunkName: "history" */ '../views/AppHistory.vue'),
    redirect: '/history/list',
    children: [
      {
        path: 'list',
        name: 'historylist',
        component: () =>
          import(/* webpackChunkName: "history" */ '@/components/history/HistoryList.vue'),
      },
      // {
      //   path: "create",
      //   name: "historycreate",
      //   component: () => import(/* webpackChunkName: "history" */ '@/components/history/HistoryCreate.vue'),
      // },
      {
        path: 'empty',
        name: 'historyempty',
        component: () =>
          import(/* webpackChunkName: "history" */ '@/components/history/HistoryEmpty.vue'),
      },
      // {
      //   path: 'image',
      //   name: 'historyimage',
      //   component: () =>
      //     import(/* webpackChunkName: "history" */ '@/components/history/HistoryImage.vue'),
      // },
      // {
      //   path: "update",
      //   name: "historyupdate",
      //   component: () => import(/* webpackChunkName: "history" */ '@/components/history/HistoryUpdate.vue'),
      // },
      // {
      //   path: "view/:historyId",
      //   name: "historyview",
      //   component: () => import(/* webpackChunkName: "history" */ '@/components/history/HistoryView.vue'),
      // },
    ],
  },
  {
    path: '/plan',
    name: 'plan',
    component: () => import(/* webpackChunkName: "plan" */ '../views/AppPlan.vue'),
    redirect: '/plan/list',
    children: [
      {
        path: 'create',
        name: 'plancreate',
        component: () => import(/* webpackChunkName: "plan" */ '@/components/plan/PlanCreate.vue'),
      },
      {
        path: 'empty',
        name: 'planempty',
        component: () => import(/* webpackChunkName: "plan" */ '@/components/plan/PlanEmpty.vue'),
      },
      {
        path: 'list',
        name: 'planlist',
        component: () => import(/* webpackChunkName: "plan" */ '@/components/plan/PlanList.vue'),
      },
      // {
      //   path: 'search',
      //   name: 'plansearch',
      //   component: () => import(/* webpackChunkName: "plan" */ '@/components/plan/PlanSearch.vue'),
      // },
      {
        path: 'update/:planId',
        name: 'planupdate',
        component: () => import(/* webpackChunkName: "plan" */ '@/components/plan/PlanUpdate.vue'),
      },
      {
        path: 'view/:planId',
        name: 'planview',
        component: () => import(/* webpackChunkName: "plan" */ '@/components/plan/PlanView.vue'),
      },
    ],
  },
  {
    path: '/user',
    name: 'user',
    component: () => import(/* webpackChunkName: "user" */ '../views/AppUser.vue'),
    children: [
      {
        path: 'login',
        name: 'userlogin',
        component: () => import(/* webpackChunkName: "user" */ '@/components/user/UserLogin.vue'),
      },
      {
        path: 'check',
        name: 'usercheck',
        component: () => import(/* webpackChunkName: "user" */ '@/components/user/UserCheck.vue'),
      },
      {
        path: 'modify',
        name: 'usermodify',
        component: () => import(/* webpackChunkName: "user" */ '@/components/user/UserModify.vue'),
      },
      {
        path: 'signup',
        name: 'usersignup',
        component: () => import(/* webpackChunkName: "user" */ '@/components/user/UserSignUp.vue'),
      },
    ],
  },
  {
    path: '/attraction',
    name: 'attraction',
    component: () => import(/* webpackChunkName: "attraction" */ '../views/AppAttraction.vue'),
    redirect: '/attraction/list',
    children: [
      {
        path: 'list',
        name: 'attractionlist',
        component: () =>
          import(/* webpackChunkName: "user" */ '@/components/attraction/AttractionList.vue'),
      },
      {
        path: 'view',
        name: 'attractionview',
        component: () =>
          import(/* webpackChunkName: "user" */ '@/components/attraction/AttractionView.vue'),
      },
    ],
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;

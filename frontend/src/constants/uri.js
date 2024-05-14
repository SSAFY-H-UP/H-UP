const API_URI = {
  BASE: '/api',
  // member
  SIGN_UP: '/member/signup',
  ID_CHECK: '/member/check',
  // auth
  LOGIN: '/auth/login',
  REFRESH: '/auth/refresh',
  // issue
  ISSUE: '/issue',
  UPDATE_ISSUE_STATUS: '/issue/status',
  UPDATE_ISSUE_DATE: '/issue/date',
  ISSUE_LIST: '/issue/list',

  MY_TEAM_LIST: '/team/me',
  MY_PROJECT_LIST: '/project/list/team',
  MY_ISSUE_LIST: '/issue/list',
  // todo
  TODO: '/todo',
  TODO_ASSIGNEE: '/todo/assignee',
  TODO_LIST: '/todo/list',

  TEAM: '/team',
  SHOW_TEAM: '/team/me',
  SHOW_TEAM_MEMBER: '/team/members',

  SHOW_TEAM_PROJECT: '/project/list/team',
  SHOW_PROJECT_MEMBER : '/project/members',
  INVITE_PROJECT_MEMBER : '/project/member',
  INVITE_TEAM_MEMBER : '/team/members',

  SHOW_MEMBER_ALL:'/member/all',
};

export default API_URI;

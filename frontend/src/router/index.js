import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '@/views/Home';
import Books from '@/views/Books';
import AddNewBook from '@/views/AddNewBook';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ '../views/About.vue'),
  },
  {
    path: '/books',
    name: 'Books',
    component: Books,
  },
  {
    path: '/newBook',
    name: 'NewBook',
    component: AddNewBook,
  },
];

const router = new VueRouter({
  mode: 'history', // uris without hashes #
  routes,
});

export default router;

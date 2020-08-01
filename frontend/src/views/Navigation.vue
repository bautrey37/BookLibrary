<template>
  <b-navbar>
    <template slot="brand"></template>
    <template slot="start">
      <b-navbar-item tag="router-link" to="/">Home</b-navbar-item>
      <b-navbar-item tag="router-link" to="/about">About</b-navbar-item>
      <b-navbar-item tag="router-link" to="/books">Books</b-navbar-item>
      <b-navbar-item tag="router-link" to="/newBook"
        >Add New Book</b-navbar-item
      >
    </template>

    <!--    <template slot="end">-->
    <!--      &lt;!&ndash;      <b-navbar-item tag="div">{{ this.username }}</b-navbar-item>&ndash;&gt;-->
    <!--      <b-navbar-item tag="router-link" to="/logout">Logout</b-navbar-item>-->
    <!--    </template>-->
  </b-navbar>
</template>

<script>
// import { mapGetters } from 'vuex';

export default {
  name: 'Navigation',
  computed: {
    // ...mapGetters(['username', 'isSite', 'isWorks']),
  },
};
</script>

<style></style>

<template>
  <div>
    <ul>
      <li v-for="person in persons" :key="person">{{ person }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'Person.vue',
  data() {
    return {
      persons: ['Brandon', 'Rain', 'Oleh'],
    };
  },
};
</script>

<style scoped></style>

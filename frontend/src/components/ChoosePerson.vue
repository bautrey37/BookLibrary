<template>
  <div>
    <b-field label="Book Owner">
      <b-dropdown hoverable aria-role="list">
        <button class="button is-primary" slot="trigger">
          <span>Choose Person</span>
        </button>
        <b-dropdown-item
          v-for="person in persons"
          :key="person"
          aria-role="listitem"
        >
          {{ person }}
        </b-dropdown-item>
      </b-dropdown>
    </b-field>
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
  methods: {
    retrievePeople() {},
  },
};
</script>

<style scoped></style>

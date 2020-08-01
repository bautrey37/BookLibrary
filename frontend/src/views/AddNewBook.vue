<template>
  <div class="wrapper">
    <form>
      <b-field name="Book Name">
        <b-input v-model="formData.bookInfo.bookName"></b-input>
      </b-field>
      <choose-person></choose-person>
    </form>
    <b-button @click.prevent="submit">Submit</b-button>
  </div>
</template>

<script>
import ChoosePerson from '@/components/ChoosePerson';
export default {
  name: 'AddNewBook.vue',
  data() {
    return {
      formData: {
        serialNumber: '',
        bookInfo: {
          bookName: '',
          author: '',
          publishDate: '',
        },
      },
    };
  },
  methods: {
    submit() {
      console.log(this.formData);
    },
  },
  components: {
    ChoosePerson,
  },
};
</script>

<style scoped></style>

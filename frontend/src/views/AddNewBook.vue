<template>
  <div class="wrapper">
    <form>
      <b-field label="Book Name">
        <b-input v-model="formData.bookInfo.bookName"></b-input>
      </b-field>
      <b-field label="Book Author">
        <b-input v-model="formData.bookInfo.author"></b-input>
      </b-field>
      <b-field label="Publish Date">
        <b-datepicker
          placeholder="Click to select..."
          v-model="formData.bookInfo.publishDate"
        ></b-datepicker>
      </b-field>
      <b-field label="Serial Number">
        <b-input v-model="formData.serialNumber"></b-input>
      </b-field>
      <choose-person></choose-person>
    </form>
    <b-button @click.prevent="submit">Submit</b-button>
  </div>
</template>

<script>
import ChoosePerson from '@/components/ChoosePerson';
// import moment from 'moment';
// moment().toDate()

export default {
  name: 'AddNewBook.vue',
  data() {
    return {
      formData: {
        serialNumber: '',
        bookInfo: {
          bookName: '',
          author: '',
          publishDate: null,
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

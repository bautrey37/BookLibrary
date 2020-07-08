<template>
  <div class="books">
    <b-table :data="books" :loading="loading">
      <template slot-scope="props">
        <b-table-column field="name" label="Book Name"
          >{{ props.row.bookName }}
        </b-table-column>
        <b-table-column field="author" label="Author"
          >{{ props.row.author }}
        </b-table-column>
        <b-table-column field="publishDate" label="Publish Date"
          >{{ props.row.publishDate }}
        </b-table-column>
        <b-table-column label="Borrow">
          <b-button @click="borrow(props.row._id)">Borrow</b-button>
        </b-table-column>
      </template>

      <template slot="empty">
        <section class="section">
          <div class="content has-text-grey has-text-centered">
            <p>
              <b-icon icon="emoticon-sad" size="is-large"></b-icon>
            </p>
            <p>No data found.</p>
          </div>
        </section>
      </template>
    </b-table>
  </div>
</template>

<script>
  import api from '../lib/api'

  export default {
  name: "Books",
  data: () => {
    return {
      books: [],
      // columns: [
      //   { field: "name", label: "Book Name" },
      //   { field: "author", label: "Author" }
      // ],
      loading: false,
    };
  },
  mounted: function () {
    this.loading = true;
    return api
      .bookList()
      .then((data) => {
        this.books = data;
        this.loading = false;
      })
      .catch(() => {
        console.log("error");
      });
  },
  methods: {
    borrow: function () {},
  },
};
</script>

<style scoped></style>

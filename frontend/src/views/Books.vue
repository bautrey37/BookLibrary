<template>
  <div class="wrapper">
    <b-table :data="books" :loading="loading">
      <template slot-scope="props">
        <b-table-column field="bookName" label="BookName"
        >{{ props.row.name }}
        </b-table-column>
        <b-table-column field="description" label="Description"
        >{{ props.row.description }}
        </b-table-column>
        <!--                <b-table-column field="price" label="Price">{{props.row.price}}</b-table-column>-->
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
  import api from "../lib/api";

  export default {
    name: "Books",
    data: () => {
      return {
        books: [],
        loading: false
      };
    },
    mounted: function () {
      return api
              .bookList()
              .then(data => {
                this.books = data;
                this.loading = true;
              })
              .catch(() => {
                console.log("error");
              });
    },
    methods: {
      borrow: function () {
      }
    }
  };
</script>

<style scoped></style>

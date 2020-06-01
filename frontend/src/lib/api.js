import axios from "./axios";

const bookList = () => axios.get("/book").then(response => response.data);

const addBook = () => axios.post("/book");

export default {
  bookList,
  addBook
};

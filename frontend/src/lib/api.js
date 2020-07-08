import axios from './axios'

const bookList = () =>
  axios
    .get("/book")
    .then((response) => response.data?._embedded?.bookEntryDTOList || []);

const addBook = () => axios.post("/book").then((response) => response.data);

export default {
  bookList,
  addBook,
};

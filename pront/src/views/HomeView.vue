<script setup lang="ts">
import axios from "axios";
import {defineProps, ref} from "vue";
import {useRouter} from "vue-router";

const posts = ref([]);
const router = useRouter();


axios.get("/api/posts?page=1&size=5").then((response) => {

  response.data.forEach((r: any) => {
    posts.value.push(r);
  });

});
const click = () => {
  console.log("c");
  console.log(current);
  console.log(pageCount);

}
const current = ref();
const pageCount = ref();
const handleSizeChange = (val: number) => {
  console.log(`${val} items per page`)
}
const handleCurrentChange = (val: number) => {
  posts.value = [];
  axios.get(`/api/posts?page= ${val}&size=5`).then((response) => {
    response.data.forEach((r: any) => {

      posts.value.push(r);
    });

  });

}
</script>

<template>


  <ul>
    <li v-for="post in posts" :key="post.id">
      <div class="title">
        <router-link :to="{ name: 'read', params: { postId: post.id } }">{{
            post.title
          }}</router-link>
      </div>

      <div class="content">
        {{ post.content }}
      </div>

      <div class="sub d-flex">
       <div class="category">{{post.category}}</div>
        <div class="regDate">{{post.dateTime}}</div>
      </div>
    </li>
  </ul>

  <div class="example-pagination-block">
    <div class="demonstration"></div>

    <el-pagination
        background layout=" prev, pager, next"
        :total="50"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />


  </div>




</template>

<style scoped lang="scss">
ul {
  list-style: none;
  padding: 0;
  li {
    margin-bottom: 2rem;
    .title {
      a {
        font-size: 1.3rem;
        color: #383838;
        text-decoration: none;
      }
      &:hover {
        text-decoration: underline;
      }
    }
    .content {
      font-size: 0.85rem;
      margin-top: 8px;
      line-height: 1.4;
      color: #707070;
    }
    &:last-child {
      margin-bottom: 0;
    }
    .sub {
      margin-top: 5px;
      font-size: 0.8rem;
      .regDate {
        margin-left: 5px;
        color: #707070;
      }
    }
  }
}
</style>
<script setup lang="ts">
import {defineProps, onMounted, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const props = defineProps({
  postId: {
    type: [Number, String],
    require: true,

  },

});

const post = ref({
  id:0,
  title: "",
  content: "",
  dateTime: "",

});
const comments = ref([]);



const router = useRouter();
const moveToEdit = () => {
  router.push({name: "edit",params: {
      postId: props.postId

    }})
}
onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then((response) => {
    console.log(response)
    post.value = response.data;
  });


});

axios.get(`/api/comment/${props.postId}`).then((response) => {
  console.log(response)
  response.data.forEach((r: any) => {
    comments.value.push(r);
  });


});

const  com_content = ref("");
const write = function () {

  axios.post(`/api/comment/${props.postId}`,{
    content : com_content.value,
  })
 // this.$router.go(); /// 새로고침 이거 맞나 ?
  router.go();
}



</script>


<template>
  <el-row>
    <el-col>
      <h2 class="title">{{ post.title }}</h2>

      <div class="sub d-flex">
        <div class="category">{{ post.category }}</div>
        <div class="regDate">{{ post.dateTime }}</div>
      </div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="content">{{ post.content }}</div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="d-flex justify-content-end">
        <el-button type="warning" @click="moveToEdit()">수정</el-button>
      </div>
    </el-col>
  </el-row>

  <ul>
    <li v-for="comment in comments" :key="post.id">
      <div class="comment_content">
        {{ comment.comment_content }}
      </div>
    </li>
  </ul>

  <div>
    <div class="mt-2">
      <el-input v-model="com_content" type="textarea" rows="1"/>
    </div>
    <div class="d-flex justify-content-end">
      <el-button type="primary" @click="write()">댓글완료</el-button>
    </div>
  </div>





</template>

<style scoped lang="scss">
.title {
  font-size: 1.6rem;
  font-weight: 600;
  color: #383838;
  margin: 0;
}
.sub {
  margin-top: 10px;
  font-size: 0.78rem;
  .regDate {
    margin-left: 10px;
    color: #6b6b6b;
  }
}
.content {
  font-size: 1rem;
  margin-top: 12px;
  color: #616161;
  white-space: break-spaces;
  line-height: 1.5;
}
</style>

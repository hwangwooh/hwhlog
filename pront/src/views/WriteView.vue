<script setup lang="ts">
import {ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const count = ref(0);
const  title = ref("");
const  content = ref("");
const  category = ref("");
const router = useRouter();

const write = function () {
axios.post("/api/posts",{
  title : title.value,
  content : content.value,
  category : radio.value.toString(),

}).then( () =>{
    router.replace({name: "home"})
    });

}

const radio = ref("")

const radio2 = ref("")
const radio3 = ref("")



</script>

<template>
  <div>
    <el-input v-model="title" placeholder="제목을 입력해주세요"/>

    <el-radio-group v-model="radio">
      <el-radio :label="1" >자유</el-radio>
      <el-radio :label="2" >개발</el-radio>
    </el-radio-group>
  </div>

  <div>
    <div class="mt-2">
      <el-input v-model="content" type="textarea" rows="20"/>
    </div>
    <div class="d-flex justify-content-end">
      <el-button type="primary" @click="write()">작성완료</el-button>
    </div>
  </div>

</template>

<style>

</style>
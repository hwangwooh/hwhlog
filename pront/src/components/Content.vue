<script setup lang="ts">

</script>


<template>
  <el-main class="content">
    <elrow>
      <el-col>
        <RouterView />
      </el-col>
    </elrow>
  </el-main>
</template>




<style scoped>
.content{
  height: calc(100vh - 60px);

}
</style>
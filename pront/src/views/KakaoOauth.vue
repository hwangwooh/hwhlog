<template>
  <section class="test">
    <div v-on:click="kakaoLoginBtn">카카오 연동</div>
  </section>
</template>

<script setup lang="js">



export default {
  name: "test",
  methods: {
    kakaoLoginBtn:function(){

      window.Kakao.init('5ecee12dc48c4ca1e33a6fad3085c02b')

      if (window.Kakao.Auth.getAccessToken()) {
        window.Kakao.API.request({
          url: '/v1/user/unlink',
          success: function (response) {
            console.log(response)
          },
          fail: function (error) {
            console.log(error)
          },
        })
        window.Kakao.Auth.setAccessToken(undefined)
      }


      window.Kakao.Auth.login({
        success: function () {
          window.Kakao.API.request({
            url: '/v2/user/me',
            data: {
              property_keys: ["kakao_account.email"]
            },
            success: async function (response) {
              console.log(response);
              console.log(response.id);
              console.log(response.kakao_account.email);

            },
            fail: function (error) {
              console.log(error)
            },
          })
        },
        fail: function (error) {
          console.log(error)
        },
      })
    }
  }
}

</script>

<style scoped>
.test{ display:flex; justify-content: center; align-items: center; height:100vh; }
div{ width: 200px; height: 40px; background-color:#fdd101; color:white; display:flex; align-items: center; justify-content: center; cursor:pointer; }

</style>
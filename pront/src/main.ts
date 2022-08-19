import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";
import ElementUI from 'element-plus';
import ElementPlus from 'element-plus';

import "normalize.css";
import "element-plus/dist/index.css";
import "./assets/main.css";
import "bootstrap/dist/css/bootstrap-utilities.css";

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(ElementUI);
app.use(ElementPlus);
app.mount("#app");

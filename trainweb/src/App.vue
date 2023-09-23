<template>
  <nav>
    <!--相当于两个a链接，公共的东西，放到app.vue里面。-->
    <router-link to="/">Home</router-link>
    |
    <router-link to="/about">About</router-link>
  </nav>

  <!--  各个路由(url地址)对应不同的内容(vue页面),会填充到 <router-view/> 区域, 主要是展示不同组件的内容。-->
  <router-view/>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>

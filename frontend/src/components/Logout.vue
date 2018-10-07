<template></template>

<script>
export default {
  name: 'Logout',
  created () {
    delete localStorage.token
    this.$store.dispatch('logout')
    this.$router.push('/')

    console.log('logging out')
    this.$router.go(0)
  }
}
</script>
<template>
    <div>
        <template v-if="item.running">
            <v-progress-circular indeterminate color="primary"></v-progress-circular>
        </template>
        <template v-else-if="item.queued">
            <v-icon>queue</v-icon>
        </template>
        <template v-else-if="item.complete">
            <v-icon>check_circle</v-icon>
        </template>
    </div>
</template>

<script>
export default {
  props: ['item'],
  name: 'StatusIndicator'

}
</script>

<style>
</style>

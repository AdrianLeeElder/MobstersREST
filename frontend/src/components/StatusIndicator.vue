<template>
    <div>
        <template v-if="getItem.running">
            <v-progress-circular indeterminate color="primary"></v-progress-circular>
        </template>
        <template v-else-if="getItem.queued">
            <v-icon>queue</v-icon>
        </template>
        <template v-else-if="getItem.complete">
            <v-icon>check_circle</v-icon>
        </template>
    </div>
</template>

<script>
export default {
  name: "StatusIndicator",
  props: {
    item: Object,
    daily: Boolean
  },
  computed: {
    getItem() {
      if (!this.daily) {
        return this.item;
      }

      if ((this.item.actionJobs === null) || !this.item.actionJobs) {
        return {}
      }

      var actionJobResult = {}

      this.item.actionJobs.forEach(actionJob => {
        if (actionJob.daily) {
            console.log(actionJob.complete)
          actionJobResult = actionJob
        }
      });

      return actionJobResult
    }
  }
};
</script>

<style>
</style>

<template>
    <div>
        <!-- <v-card v-else-if="props.item.botMode === 'Buy Property'" class="ma-3">
            <v-list>
                <v-list-tile v-for="(message, index) in props.item.buyPropertyMessages" :key="index">
                    <v-list-tile-content>
                        <v-list-tile-title>{{message}}</v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
            </v-list>
        </v-card> -->
        <v-card class="ma-3">
            <v-tabs v-model="activeActionJobTab">
                <v-tab>
                    Daily
                </v-tab>
                <!-- <v-tab>
                    Buy Property
                </v-tab> -->
                <v-tab-item>
                    <v-data-table :items="dailyActionJob.actionList" :headers="dailyActionJobHeaders" hide-actions>
                        <template slot="items" slot-scope="props">
                            <td>{{props.item.name}}</td>
                            <td>
                                <StatusIndicator :item="props.item"></StatusIndicator>
                            </td>
                        </template>
                    </v-data-table>
                </v-tab-item>
                <!-- <v-tab-item>
                    <v-data-table :items="buyPropertyActionJob.actionList" :headers="dailyActionJobHeaders">
                        <template slot="items" slot-scope="props">
                            <td>{{props.item.name}}</td>
                            <td>
                                <StatusIndicator :item="props.item" />
                            </td>
                        </template>
                    </v-data-table>
                </v-tab-item> -->
            </v-tabs>
        </v-card>
    </div>
</template>

<script>
import StatusIndicator from "./StatusIndicator";

export default {
  name: "ActionJobs",
  props: {
    actionJobs: Array
  },
  data() {
    return {
      activeActionJobTab: 0,
      dailyActionJobHeaders: [
        { text: "Action Name", value: "name", sortable: false },
        { text: "Status", value: "status", sortable: false }
      ]
    };
  },
  computed: {
    dailyActionJob() {
      var resultJob = {};

      if (this.actionJobs !== undefined) {
        this.actionJobs.forEach(actionJob => {
          if (actionJob !== undefined && actionJob.daily) {
            resultJob = actionJob;

            console.log("Found daily job:");
            console.log(resultJob);
          }
        });
      }

      return resultJob;
    },
    buyPropertyActionJob() {
      var resultJob = {};

      if (this.actionJobs !== undefined) {
        this.actionJobs.forEach(actionJob => {
          if (actionJob !== undefined && actionJob.buyProperty) {
            resultJob = actionJob;

            console.log("Found buy property job:");
            console.log(resultJob);
          }
        });
      }

      return resultJob;
    }
  },
  components: {
    StatusIndicator
  }
};
</script>


<template>
  <div>
    <v-layout row wrap>
      <v-flex xs12>
        <v-spacer></v-spacer>
        <v-btn @click="addToQueue" :disabled="selectedAccounts.length === 0" color="primary" class="white--text">
          <v-icon left>queue_play_next</v-icon> Add to Queue
        </v-btn>
      </v-flex>
      <v-flex xs12>
        <v-divider></v-divider>
      </v-flex>
      <v-flex xs12>
        <v-data-table :headers="accountHeaders" :items="mobsters" hide-actions item-key="username" v-model="selectedAccounts" select-all>
          <template slot="items" slot-scope="props">
            <tr @click="expandMobster(props)" style="cursor: pointer">
              <td>
                <v-checkbox v-model="props.selected" primary hide-details @click.stop></v-checkbox>
              </td>
              <td>
                <StatusIndicator :item="props.item"></StatusIndicator>
              </td>
              <td>{{props.item.username}}</td>
              <td>{{props.item.botMode}}</td>
              <td v-if="props.item.priority === 'Low'">
                <v-icon>star_border</v-icon>
              </td>
              <td v-else-if="props.item.priority === 'Medium'">
                <v-icon>star_half</v-icon>
              </td>
              <td v-else-if="props.item.priority === 'High'">
                <v-icon>star</v-icon>
              </td>
              <td>
                <v-btn icon @click.stop="editAccount(props.item)">
                  <v-icon>create</v-icon>
                </v-btn>
                <v-btn icon>
                  <v-icon>more_vert</v-icon>
                </v-btn>
              </td>
            </tr>
          </template>
          <template slot="expand" slot-scope="props">
            <ActionJobs :actionJobs="activeActionJobs" />
          </template>
        </v-data-table>
        <v-dialog v-model="showAccountEditor" max-width="300px">
          <v-card>
            <v-card-title>Edit Account Settings</v-card-title>
            <v-card-text>
              <v-select label="Priority" :items="priorities" v-model="editingAccount.priority"></v-select>
            </v-card-text>
            <v-card-actions>
              <!-- TODO: make this save in database -->
              <v-btn color="primary" justify-right @click="saveAccount()">Save</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-flex>
    </v-layout>
  </div>
</template>

<script>
import { AXIOS } from "./http-common"
import StatusIndicator from "./StatusIndicator"
import ActionJobs from "./ActionJobs"

export default {
  name: "AccountsDashboard",
  data() {
    return {
      accountHeaders: [
        { text: "Status", value: "status" },
        { text: "Username", value: "username" },
        { text: "Priority", value: "priority", sortable: true },
        { text: "Account", value: "account" }
      ],
      mobsters: [],
      editingAccount: {
        priority: "Low",
        botMode: "Daily"
      },
      priorities: ["Low", "Medium", "High"],
      showAccountEditor: false,
      selectedAccounts: [],
      activeActionJobs: []
    };
  },
  methods: {
    editAccount: function(account) {
      this.editingAccount = account;
      this.showAccountEditor = true;
    },
    saveAccount: function() {
      this.showAccountEditor = false;
    },
    addToQueue: function() {
      var usernameList = [];
      this.selectedAccounts.forEach(mobster => {
        usernameList.push(mobster.username);
      });

      AXIOS.post("actionjob/new", {
        usernames: usernameList
      }).then(response => {});
      this.selectedAccounts = [];
    },
    loadMobsters: function() {
      AXIOS.get(`mobster`)
        .then(response => {
          // JSON responses are automatically parsed.
          this.mobsters = response.data;
        }, this)
        .catch(e => {
          // this.errors.push(e)
          this.mobsters = [
            {
              username: "zombie",
              dailyActions: [
                {
                  name: "Login",
                  running: false,
                  complete: true
                },
                {
                  name: "Daily Energy Link",
                  running: true,
                  complete: false,
                  queued: false
                },
                {
                  name: "Logout",
                  running: false,
                  complete: false,
                  queued: true
                }
              ],
              priority: "Low"
            },
            {
              username: "bigtrac",
              dailyActions: [
                {
                  name: "Login",
                  running: false,
                  complete: true
                },
                {
                  name: "Daily Energy Link",
                  running: true,
                  complete: true
                },
                {
                  name: "Logout",
                  running: false,
                  complete: false
                }
              ],
              botMode: "Buy Property",
              buyPropertyMessages: [
                "Bought a Skyscraper for $1000 x 10",
                "Buying more newsstands to see what to buy next"
              ],
              priority: "High",
              running: false,
              queued: false,
              complete: false
            }
          ];
        }, this);
    },
    expandMobster(props) {
      props.expanded = !props.expanded;
      this.mobsterToTickActionJobs = props.item;
    },
    updateMobsterActionJobs() {
      setInterval(() => {
        if (this.mobsterToTickActionJobs !== undefined) {
          var username = this.mobsterToTickActionJobs.username;
          if (
            Object.keys(this.mobsterToTickActionJobs).length > 0 &&
            username !== undefined &&
            username.length > 0
          ) {
            AXIOS.get("actionjob/mobster/" + username).then((response) => {
              this.activeActionJobs = response.data;
            });
          }
        }
      }, 5000);
    }
  },
  created() {
    this.loadMobsters();
    this.updateMobsterActionJobs();
  },
  computed: {
    getStatusIcon: function(dailyAction) {
      if (dailyAction.running) {
        return "running";
      }
    }
  },
  components: {
    StatusIndicator,
    ActionJobs
  }
};
</script>

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
            <tr @click="props.expanded = !props.expanded" style="cursor: pointer">
              <td>
                <v-checkbox v-model="props.selected" primary hide-details @click.stop></v-checkbox>
              </td>
              <td>
                <status-indicator :item="props.item"></status-indicator>
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
            <v-card class="ma-3" v-if="props.item.botMode === 'Daily'">
              <v-data-table :items="props.item.dailyActions" :headers="dailyActionHeaders">
                <template slot="items" slot-scope="props">
                  <td>{{props.item.name}}</td>
                  <td>
                    <status-indicator :item="props.item" />
                  </td>
                </template>
              </v-data-table>
            </v-card>
            <v-card v-else-if="props.item.botMode === 'Buy Property'" class="ma-3">
              <v-list>
                <v-list-tile v-for="(message, index) in props.item.buyPropertyMessages" :key="index">
                  <v-list-tile-content>
                    <v-list-tile-title>{{message}}</v-list-tile-title>
                  </v-list-tile-content>
                </v-list-tile>
              </v-list>
            </v-card>
          </template>
        </v-data-table>
        <v-dialog v-model="showAccountEditor" max-width="300px">
          <v-card>
            <v-card-title>Edit Account Settings</v-card-title>
            <v-card-text>
              <v-select label="Priority" :items="priorities" v-model="editingAccount.priority"></v-select>
              <v-select label="Bot Mode" :items="botModes" v-model="editingAccount.botMode"></v-select>
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
import { AXIOS } from './http-common'
import StatusIndicator from './StatusIndicator'

export default {
  name: 'AccountsDashboard',
  data () {
    return {
      dailyActionHeaders: [
        { text: 'Action Name', value: 'name', sortable: false },
        { text: 'Status', value: 'running', sortable: false }
      ],
      accountHeaders: [
        { text: 'Status', value: 'status' },
        { text: 'Username', value: 'username' },
        { text: 'Bot Mode', value: 'botMode' },
        { text: 'Priority', value: 'priority', sortable: true },
        { text: 'Account', value: 'account' }
      ],
      mobsters: [],
      editingAccount: {
        priority: 'Low',
        botMode: 'Daily'
      },
      priorities: ['Low', 'Medium', 'High'],
      botModes: ['Buy Property', 'Daily'],
      showAccountEditor: false,
      selectedAccounts: []
    }
  },
  methods: {
    editAccount: function (account) {
      this.editingAccount = account
      this.showAccountEditor = true
    },
    saveAccount: function () {
      this.showAccountEditor = false
    },
    addToQueue: function () {
      this.selectedAccounts.forEach((mobster) => {
        // TODO: MAKE THIS ACTUAL UPDATE API
        if (!mobster.running && !mobster.queued) {
          mobster.queued = true
        }
      })

      this.selectedAccounts = []
    }
  },
  created () {
    AXIOS.get(`mobsters`)
      .then(response => {
        // JSON responses are automatically parsed.
        this.mobsters = response.data
      }, this)
      .catch(e => {
        // this.errors.push(e)
        this.mobsters = [
          {
            username: 'zombie',
            dailyActions: [
              {
                name: 'Login',
                running: false,
                complete: true
              },
              {
                name: 'Daily Energy Link',
                running: true,
                complete: false,
                queued: false
              },
              {
                name: 'Logout',
                running: false,
                complete: false,
                queued: true
              }
            ],
            botMode: 'Daily',
            priority: 'Low',
            running: true,
            queued: false,
            complete: false
          },
          {
            username: 'bigtrac',
            dailyActions: [
              {
                name: 'Login',
                running: false,
                complete: true,
                queued: false
              },
              {
                name: 'Daily Energy Link',
                running: true,
                complete: true,
                queued: false
              },
              {
                name: 'Logout',
                running: false,
                complete: false,
                queued: true
              }
            ],
            botMode: 'Buy Property',
            buyPropertyMessages: [
              'Bought a Skyscraper for $1000 x 10',
              'Buying more newsstands to see what to buy next'
            ],
            priority: 'High',
            running: false,
            queued: false,
            complete: false
          }
        ]
      }, this)
  },
  computed: {
    getStatusIcon: function (dailyAction) {
      if (dailyAction.running) {
        return 'running'
      }
    }
  },
  components: {
    StatusIndicator
  }
}
</script>

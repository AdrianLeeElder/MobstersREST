<template>
  <div>
    <v-alert v-model="showDeletedSuccess" dismissible type="success">
      Account ({{deletingAccount.username}}) successfully deleted.
    </v-alert>

    <v-alert v-model="showDeletedFailure" dismissible type="error">
      Unable to delete account ({{deletingAccount.username}})!
    </v-alert>

    <v-alert v-model="showAccountEditedSuccessfully" dismissible type="success">
      Successfully updated settings for {{editingAccount.username}}.
    </v-alert>

    <v-alert v-model="showAccountEditedFailure" dismissible type="error">
      Unable to save account settings for {{editingAccount.username}}.
    </v-alert>

    <v-layout row wrap class="ma-4">
      <v-flex x8>
        <v-card class="pa-3">
          <v-card-title>
            <v-spacer></v-spacer>
            <v-text-field v-model="searchMobster" append-icon="search" label="Search" single-line hide-details></v-text-field>
          </v-card-title>

          <v-data-table :headers="accountHeaders" :items="mobsters" class="elevation-2" :loading="loadingMobsters" :search="searchMobster">
            <v-progress-linear slot="progress" color="blue" indeterminate></v-progress-linear>
            <template slot="items" slot-scope="props">
              <tr @click="expandMobster(props)" style="cursor: pointer">
                <td>
                  <StatusIndicator :item="props.item" :daily="true"></StatusIndicator>
                </td>
                <td>{{props.item.username}}</td>
                <td>
                  <template v-if="props.item.priority === 'Low'">
                    <v-icon>star_border</v-icon>
                  </template>
                  <template v-else-if="props.item.priority === 'Medium'">
                    <v-icon>star_half</v-icon>
                  </template>
                  <template v-else-if="props.item.priority === 'High'">
                    <v-icon>star</v-icon>
                  </template>
                </td>

                <td class="justify-center layout px-0">
                  <v-icon small class="mr-2" @click.stop="editAccount(props.item)">
                    edit
                  </v-icon>
                  <v-icon small @click.stop="areYouSureDeleteAccountConfirm(props.item)">
                    delete
                  </v-icon>
                </td>
              </tr>
            </template>
            <template slot="expand" slot-scope="props">
              <ActionJobs :actionJobs="props.item.actionJobs" />
            </template>
          </v-data-table>
          <v-dialog v-model="showAccountDeletionConfirm" width="500">
            <v-card>
              <v-card-title class="headline red lighten-2" primary-title>
                Delete Account: {{deletingAccount.username}}
              </v-card-title>

              <v-card-text>
                Are you sure you want to delete this account?
              </v-card-text>

              <v-divider></v-divider>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="error" flat @click.native="deleteAccount">
                  Delete
                </v-btn>
                <v-btn color="secondary" flat @click.native="closeDeleteAccountConfirmation">
                  Cancel
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-card>
      </v-flex>
    </v-layout>

    <v-dialog v-model="showAccountEditor" max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">Mobster: {{ editingAccount.username }}</span>
        </v-card-title>

        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex xs12 sm6 md4>
                <v-text-field v-model="editingAccount.username" label="Username"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-text-field v-model="editingAccount.password" label="Password" type="password"></v-text-field>
              </v-flex>
              <v-flex xs12 sm6 md4>
                <v-select :items="priorities" v-model="editingAccount.priority"></v-select>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" flat @click.native="closeAccountEditor">Cancel</v-btn>
          <v-btn color="blue darken-1" flat @click.native="saveAccount">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { AXIOS } from "./http-common";
import StatusIndicator from "./StatusIndicator";
import ActionJobs from "./ActionJobs";

export default {
  name: "AccountsDashboard",
  data() {
    return {
      accountHeaders: [
        { text: "Status", value: "status" },
        { text: "Username", value: "username" },
        { text: "Priority", value: "priority", sortable: true },
        { text: "Account", value: "account", sortable: false }
      ],
      mobsters: [],
      editingAccount: {
        priority: "Low",
        username: "",
        password: ""
      },
      priorities: ["Low", "Medium", "High"],
      showAccountEditor: false,
      selectedAccounts: [],
      activeActionJobs: [],
      tempMobsters: [],
      deletingAccount: { username: "" },
      showDeletedSuccess: false,
      showAccountDeletionConfirm: false,
      showDeletedFailure: false,
      loadingMobsters: true,
      searchMobster: "",
      showAccountEditedSuccessfully: false,
      showAccountEditedFailure: false
    };
  },
  methods: {
    closeDeleteAccountConfirmation() {
      this.showAccountDeletionConfirm = false;
    },
    closeAccountEditor() {
      this.showAccountEditor = false;
      this.editingAccount = {};
    },
    editAccount: function(account) {
      this.editingAccount = account;
      this.showAccountEditor = true;

      console.log("editing account");
      console.log(this.editingAccount);
    },
    deleteAccount() {
      AXIOS.delete("mobster/delete/" + this.deletingAccount.id).then(
        response => {
          if (response.data.status === 200) {
            this.showDeletedSuccess = true;
            this.showAccountDeletionConfirm = false;
          } else {
            this.showDeletedFailure = true;
          }
        }
      );
    },
    saveAccount() {
      console.log("save account");
      console.log(this.editingAccount);
      AXIOS.post("mobster", this.editingAccount).then(response => {
        // JSON responses are automatically parsed.
        if (response.data.status === 200) {
          this.showAccountEditedSuccessfully = true;
        } else {
          this.showAccountEditedFailure = false;
        }

        this.closeAccountEditor();
      });
    },
    areYouSureDeleteAccountConfirm(accountToDelete) {
      this.deletingAccount = accountToDelete;

      this.showAccountDeletionConfirm = true;
    },
    close() {
      this.showAccountEditor = false;
    },
    loadMobsters() {
      this.mobsters.forEach(mobster => {
        var tempMobster = this.tempMobsters.find(
          tmpMob => tmpMob.username === mobster.username
        );

        mobster.actionJobs = tempMobster.actionJobs;
      });
    },
    setTempMobsters(refreshMobsters) {
      AXIOS.get("mobster").then(response => {
        // JSON responses are automatically parsed.
        this.tempMobsters = response.data;

        if (refreshMobsters) {
          this.mobsters = this.tempMobsters;
        }

        this.loadingMobsters = false;
      });
    },
    expandMobster(props) {
      this.mobsterToTickActionJobs = props.item;
      props.expanded = !props.expanded;
    },
    tickTempMobsters() {
      setInterval(() => {
        this.setTempMobsters(false);
      }, 2000);
    },
    tickLoadMobsters() {
      setInterval(() => {
        this.loadMobsters();
      }, 2000);
    }
  },
  created() {
    this.loadingMobsters = true;
    this.setTempMobsters(true);
    this.tickTempMobsters();
    this.tickLoadMobsters();
  },
  components: {
    StatusIndicator,
    ActionJobs
  }
};
</script>
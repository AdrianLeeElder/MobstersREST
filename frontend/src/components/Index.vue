<template>
    <div>
        <v-toolbar dark color="primary" app>
            <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
            <v-toolbar-title class="white--text">Mobsters Bot - Welcome, {{currentUser.username}}</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn flat to="/logout">
               Logout
            </v-btn>
        </v-toolbar>
        <v-navigation-drawer v-model="drawer" app>
            <v-list>
                <v-list-group>
                    <v-list-tile slot="activator">
                        <v-list-tile-action>
                            <v-icon>account_circle</v-icon>
                        </v-list-tile-action>
                        <v-list-tile-title>
                            Mobsters
                        </v-list-tile-title>
                    </v-list-tile>
                </v-list-group>
            </v-list>
        </v-navigation-drawer>
        <v-content>
            <accounts-dashboard></accounts-dashboard>
        </v-content>
    </div>
</template>

<script>
import AccountsDashboard from './AccountsDashboard'
import { mapGetters } from 'vuex'
export default {
    name: 'index',
    data() {
        return {
            drawer: false
        }
    },
    components: {
        AccountsDashboard
    },
    computed: {
        ...mapGetters({ currentUser: 'currentUser'})
    }
}
</script>

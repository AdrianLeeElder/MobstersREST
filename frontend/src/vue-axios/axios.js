import axios from 'axios'

const MOBSTERS_API_URL = process.env.MOBSTERS_API_URL || 'http://mobster-players-revenge.herokuapp.com/api/v1'

var mobstersApi = axios.create({
  baseURL: MOBSTERS_API_URL,
  headers: {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + localStorage.token
  }
})

export default mobstersApi
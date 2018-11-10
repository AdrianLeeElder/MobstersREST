import axios from 'axios'

const MOBSTERS_API_URL = 'http://localhost:8080/api/v1'

var mobstersApi = axios.create({
  baseURL: MOBSTERS_API_URL,
  headers: {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + localStorage.token
  }
})

export default mobstersApi
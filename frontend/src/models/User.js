import JwtDecode from 'jwt-decode'

export default class User {
  static from (token) {
    try {
      let obj = JwtDecode(token)
      console.log('attempting to decode token')
      console.log(obj)
      return new User(obj)
    } catch (_) {
      return null
    }
  }

  constructor ({ sub, role }) {
    this.username = sub
    this.role = role
  }

  get isAdmin () {
    var admin = false
    this.role.forEach((rol) => {
      if (rol === 'ROLE_ADMIN') {
        admin = true
      }
    })

    return admin
  }
}
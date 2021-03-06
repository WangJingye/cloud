'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  REQUEST_HOST: '"http://127.0.0.1:8080/website/"',
  IMAGE_HOST: '"http://localhost/"'
})

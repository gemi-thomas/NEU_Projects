exports['test Invalid Credential'] = function(assert, done) {


  const https = require('http')
  const options = {
    hostname: 'localhost',
    port: 8080,
    path: '/v1/user/self',
    method: 'GET'
  }
  console.log("Making http request");
  const req = https.request(options, res => {
    console.log(`statusCode: ${res.statusCode}`)

    assert.equal(res.statusCode, 500, 'Invalid Credential') // will log result
    done() // telling test runner that we're done with this test
  })

  req.on('error', error => {
  console.error(error)
  })
  req.end()


}

 
if (module == require.main) require('test').run(exports)
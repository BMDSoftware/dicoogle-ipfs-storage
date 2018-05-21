var ipfsAPI = require('ipfs-api')

// connect to ipfs daemon API server
var ipfs = ipfsAPI('localhost', '5001', {protocol: 'http'}) // leaving out the arguments will default to these values

// or connect with multiaddr
var ipfs = ipfsAPI('/ip4/127.0.0.1/tcp/5002')

// or using options
//var ipfs = ipfsAPI({host: 'localhost', port: '5002', protocol: 'http'})

// or specifying a specific API path
//var ipfs = ipfsAPI({host: '1.1.1.1', port: '80', 'api-path': '/ipfs/api/v0'})


//const validCID = 'QmQ2r6iMNpky5f1m4cnm3Yqw8VSvjuKpTcK1X7dBR1LkJF'
const validCID = 'QmdWFrmDRZmAG8kMX5UG2RmEWpNbUkppeuiLcvNTBnU8df';
ipfs.files.get(validCID, function (err, files) {
  files.forEach((file) => {
    console.log(file.path)
    console.log(file.content.toString('utf8'))
  })
})

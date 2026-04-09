import JSEncrypt from 'jsencrypt/bin/jsencrypt.min'

// 密钥对生成 http://web.chacuo.net/netrsakeypair

const publicKey = 'MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKoR8mX0rGKLqzcWmOzbfj64K8ZIgOdH\n' +
  'nzkXSOVOZbFu/TJhZ7rFAN+eaGkl3C4buccQd/EjEsj9ir7ijT7h96MCAwEAAQ=='

const privateKey = 'MIIBpjBABgkqhkiG9w0BBQ0wMzAbBgkqhkiG9w0BBQwwDgQIHc5MpZ9tssICAggA\n' +
  'MBQGCCqGSIb3DQMHBAhda4KZQoUEEwSCAWC9HPPxpdOcqJjW6DMdoqBYtwtbU2oA\n' +
  'ItWZqfljpr6bkVDLmka/lgYEbmhg19T2vVtVlFC8xXsv959VUYh4FfnpuWKIPDes\n' +
  'GrsZnDfbt6npl7eeiBWfuR8qSCsVnkQt3A1CvEg36WoVmvrw9dNpjcrHDJHRISOE\n' +
  'qUiAZPvmK/1KySZvTI5e3HAlUoXVxPB+pRWL5IN578SDZ2J2c4uStM1dBhlevlOF\n' +
  'XcvnvHfqWnzEOF+pavrWVZU7Z82/UJIMwkY1h8LBXqrMn1/4hO05XWOdMtYrGWKm\n' +
  'T/8OE+pgz0bN4cwoA7bASzxolAIh/WtcqRYZXJyg5aZmkELXVPR62xuQ5X1iBKRT\n' +
  'yFyaj2p9VqA1DKAhHcacKARjoev0xRrS4OYNuP7vf1CEhS4Hr4ewxsTb+AfgKm+R\n' +
  '4CZ08UfNo/alnVW1nkGqrAEoCBIoivNuJ7WD9ZzdO2cW5rT9uBjYGqsc'

// 加密
export function encrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPublicKey(publicKey) // 设置公钥
  return encryptor.encrypt(txt) // 对数据进行加密
}

// 解密
export function decrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPrivateKey(privateKey) // 设置私钥
  return encryptor.decrypt(txt) // 对数据进行解密
}


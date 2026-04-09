package com.ruoyi.common.utils.sign;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author dongsheng
 * @date 2024/5/9 22:46
 * Description：RsaUtuils
 */
public class RsaUtuils {
    // RSA 解密
    public static String decryptRSA(String encryptedText, String privateKeyStr) throws Exception {
        // 将私钥字符串解码为字节数组
        byte[] privateKeyBytes = Base64.decode(privateKeyStr);
        // 创建 PKCS8EncodedKeySpec 对象
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
        // 使用 KeyFactory 根据 KeySpec 生成 PrivateKey 对象
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(spec);
        // 创建 Cipher 对象并进行解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedBytes = Base64.decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}

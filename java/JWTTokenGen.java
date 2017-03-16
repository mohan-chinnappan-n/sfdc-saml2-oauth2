import org.apache.commons.codec.binary.Base64;
import java.io.*;
import java.security.*;
import java.text.MessageFormat;

/*
-----------------------
 compile:
 javac -cp ./lib/*:. JWTTokenGen.java

 run:
 java -cp ./lib/*:. JWTTokenGen

--------------------------
*/

public class JWTTokenGen {

public static void main(String[] args) {

        // TODO: make sure to fill the correct values for next four lines
        String KESTORE_JKS_FILE = "./path/to/keystore.jks";
        String KESTORE_PASSWORD= "keystorepassword";
        String CERT_ALIAS = "certalias";
        String PRIVATE_KEY_PASSWORD = "privatekeypassword";

        //-----------

        try {
                StringBuffer token = new StringBuffer();

                //Encode the JWT Header and add it to our string to sign
                String header = "{\"alg\":\"RS256\"}";
                token.append(Base64.encodeBase64URLSafeString(header.getBytes("UTF-8")));

                //Separate with a period
                token.append(".");

                //Create the JWT Claims Object
                String[] claimArray = new String[4];
                // Client_id
                claimArray[0] = "3MVG99OxTyEMCQ3gNp2PjkqeZKxnmAiG1xV4oHh9AKL_rSK.BoSVPGZHQukXnVjzRgSuQqGn75NL7yfkQcyy7";
                // SFDC username
                claimArray[1] = "my@email.com";
                // Audience
                claimArray[2] = "https://login.salesforce.com";
                claimArray[3] = Long.toString( ( System.currentTimeMillis()/1000 ) + 300);

                MessageFormat claims;
                String claimTemplate = "'{'\"iss\": \"{0}\", \"sub\": \"{1}\", \"aud\": \"{2}\", \"exp\": \"{3}\"'}'";
                claims = new MessageFormat(claimTemplate);
                String payload = claims.format(claimArray);

                //Add the encoded claims object (JWT_str)
                token.append(Base64.encodeBase64URLSafeString(payload.getBytes("UTF-8")));

                //Load the private key from a keystore
                KeyStore keystore = KeyStore.getInstance("JKS");
                keystore.load(new FileInputStream(KESTORE_JKS_FILE),
                              KESTORE_PASSWORD.toCharArray()
                              );

                PrivateKey privateKey = (PrivateKey) keystore.getKey(CERT_ALIAS, PRIVATE_KEY_PASSWORD.toCharArray());

                //Sign the JWT Header + "." + JWT Claims Object
                Signature signature = Signature.getInstance("SHA256withRSA");
                signature.initSign(privateKey);
                // sign the JWT_str to get signedPayload
                signature.update(token.toString().getBytes("UTF-8"));
                String signedPayload = Base64.encodeBase64URLSafeString(signature.sign());

                //Separate with a period
                token.append(".");

                //Add the encoded signature
                // JWT_token = JWT_str + "." +   base64_encoded_signature
                token.append(signedPayload);

                System.out.println(token.toString());

        } catch (Exception e) {
                e.printStackTrace();
        }
}
}

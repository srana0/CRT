

/**
* The Program will provide CRT Encryption Scheme
* 
*
* @author  SUBHABRATA RANA
* @version 1.0
* @since   2022-10-14
* @assignment : 01 
*/
import java.util.*;
import java.math.*;
import java.security.*;
import java.time.Duration;
import java.time.Instant;

public class CRTCryptographyModule {
	
	private static long elapsedTime_RSA=0;
	private static long elapsedTime_CRT=0;
	
	public static void main(String[] args) 
	{
		
		try
		{
			BigInteger ciphertext=BigInteger.ZERO;
			BigInteger message=BigInteger.ZERO;
			
			HashMap<Character, BigInteger> private_public_Params=generatePrivatePublicKeyPair();
			BigInteger decryptionExponent=private_public_Params.get('d');
			BigInteger encryptionExponent=private_public_Params.get('e');
			BigInteger primeNumber1=private_public_Params.get('p');
			BigInteger primeNumber2=private_public_Params.get('q');
			BigInteger compositeModulus=private_public_Params.get('n');
			
			System.out.println("\n------------------------------------------------");
			System.out.println("A2: RSA AND CRT DECRYPTION");
			System.out.println("------------------------------------------------\n");
		
							
			message=generateRanmonMessage(primeNumber1);
			ciphertext=EncryptionModule(message,encryptionExponent, compositeModulus);	
			
			// Decrypt using CRT 
			DecryptionModule_CRT(ciphertext,decryptionExponent,compositeModulus,primeNumber1,primeNumber2);
			
		   // Decrypt using RSA
			DecryptionModule_TraditionalRSA(ciphertext,decryptionExponent,compositeModulus);
			
			// Analyze the results
			analyzeResults();
		}
		 catch(Exception ex)
		  {
			  System.out.println("Exception occurred in the main module : "+ex);
		  }
		
			
			
	}// main


	// This function will generate Random Message
	private static BigInteger generateRanmonMessage(BigInteger number)
	{	
		 BigInteger message=BigInteger.ZERO;
		  try
		  {
			  SecureRandom secureNumber = new SecureRandom();	
			   message = new BigInteger(number.bitLength(), secureNumber);	
		  }
	   catch(Exception ex)
		{
			  System.out.println("Encryption occures while generating random number:"+ex);
		}
		  
		  return message;
	}
	  
	  // This function will return the private public key sets
	  private static HashMap<Character,BigInteger> generatePrivatePublicKeyPair()
	  {
		
		  HashMap<Character,BigInteger> privatePublicKeyStore=new HashMap<Character,BigInteger>();
		
		  try
		  {
			  // Prime Numbers are private
			  BigInteger primeNumber1=new BigInteger("19211916981990472618936322908621863986876987146317321175477459636156953561475008733870517275438245830106443145241548501528064000686696553079813968930084003413592173929258239545538559059522893001415540383237712787805857248668921475503029012210091798624401493551321836739170290569343885146402734119714622761918874473987849224658821203492683692059569546468953937059529709368583742816455260753650612502430591087268113652659115398868234585603351162620007030560547611");
			  BigInteger primeNumber2=new BigInteger("49400957163547757452528775346560420645353827504469813702447095057241998403355821905395551250978714023163401985077729384422721713135644084394023796644398582673187943364713315617271802772949577464712104737208148338528834981720321532125957782517699692081175107563795482281654333294693930543491780359799856300841301804870312412567636723373557700882499622073341225199446003974972311496703259471182056856143760293363135470539860065760306974196552067736902898897585691");
			  
			  
			  // Composite Modulus
			  BigInteger compositeModulus=primeNumber1.multiply(primeNumber2);
			  
			  // Generate Fi(n)=(p-1) * (q-1)
			  BigInteger fi_n=(primeNumber1.subtract(BigInteger.ONE)).multiply(primeNumber2.subtract(BigInteger.ONE));
			  
			  BigInteger encryptionExponent=getEncryptionExponant(fi_n);
					  
			  // Get the public Key
			  BigInteger decryptionExponent = encryptionExponent.modInverse(fi_n);		
			  
			  //Store the values in to the hash map
			  privatePublicKeyStore.put('d', decryptionExponent);
			  privatePublicKeyStore.put('e', encryptionExponent);
			  privatePublicKeyStore.put('p', primeNumber1);
			  privatePublicKeyStore.put('q', primeNumber2);
			  privatePublicKeyStore.put('n', compositeModulus); 
			
		  }
		  catch(Exception ex)
		  {
			  System.out.println("Exception occurred while generating private public key pairs : " +ex);
		  }
		  
		  return privatePublicKeyStore;
	  }
	
	  
	  // This function returns Encryption Exponent
	  // ComapareTo Function in BigInteger Returns -1 (if < targetValue) or 0(If ==targetValue) or 1 (if > targetValue)	
     private static BigInteger getEncryptionExponant(BigInteger fi_N)
     {
    	 BigInteger exponent=BigInteger.ZERO;     
         BigInteger gcdValue=BigInteger.ZERO;
    	 try
    	 {
    		// gcdValue= exponent.gcd(fi_N);
    		 for (exponent = BigInteger.valueOf(2); exponent.compareTo(fi_N)==-1; exponent=exponent.add(BigInteger.ONE)) 
			   {
    			    
    			  gcdValue=exponent.gcd(fi_N);    	
				  if (gcdValue.compareTo(BigInteger.valueOf(1))==0)
				  {
		                break;
		          }
		     }
			   
    	 }
    	 catch(Exception ex)
    	 {
    		 System.out.println("Exception occurred while getiting exponent : "+ex);
    	 }
    
    	 return exponent;
     }
     
 	
	 // This function will encrypt data
	  private static BigInteger EncryptionModule(BigInteger message,BigInteger encryptionExponent, BigInteger compositeModulus)
	  {		  
		  BigInteger ciphertext=BigInteger.ZERO;
		  try
		  {
			 			  
			  ciphertext=message.modPow(encryptionExponent,compositeModulus);	  
			  
			  System.out.println("ENCRYPTION USING  RSA:\n");
			  System.out.println("Chosen message is m : "+message);
			  System.out.println("Ciphertext is c     : "+ciphertext);
			 
		  }
		  catch(Exception ex)
		  {
			  System.out.println("Exception occurred while encrypting data : "+ex);
		  }
		  
		  return ciphertext;
		  		 
	  }
	  
	  
	  // This function will decrypt data using traditional RSA
	  private static void DecryptionModule_TraditionalRSA(BigInteger ciphertext, BigInteger decryptionExponent, BigInteger compositeModulus)
	  {		 
			System.out.println("------------------------------------------------\n");
			System.out.println("DECRYPTION USING TRADITIONAL RSA: \n");
		    long nano_startTime_RSA=0;
		 //   long elapsedTime_RSA=0;
		    long nano_endTime_RSA=0;
		    BigInteger plaintext=BigInteger.ZERO;
		  
		 
		  try
		  { 	
			  nano_startTime_RSA= System.nanoTime();
			  
			  plaintext=ciphertext.modPow(decryptionExponent,compositeModulus);
			  
			  nano_endTime_RSA = System.nanoTime();
			  elapsedTime_RSA= nano_endTime_RSA-nano_startTime_RSA;
			  
			  System.out.println("Ciphertext  : "+ ciphertext);
			  System.out.println("Plaintext   : "+ plaintext);
			  System.out.println("Time taken in decryption using Traditional RSA scheme : "+elapsedTime_RSA +" nano seconds");
		  }
		  catch(Exception ex)
		  {
			  System.out.println("Exception occurred while decrypting data : "+ex);
		  }
	
	  }
  
	  
	  // This function will decrypt data using traditional RSA
	  private static void DecryptionModule_CRT(BigInteger ciphertext, BigInteger d, BigInteger n, BigInteger p, BigInteger q)
	  {		 
		  System.out.println("------------------------------------------------\n");
		  System.out.println("DECRYPTION USING CRT: \n");
		  BigInteger p_prime=BigInteger.ZERO;	
		  BigInteger d_p=BigInteger.ZERO;
		  BigInteger c_p=BigInteger.ZERO;
		  BigInteger m_p=BigInteger.ZERO;
		
		  		
		  BigInteger q_prime=BigInteger.ZERO;
		  BigInteger d_q=BigInteger.ZERO;
		  BigInteger c_q=BigInteger.ZERO;
		  BigInteger m_q=BigInteger.ZERO;
		
		  
		  BigInteger plaintext=BigInteger.ZERO;
			
		  long nano_startTime_CRT=0;
		  long nano_endTime_CRT=0;
		  //long elapsedTime_CRT=0;
		  try
		  { 
						  		
			  q_prime= q.modInverse(p);
			  d_p=d.mod(p.subtract(BigInteger.ONE));
			  p_prime= p.modInverse(q);
			  d_q=d.mod(q.subtract(BigInteger.ONE));
			  
			  // As per the question, including time taken for c_p, c_q, m_p, m_q and m
			  nano_startTime_CRT = System.nanoTime(); 
			  c_p=ciphertext.mod(p);
			  m_p=c_p.modPow(d_p,p);
			  
			  c_q=ciphertext.mod(q);
			  m_q=c_q.modPow(d_q,q);
			  			  
			  plaintext=((m_p.multiply(q).multiply(q_prime)).add(m_q.multiply(p).multiply(p_prime))).mod(n);
			  	
			  nano_endTime_CRT = System.nanoTime(); 
			  elapsedTime_CRT = nano_endTime_CRT - nano_startTime_CRT;
			  
			  System.out.println("Ciphertext:"+ ciphertext);
			  System.out.println("Plaintext: "+ plaintext);
			  System.out.println("m_p      : "+ m_p);
			  System.out.println("m_q      : "+ m_q);
			  System.out.println("Time taken in decryption using CRT scheme : "+elapsedTime_CRT +" nano seconds");
		  }
		  catch(Exception ex)
		  {
			  System.out.println("Exception occurred while decrypting data : "+ex);
		  }
		  		
		  
	  }//
	  
	  private static void analyzeResults()
	  {		 
			  try
			  {
				  System.out.println("------------------------------------------------\n");
				    // ANALYSIS
					System.out.println("COMPARISON BETWEEN CRT VS RSA DECRYPTION TIME: \n");
					if(elapsedTime_CRT<elapsedTime_RSA)
					{
						long diff_in_dryption=elapsedTime_RSA-elapsedTime_CRT;
						System.out.println("Tradional RSA takes : "+ diff_in_dryption +" nano seconds longer than CRT scheme for decryption.\n" );
					}
					else if(elapsedTime_CRT>elapsedTime_RSA)
					{
						long diff_in_dryption=elapsedTime_CRT-elapsedTime_RSA;
						System.out.println("CRT scheme takes : "+ diff_in_dryption +" nano seconds longer than Traditional RSA scheme for decryption.\n" );
					}
					System.out.println("------------------------------------------------");
					System.out.println("Note: Based on multiple run, sometimes RSA takes longer time for decryption and sometimes CRT takes longer time.");
					System.out.println("------------------------------------------------");
			  }
	       catch(Exception ex)
			{
				  System.out.println("Encryption occures while generating random number:"+ex);
			}
	  }

}

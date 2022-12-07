# Chinese Remainder Theorem 

This program is on the implementation of speeding up the RSA decryption using CRT. 

Parameters p<sub>k</sub>= (e, n) and sk = (d, p, q).
Randomly pick a large message m from Z<sub>n∗ </sub>
ciphertext c = m<sup>e</sup> mod n.  
</br>
For the faster RSA decryption, the decryption function (m' = c<sup>d</sup> mod n) is computed using CRT as follows:
</br></br>
q' = q<sup>−1</sup> mod p	
d<sub>p</sub> = d  mod (p − 1)	
c<sub>p</sub> = c  mod p	
 
mp = cdp
 
mod p	mq
 
= cdq
 
mod q
 
m = mp · q · qJ + mq · p · pJ mod n.

</br>
</br>
p' = p<sup>−1</sup> mod q </br>
d<sub>q</sub> = d mod (q − 1) </br>
c<sub>q</sub> = c mod q </br>


</br>
</br>
Please implement the above CRT based decryption in your chosen programming language. You are prohibited to use any CRT based available implementation on Internet or other sources.
Please compare the execution time of the naive RSA decryption in A1 and the CRT based decryption in A2. When you capture the time, you need to include the timings of computing cp, cq, mp, mq and m.

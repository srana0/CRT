# Chinese Remainder Theorem 

This program is on the implementation of speeding up the RSA decryption using CRT. 
Use the parameters pk = (e, n) and sk = (d, p, q) from A1 and randomly pick a large message m from Z<sub>n∗ </sub>
and compute the ciphertext c = me   mod n.  For the faster RSA decryption, the decryption function (mJ = cd mod n) is computed using CRT as follows:
qJ = q−1 mod p	pJ = p−1 mod q
dp = d  mod (p − 1)	dq = d mod (q − 1)
cp = c  mod p	cq = c mod q
 
mp = cdp
 
mod p	mq
 
= cdq
 
mod q
 
m = mp · q · qJ + mq · p · pJ mod n.
Please implement the above CRT based decryption in your chosen programming language. You are prohibited to use any CRT based available implementation on Internet or other sources.
Please compare the execution time of the naive RSA decryption in A1 and the CRT based decryption in A2. When you capture the time, you need to include the timings of computing cp, cq, mp, mq and m.

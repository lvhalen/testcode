//here’s what I have so far. Each function will get 16 unique character sets to work with
//function 1 gets s1, function 2 gets s2, and so on. Please try to include the entire set
//in your individual programs.

import java.util.Scanner;

public class passwordprogram {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.printf("Please enter password phrase:\n");
        String passPhrase = in.nextLine();
        System.out.printf("Please enter site phrase:\n");
        String sitePhrase = in.nextLine();
        in.close();
        int x = passPhrase.length();
        int y = sitePhrase.length();
        int z = x + y;
        char[] tempChar = new char[z];
        char[] charPool = new char[80];
        for (int i=0;i<x;i++){
            tempChar[i] = passPhrase.charAt(i);
        }
        for (int i=0;i<y;i++){
            tempChar[i+x] = sitePhrase.charAt(i);
        }
        while (z % 2 == 0 || z % 5 == 0){
            z++;
        }
        for (int i=0;i<80;i++){
            charPool[(z*i)%80] = tempChar[i%(x+y)];
        }
        String s1 = new String(charPool,0,16);
        String s2 = new String(charPool,16,16);
        String s3 = new String(charPool,32,16);
        String s4 = new String(charPool,48,16);
        String s5 = new String(charPool,64,16);
        System.out.printf("Individual sets are:%s %s %s %s %s\n", s1,s2,s3,s4,s5);
        StringBuilder password = new StringBuilder(function1(s1));
        password.append(function2(s2));
        password.append(function3(s3));
        password.append(function4(s4));
        password.append(function5(s5));
        System.out.printf("Password is: %s\n", function6(password));
    }
    private static String function1(String x){ 				//Viginere shift, returns string of 3 lower text chars
        char[] lower = new char[8];
        char[] ret = new char[3];
        int array[] = {97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
        int a,b,c = 0;
        for(int i = 0; i < 8; i++){
            a = (int) x.charAt(i);				//chars to ints
            b = (int) x.charAt(i+8);
            c = (a + b) % 26;				//run input through Viginere
            lower[i] = (char) array[c];			//create 8 lower case characters
        }
        a = 0;
        b = 1;
        for (int i = 0; i < 16; i++){					//Affine cipher for char selection
            a+=x.charAt(i);
            b*=x.charAt(i);
            b=b%71+1;
        }
        a%=8;
        if (a%2 == 0) a++; 					//ensure a is relatively prime to 8
        for(int i = 1; i < 4; i++){					//select three chars from pool of 8
            ret[i-1] = lower[(a*i+b)%8];			//NOTE: upper/lower input change will not affect selection
        }
        System.out.print("Function 1 returns:");
        System.out.println(ret);
        return new String(ret);					//return string of 3 chars to append
    }

    private static String function2(String x){ 				//hill cipher, returns string of 3 upper text chars
        //Use three rounds of the Hill cipher to generate three uppercase letters
        //This implementation of the Hill cipher
        //does not verify that the matrix is invertible.
        //This is intentional since we do not need or want to be able to decrypt the output
        //Each round of the Hill cipher encrypts the output of the previous round with a different key


        String keystring, plainstring;
        int keymatrix[][] = new int[2][2];
        int plainmatrix[] = new int[2];
        int outmatrix[][] = new int[2][2];
        int count;

        //grab parts of the string to use for the matrix multiplication
        plainstring = x.substring(0,4);
        keystring = x.substring(4,8);

        //prepare key matrix
        keymatrix[0][0] = ((int) keystring.charAt(0)) % 26;
        keymatrix[0][1] = ((int) keystring.charAt(1)) % 26;
        keymatrix[1][0] = ((int) keystring.charAt(2)) % 26;
        keymatrix[1][1] = ((int) keystring.charAt(3)) % 26;

        for(count = 0; count < 3; count ++);
        {

            //prepare first plaintext matrix
            plainmatrix[0] = ((int) plainstring.charAt(0)) % 26;
            plainmatrix[1] = ((int) plainstring.charAt(1)) % 26;

            //do the matrix multiplication
            outmatrix[0][0] = ((keymatrix[0][0] * plainmatrix[0]) + (keymatrix[0][1] * plainmatrix[1])) % 26;
            outmatrix[1][0] = ((keymatrix[1][0] * plainmatrix[0]) + (keymatrix[1][1] * plainmatrix[1])) % 26;

            //load the next 2x1 array
            plainmatrix[0] = ((int) plainstring.charAt(2)) % 26;
            plainmatrix[1] = ((int) plainstring.charAt(3)) % 26;

            //do the matrix multiplication
            outmatrix[0][1] = ((keymatrix[0][0] * plainmatrix[0]) + (keymatrix[0][1] * plainmatrix[1])) % 26;
            outmatrix[1][1] = ((keymatrix[1][0] * plainmatrix[0]) + (keymatrix[1][1] * plainmatrix[1])) % 26;

            //if we're not on the last pass
            //load output into plain matrix and load new key
            if(count < 2)
            {
                int increment = (count + 1) * 4; //set indexes correctly for next chunk of string

                keystring = x.substring((4 + increment),(8 + increment));

                //grab each character of output to create new plain string
                plainstring = Character.toString((char)(outmatrix[0][0] + 65));
                plainstring = plainstring + Character.toString((char)(outmatrix[1][0] + 65));
                plainstring = plainstring + Character.toString((char)(outmatrix[0][1] + 65));
                plainstring = plainstring + Character.toString((char)(outmatrix[1][1] + 65));

            }
        }
        //Encryption done
        //Convert the results to uppercase characters and load into output
        x = Character.toString((char)(outmatrix[0][0] + 65));
        x = x + Character.toString((char)(outmatrix[1][0] + 65));
        x = x + Character.toString((char)(outmatrix[0][1] + 65));
        //System.out.printf(“Function2 returns:%s\n”,x);
        System.out.printf("Function 2 returns:%s\n",x);
        return x;
    }

    private static String function3(String x){				//3 numbers
        return new String("333");
    }

    private static String function4(String x){ 				//3 special symbols
        return new String("$$$");
        /*int a, b, c = 0;
        char[] tempArray = new char[8];
        char[] returnArray = new char[3];
        int array[] = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63};

        for (int i = 0; i < 8; i++) {
            a = (int) x.charAt(i);
            b = (int) x.charAt(i + 8);
            c = (a + b) % 26;
            tempArray[i] = (char) array[c];
        }

        a = 0;
        b = 1;
        for (int i = 0; i < 16; i++) {
            a += x.charAt(i);
            b *= x.charAt(i);
            b = b % 71 + 1;
        }
        a %= 8;
        if (a % 2 == 0)
            a++;

        for (int i = 1; i < 4; i++) {
            returnArray[i - 1] = tempArray[(a * i + b) % 8];
        }

        System.out.printf("Function 4 returns:%s\n", returnArray);
        System.out.println(returnArray);
        return new String(returnArray);*/
    }
    private static String function5(String x){				 //4 randoms
        char[] temp = new char[16];
        char[] ret = new char[4];
        int array[] = {33,35,36,37,38,42,48,49,50,51,52,53,54,55,56,57,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,94,97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
        int a,b,c,d = 0;
        for (int i = 0; i < 16; i++){
            temp[i] = x.charAt(i);
        }
        for (int i = 0; i < 4; i++){
            a = (int) temp[i];
            b = (int) temp[i+4];
            c = (int) temp[i+8];
            d = (int) temp[i+12];
            a*=(b*c*d);
            a%=71;
            ret[i] = (char) array[a];
        }
        System.out.print("Function 5 returns:");
        System.out.println(ret);
        return new String(ret);
    }
    private static String function6(StringBuilder x){
        String temp = x.toString();
        char[] first = new char[12];
        char[] second = new char[8];
        char[] ret = new char[12];
        int a,b,c,d,e = 0;
        for (int i = 0; i < 12; i+=3){
            a = (int) temp.charAt(i+0);			//setup for Affine cipher
            b = (int) temp.charAt(i+1);
            c = (int) temp.charAt(i+2);
            d = (a+b+c)%2+1;
            e = (((a*71)+b*71)+c)%3;			//0,1,2(1x+0) 0,2,1(2x+0) 1,0,2 (2x+1) 1,2,0 (1x+1) 2,1,0 (2x+2) 2,0,1 (1x+2)
            first[(2*i/3)] = temp.charAt((d*i+e)%3+i);
            first[(2*i/3)+1] = temp.charAt((d*(i+1)+e)%3+i);
            second[(i/3)] = temp.charAt((d*(i+2)+e)%3+i);
        }
        for (int i = 0; i < 4; i++){					//appends randoms to second array
            second[i+4] = temp.charAt(i+12);
        }
        a = d = 0;
        b = e = 1;
        for (int i = 0; i < 16; i++){					//takes in entire set of x to build Affine cipher
            d+=temp.charAt(i);
            e*=temp.charAt(i);
            e=e%8+1;
        }
        d%=8;
        if (d%2 == 0) d++; 					//ensure d is relatively prime to 8
        for(int i = 0; i < 4; i++){					//select four chars from pool of 8
            first[i+8] = second[(d*i+e)%8];
        }
        for(int i = 4; i < 8; i++){					//uses unused chars to build final scramble
            a+= (int) second[(d*i+e)%8];
            b*= (int) second[(d*i+e)%8];
            b=b%12+1;
        }
        a%=12;
        while (a%2 == 0 || a%3 == 0) a++;
        for(int i = 0;i < 12;i++){
            ret[i] = first[(a*i+b)%12];
        }
        return new String(ret);
    }
}
